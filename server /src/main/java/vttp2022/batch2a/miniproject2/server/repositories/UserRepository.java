package vttp2022.batch2a.miniproject2.server.repositories;

import static vttp2022.batch2a.miniproject2.server.repositories.Queries.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.batch2a.miniproject2.server.models.users.User;

@Repository
public class UserRepository {
  
  @Autowired private JdbcTemplate jdbcTemplate;

  public void register(User user) {
    jdbcTemplate.update(
      SQL_REGISTER,
      user.getFirstName(),
      user.getLastName(),
      user.getEmail(),
      user.getPassword(),
      user.getVerificationString()
    );
  }

  public Optional<User> findUserByEmail(String email) {
    SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_USER_BY_EMAIL, email);

    if (!rs.next()) {
      return Optional.empty();
    } 

    return Optional.of(User.createUser(rs));
  }

  public Optional<User> findVerificationString(String verficationString) {
    SqlRowSet rs = jdbcTemplate.queryForRowSet(
      SQL_FIND_USER_BY_VERIFICATION_STRING,
      verficationString
    );

    if (!rs.next()) {
      return Optional.empty();
    }

    return Optional.of(User.createUser(rs));
  }

  public Optional<User> findResetPwdString(String resetPwdString) {
    SqlRowSet rs = jdbcTemplate.queryForRowSet(
      SQL_FIND_USER_BY_RESET_PWD_STRING,
      resetPwdString
    );

    if (!rs.next()) {
      return Optional.empty();
    }

    return Optional.of(User.createUser(rs));
  }

  @Transactional
  public void verifyUser(String verficationString) {
    jdbcTemplate.update(UPDATE_USER_VERIFIED, verficationString);
    jdbcTemplate.update(DELETE_VERIFICATION_STRING, verficationString);
  }

  @Transactional
  public void resetPassword(String resetPwdString, String encodedPassword) {
    jdbcTemplate.update(SQL_RESET_PWD_BY_RESET_PWD_STRING, encodedPassword, resetPwdString);
    jdbcTemplate.update(DELETE_RESET_PWD_STRING, resetPwdString);
  }

  public void insertResetPwdString(User user) {
    jdbcTemplate.update(INSERT_RESET_PWD_STRING, user.getResetPwdString(), user.getEmail());
  }

  public void updateUserPassword(User user) {
    jdbcTemplate.update(
      SQL_UPDATE_USER_PASSWORD,
      user.getPassword(),
      user.getEmail()
    );
  }

}
