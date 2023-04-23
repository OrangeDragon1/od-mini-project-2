package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorderrequests;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class OrderRequestPassengerIdentityDocuments {
  private String uniqueIdentifier;
  private String type;
  private String issuingCountryCode;
  private String expiresOn;

  public String getUniqueIdentifier() { return uniqueIdentifier; }
  public void setUniqueIdentifier(String uniqueIdentifier) { this.uniqueIdentifier = uniqueIdentifier; }
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public String getIssuingCountryCode() { return issuingCountryCode; }
  public void setIssuingCountryCode(String issuingCountryCode) { this.issuingCountryCode = issuingCountryCode; }
  public String getExpiresOn() { return expiresOn; }
  public void setExpiresOn(String expiresOn) { this.expiresOn = expiresOn; }

  public static OrderRequestPassengerIdentityDocuments create(JsonObject jo) {
    OrderRequestPassengerIdentityDocuments r = new OrderRequestPassengerIdentityDocuments();
    r.setUniqueIdentifier(jo.getString("uniqueIdentifier").trim());
    r.setType(jo.getString("type"));
    r.setIssuingCountryCode(jo.getString("issuingCountryCode").toUpperCase());
    r.setExpiresOn(jo.getString("expiresOn").substring(0, 10));
    return r;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("unique_identifier", getUniqueIdentifier())
        .add("type", getType())
        .add("issuing_country_code", getIssuingCountryCode())
        .add("expires_on", getExpiresOn())
        .build();
  }

  @Override
  public String toString() {
    return "OrderRequestPassengerIdentityDocuments [uniqueIdentifier=" + uniqueIdentifier + ", type=" + type
        + ", issuingCountryCode=" + issuingCountryCode + ", expiresOn=" + expiresOn + "]";
  }

}
