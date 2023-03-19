package vttp2022.batch2a.miniproject2.server.repositories;

public class Queries {

  public static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
  public static final String SQL_FIND_USER_BY_VERIFICATION_STRING = "SELECT * FROM users WHERE verificationString = ?";
  public static final String SQL_REGISTER = "INSERT INTO users(firstName, lastName, email, password, verificationString) VALUES(?, ?, ? ,?, ?)";
  public static final String UPDATE_USER_VERIFIED = "UPDATE users SET verified = 1 WHERE verificationString = ?";
  public static final String DELETE_VERIFICATION_STRING = "UPDATE users SET verificationString = NULL WHERE verificationString = ?";
  public static final String SQL_UPDATE_USER_PASSWORD = "UPDATE users SET password = ? WHERE email = ?";
  public static final String INSERT_RESET_PWD_STRING = "UPDATE users SET resetPwdString = ? WHERE email = ?";
  public static final String SQL_FIND_USER_BY_RESET_PWD_STRING = "SELECT * FROM users WHERE resetPwdString = ?";
  public static final String SQL_RESET_PWD_BY_RESET_PWD_STRING = "UPDATE users SET password = ? WHERE resetPwdString = ?";
  public static final String DELETE_RESET_PWD_STRING = "UPDATE users SET resetPwdString = NULL WHERE resetPwdString = ?";

}
