package vttp2022.batch2a.miniproject2.server.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.Utils;
import vttp2022.batch2a.miniproject2.server.services.AuthenticationService;

@Controller
@RequestMapping(path = "/api/v1/auth")
@CrossOrigin(origins="https://od-mini-project-2.vercel.app")
public class AuthenticationController {

  @Autowired private AuthenticationService authSvc;

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
  
  @PostMapping(path = "/register")
  @ResponseBody
  public ResponseEntity<String> postRegister(@RequestBody Optional<String> opt) {

    if (opt.isEmpty()) {
      return ResponseEntity.badRequest()
          .body(Utils.createError("Payload is empty")
          .toString()
          );
    }

    String payload = opt.get();
    JsonObject payloadObj = Utils.payloadToJsonObj(payload);

    if (!Utils.validateRegister(payloadObj)) {
      return ResponseEntity.badRequest()
          .body(Utils.createError("Missing parameter(s) or empty field(s)")
          .toString()
          );
    }

    JsonObject registerObj = authSvc.register(payloadObj);
    return ResponseEntity.ok(registerObj.toString());
  }

  @PostMapping(path = "/authenticate")
  @ResponseBody
  public ResponseEntity<String> postAuthenticate(@RequestBody Optional<String> opt) {

    if (opt.isEmpty()) {
      return ResponseEntity.badRequest()
          .body(Utils.createError("Payload is empty")
          .toString()
          );
    }

    String payload = opt.get();
    JsonObject payloadObj = Utils.payloadToJsonObj(payload);

    if (!Utils.validateAuthentication(payloadObj)) {
      return ResponseEntity.badRequest()
          .body(Utils.createError("Missing parameter(s) or empty field(s)")
          .toString()
          );
    }

    JsonObject authObj = authSvc.authenticate(payloadObj);

    if (authObj.containsKey("error")) {
      return ResponseEntity.badRequest().body(authObj.toString());
    }

    return ResponseEntity.ok(authObj.toString());
  }

  @GetMapping(path = "/verify")
  @ResponseBody
  public ResponseEntity<String> postVerify(@RequestParam Optional<String> verificationString) {
  
    if (verificationString.isEmpty()) {
      return ResponseEntity.badRequest()
          .body(Utils.createError("'verificationString' parameter not found")
          .toString()
          );
    }

    String verificationStr = verificationString.get();
    JsonObject verifyObj = authSvc.verify(verificationStr);

    if (verifyObj.containsKey("error")) {
      return ResponseEntity.badRequest().body(verifyObj.toString());
    }

    return ResponseEntity.ok(verifyObj.toString());
  }

  @PostMapping(path = "/forgotPassword")
  @ResponseBody
  public ResponseEntity<String> postForgotPassword(@RequestBody Optional<String> opt) {

    if (opt.isEmpty()) {
      return ResponseEntity.badRequest()
          .body(Utils.createError("Payload is empty")
          .toString()
          );
    }

    String payload = opt.get();
    JsonObject payloadObj = Utils.payloadToJsonObj(payload);

    if (!Utils.validateForgotPassword(payloadObj)) {
      return ResponseEntity.badRequest()
          .body(Utils.createError("Missing parameter(s) or empty field(s)")
          .toString()
          );
    }

    JsonObject resetPwdObj = authSvc.forgotPassword(payloadObj);

    if (resetPwdObj.containsKey("error")) {
      return ResponseEntity.badRequest().body(resetPwdObj.toString());
    }

    return ResponseEntity.ok(resetPwdObj.toString());
  }

  @PutMapping(path = "/resetPassword")
  @ResponseBody
  public ResponseEntity<String> putResetPassword(@RequestParam Optional<String> resetPwdString) {
    
    if (resetPwdString.isEmpty()) {
      return ResponseEntity.badRequest()
          .body(Utils.createError("'resetPwdString' parameter not found")
          .toString()
          );
    }

    String resetPwdStr = resetPwdString.get();
    JsonObject resetPwdObj = authSvc.resetPassword(resetPwdStr);

    if (resetPwdObj.containsKey("error")) {
      return ResponseEntity.badRequest().body(resetPwdObj.toString());
    }

    return ResponseEntity.ok(resetPwdObj.toString());
  }

  @PutMapping(path = "/changePassword")
  @ResponseBody
  public ResponseEntity<String> putChangePassword(@RequestBody Optional<String> opt) {

    if (opt.isEmpty()) {
      return ResponseEntity.badRequest()
          .body(Utils.createError("Payload is empty")
          .toString()
          );
    }

    String payload = opt.get();
    JsonObject payloadObj = Utils.payloadToJsonObj(payload);
    JsonObject chgPwdObj = authSvc.changePassword(payloadObj);

    if (chgPwdObj.containsKey("error")) {
      return ResponseEntity.badRequest().body(chgPwdObj.toString());
    }

    return ResponseEntity.ok(chgPwdObj.toString());
  }

  @DeleteMapping(path = "/deleteUser")
  @ResponseBody
  public ResponseEntity<String> putDeleteUser() {

    /*
     * Implement logout here 
     */

    return null;
  }

  @PostMapping(path = "/logout")
  @ResponseBody
  public ResponseEntity<String> postLogout() {

    /*
     * Implement logout here 
     */

    return null;
  }


}