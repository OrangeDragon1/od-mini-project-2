package vttp2022.batch2a.miniproject2.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.Utils;
import vttp2022.batch2a.miniproject2.server.models.users.User;
import vttp2022.batch2a.miniproject2.server.repositories.UserRepository;

@Service
public class AuthenticationService {

  @Autowired private UserRepository userRepo;
  @Autowired private JWTService jwtSvc;
  @Autowired private AuthenticationManager authManager;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private JavaMailSender emailSender;


  public JsonObject register(JsonObject jo) {

    if (userRepo.findUserByEmail(jo.getString("email")).isPresent()) {
      return Utils.createError(
        "User '%s' already exists".formatted(jo.getString("email"))
      );
    }

    User user = User.createUser(jo, passwordEncoder);
    String verificationString = Utils.generateHash(user.getEmail(), 1000);
    user.setVerificationString(verificationString);
    userRepo.register(user);
    sendEmail(
      user.getEmail(), 
      "Account verification required", 
      Utils.EMAIL_VERIFICATION_TEMPLATE.formatted(
        user.getEmail(),
        verificationString
      )
    );
    return Utils.createSuccess(
      "User '%s' has been successfully registered".formatted(user.getEmail())
    );
  }

  public JsonObject authenticate(JsonObject jo) {

    try {
      authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          jo.getString("email"),
          jo.getString("password")
        )
      );
      User user = userRepo.findUserByEmail(jo.getString("email")).get();

      if (!user.getVerified()) {
        return Utils.createError("User '%s' has yet to be verified".formatted(user.getEmail()));
      }

      String jwtToken = jwtSvc.generateToken(user.getRole().name(), user);
      return Utils.createSuccess(
      "User '%s' has been successfully authenticated".formatted(user.getEmail()),
      jwtToken
      );
    } catch (Exception ex) {
      return Utils.createError(ex.getMessage()); 
    }

  }

  public JsonObject verify(String verificationString) {
    Optional<User> opt = userRepo.findVerificationString(verificationString);
    
    if(opt.isEmpty()) {
      return Utils.createError("Invalid verification string");
    }
    
    userRepo.verifyUser(verificationString);
    return Utils.createSuccess("User '%s' has been successfully verified".formatted(
      opt.get().getEmail())
    );
  }

  public JsonObject changePassword(JsonObject jo) {
    String email = jwtSvc.extractUsername();

    try {
      Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          email,
          jo.getString("password")
        )
      );
      
      if (authentication.isAuthenticated()) {
        User user = userRepo.findUserByEmail(jwtSvc.extractUsername()).get();
        String encodedNewPassword = passwordEncoder.encode(jo.getString("newPassword"));
        user.setPassword(encodedNewPassword);
        userRepo.updateUserPassword(user);
        return Utils.createSuccess(
          "User '%s's' password has been changed successfully".formatted(user.getEmail())
        );
      }
    } catch (Exception ex) { }
    
    return Utils.createError("Wrong password");
  }

  public JsonObject getUser() {
    String email = jwtSvc.extractUsername();
    Optional<User> opt = userRepo.findUserByEmail(email);
    User user = opt.get();
    return user.toJson();
  }

  public JsonObject forgotPassword(JsonObject jo) {
    Optional<User> opt = userRepo.findUserByEmail(jo.getString("email"));

    if (opt.isEmpty()) {
      return Utils.createError("User '%s' does not exist".formatted(jo.getString("email")));
    }
    
    User user = opt.get();
    String resetPwdString = Utils.generateHash(user.getEmail(), 1100);
    user.setResetPwdString(resetPwdString);
    userRepo.insertResetPwdString(user);
    sendEmail(
      user.getEmail(), 
      "Reset password", 
      Utils.EMAIL_RESET_PASSWORD_TEMPLATE.formatted(user.getEmail(), resetPwdString)
    );

    return Utils.createSuccess(
      "Request to reset password for user '%s' is successful".formatted(user.getEmail())
    );
  }

  public JsonObject resetPassword(String resetPwdString) {
    Optional<User> opt = userRepo.findResetPwdString(resetPwdString);
    
    if(opt.isEmpty()) {
      return Utils.createError("Invalid reset password string");
    }

    String randomPassword = Utils.generateRandomPassword();
    String encodedPassword = passwordEncoder.encode(randomPassword);
    userRepo.resetPassword(resetPwdString, encodedPassword);
    sendEmail(
      opt.get().getEmail(), 
      "Password has been reset successfully",
      Utils.EMAIL_PASSWORD_RESETTED_TEMPLATE.formatted(
        opt.get().getEmail(),
        randomPassword
      )
    );
    return Utils.createSuccess("User '%s''s password has reset successfully".formatted(
      opt.get().getEmail())
    );
  }

  public String getRole() { 
    return jwtSvc.extractRole(); 
  }

  private void sendEmail(String email, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("company@company.com");
    message.setTo(email);
    message.setSubject(subject);
    message.setText(text);
    emailSender.send(message);
  }
  
}
