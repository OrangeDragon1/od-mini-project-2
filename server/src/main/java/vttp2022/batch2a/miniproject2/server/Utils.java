package vttp2022.batch2a.miniproject2.server;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Utils {

  public static final String EMAIL_VERIFICATION_TEMPLATE = """
      \n
      Awaiting verification for user: %s\n
      Verification link: %s
      """;

  public static final String EMAIL_RESET_PASSWORD_TEMPLATE = """
      \n
      Password reset requested from user: %s\n
      Reset password link: %s
      """;

  public static final String EMAIL_PASSWORD_RESETTED_TEMPLATE = """
      \n
      Password has been successfully reset for user: %s\n
      New password: %s

      Please change this password as soon as possible.
      """;

  public static JsonObject payloadToJsonObj(String payload) {
    JsonReader jsonReader = Json.createReader(new StringReader(payload));
    return jsonReader.readObject();
  }

  public static JsonArray payloadToJsonArr(String payload) {
    JsonReader jsonReader = Json.createReader(new StringReader(payload));
    return jsonReader.readArray();
  }

  public static JsonObject createSuccess(String message) {
    return Json.createObjectBuilder()
        .add("success", message)
        .build();
  }

  public static JsonObject createSuccess(String message, String token) {
    return Json.createObjectBuilder()
        .add("success", message)
        .add("token", token)
        .build();
  }

  public static JsonObject createError(String message) {
    return Json.createObjectBuilder()
        .add("error", message)
        .build();
  }

  public static Boolean validateRegister(JsonObject jo) {
    return (jo.containsKey("firstName")
        && jo.containsKey("lastName")
        && jo.containsKey("email")
        && jo.containsKey("password")
        && jo.getString("firstName").length() > 0
        && jo.getString("lastName").length() > 0
        && jo.getString("email").length() > 0
        && jo.getString("password").length() > 0);
  }

  public static Boolean validateAuthentication(JsonObject jo) {
    return (jo.containsKey("email")
        && jo.containsKey("password")
        && jo.getString("email").length() > 0
        && jo.getString("password").length() > 0);
  }

  public static Boolean validateForgotPassword(JsonObject jo) {
    return (jo.containsKey("email")
        && jo.getString("email").length() > 0);
  }

  public static String generateHash(String data, final Integer ITERATIONS) {

    try {
      SecureRandom random = new SecureRandom();
      byte[] salt = new byte[16];
      random.nextBytes(salt);
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.reset();
      md.update(salt);
      byte[] hash = md.digest(data.getBytes());

      for (int i = 0; i < ITERATIONS; i++) {
        md.reset();
        hash = md.digest(hash);
      }

      byte[] encodedHash = Base64.getUrlEncoder().encode(hash);
      String encodedString = new String(encodedHash);
      int paddingIndex = encodedString.indexOf("=");

      if (paddingIndex != -1) {
        encodedString = encodedString.substring(0, paddingIndex);
      }

      return encodedString;
    } catch (Exception ex) {
      throw new RuntimeException("Error generating hash: " + ex.getMessage());
    }

  }

  public static String generateRandomPassword() {
    final int passwordLength = 8;
    final String CHARACTERS = """
        ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+
        """;
    StringBuilder password = new StringBuilder(passwordLength);

    for (int i = 0; i < passwordLength; i++) {
      password.append(
          CHARACTERS.charAt(
              new SecureRandom().nextInt(CHARACTERS.length())));
    }

    return password.toString();
  }

  public static OAuthJSONAccessTokenResponse getAccessTokenResponse(String clientID, String clientSecret)
      throws OAuthSystemException, OAuthProblemException {
    String tokenURL = "https://test.api.amadeus.com/v1/security/oauth2/token";
    String encodedValue = Base64.getEncoder()
        .encodeToString((clientID + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
    OAuthClient client = new OAuthClient(new URLConnectionClient());
    OAuthClientRequest request = OAuthClientRequest.tokenLocation(tokenURL)
        .setGrantType(GrantType.CLIENT_CREDENTIALS)
        .buildBodyMessage();
    request.addHeader("Authorization", "Basic " + encodedValue);
    OAuthJSONAccessTokenResponse oAuthResponse = client.accessToken(request, OAuth.HttpMethod.POST,
        OAuthJSONAccessTokenResponse.class);
    return oAuthResponse;
  }

  public static List<JsonObject> jsonArrToJsonObjs(JsonArray ja) {
    return ja.stream()
        .map(v -> v.asJsonObject())
        .collect(Collectors.toList());
  }

}
