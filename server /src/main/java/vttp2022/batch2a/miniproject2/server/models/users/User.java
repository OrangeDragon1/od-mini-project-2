package vttp2022.batch2a.miniproject2.server.models.users;

import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.json.JsonObject;

public class User implements UserDetails {

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Role role;
  private Boolean verified;
  private String verificationString;
  private String resetPwdString;

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public void setPassword(String password) { this.password = password; }
  public Role getRole() { return role; }
  public void setRole(Role role) { this.role = role; }
  public Boolean getVerified() { return verified; }
  public void setVerified(Boolean verified) { this.verified = verified; }
  public String getVerificationString() { return verificationString; }
  public void setVerificationString(String verificationString) { this.verificationString = verificationString; }
  public String getResetPwdString() { return resetPwdString; }
  public void setResetPwdString(String resetPwdString) { this.resetPwdString = resetPwdString; }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override public String getUsername() { return email; }
  @Override public String getPassword() { return password; }
  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return true; }

  public static User createUser(SqlRowSet rs) {
    User u = new User();
    u.setFirstName(rs.getString("firstName"));
    u.setLastName(rs.getString("lastName"));
    u.setEmail(rs.getString("email"));
    u.setPassword(rs.getString("password"));
    u.setRole(Role.valueOf(rs.getString("role")));
    u.setVerified(rs.getBoolean("verified"));
    u.setVerificationString(rs.getString("verificationString"));
    u.setResetPwdString(rs.getString("resetPwdString"));
    return u;
  }

  public static User createUser(JsonObject jo, PasswordEncoder passwordEncoder) {
    User u = new User();
    u.setFirstName(jo.getString("firstName"));
    u.setLastName(jo.getString("lastName"));
    u.setEmail(jo.getString("email"));
    u.setPassword(passwordEncoder.encode(jo.getString("password")));
    u.setRole(Role.USER);
    u.setVerified(false);
    if (jo.containsKey("verificationString")) {
      u.setVerificationString(jo.getString("verificationString"));
    }
    if (jo.containsKey("resetPwdString")) {
      u.setResetPwdString(jo.getString("resetPwdString"));
    }
    return u;
  }

}
