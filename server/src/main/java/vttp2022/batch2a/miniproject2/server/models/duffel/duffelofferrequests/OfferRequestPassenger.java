package vttp2022.batch2a.miniproject2.server.models.duffel.duffelofferrequests;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.stream.JsonCollectors;
import vttp2022.batch2a.miniproject2.server.models.duffel.Enum.FareType;
import vttp2022.batch2a.miniproject2.server.models.duffel.Enum.PassengerType;


public class OfferRequestPassenger {

  private Integer age; // required or type or faretype
  private String familyName; // only required when loyalty programme present
  private String givenName; // only required when loyalty programme present
  private List<OfferRequestPassengerLoyaltyProgrammeAccount> loyaltyProgrammeAccounts;
  private PassengerType type; // required or age or faretype
  private FareType fareType; // required or age or type

  public Integer getAge() { return age; }
  public void setAge(Integer age) { this.age = age; }
  public String getFamilyName() { return familyName; }
  public void setFamilyName(String familyName) { this.familyName = familyName; }
  public String getGivenName() { return givenName; }
  public void setGivenName(String givenName) { this.givenName = givenName; }
  public List<OfferRequestPassengerLoyaltyProgrammeAccount> getLoyaltyProgrammeAccounts() { 
    return loyaltyProgrammeAccounts; 
  }
  public void setLoyaltyProgrammeAccounts(List<OfferRequestPassengerLoyaltyProgrammeAccount> loyaltyProgrammeAccounts) { 
    this.loyaltyProgrammeAccounts = loyaltyProgrammeAccounts; 
  }
  public PassengerType getType() { return type; }
  public void setType(PassengerType type) { this.type = type; }
  public FareType getFareType() { return fareType; }
  public void setFareType(FareType fareType) { this.fareType = fareType; }

  public static OfferRequestPassenger create(JsonObject jo) {
    OfferRequestPassenger p = new OfferRequestPassenger();
    if (jo.containsKey("age"))
      p.setAge(jo.getInt("age"));
    if (jo.containsKey("familyName"))
      p.setFamilyName(jo.getString("familyName"));
    if (jo.containsKey("givenName"))
      p.setGivenName(jo.getString("givenName"));
    // if (jo.containsKey("loyaltyProgrammeAccounts"))
    if (jo.containsKey("type"))
      p.setType(PassengerType.valueOf(jo.getString("type")));
    if (jo.containsKey("fareType"))
      p.setFareType(FareType.valueOf(jo.getString("fareType")));
    return p;
    // private Integer age; // required or type or faretype
    // private String familyName; // only required when loyalty programme present
    // private String givenName; // only required when loyalty programme present
    // private List<LoyaltyProgrammeAccount> loyaltyProgrammeAccounts;
    // private Type type; // required or age or faretype
    // private FareType fareType; // required or age or type
  } 

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    if (null != age)
      objBuilder.add("age", getAge());
    if (null != loyaltyProgrammeAccounts) 
      objBuilder.add("family_name", getFamilyName());
    if (null != fareType)
      objBuilder.add("fare_type", getFareType().name());
    if (null != loyaltyProgrammeAccounts) {
      objBuilder.add("given_name", getGivenName());
      objBuilder.add("loyalty_programme_accounts", getLoyaltyProgrammeAccounts().stream()
          .map(OfferRequestPassengerLoyaltyProgrammeAccount::toJson)
          .collect(JsonCollectors.toJsonArray())
      );
    }
    if (null != type) 
      objBuilder.add("type", getType().name());
    return objBuilder.build();
  }

  public JsonObject toJsonWithType() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    if (null != loyaltyProgrammeAccounts) {
      objBuilder.add("family_name", getFamilyName());
      objBuilder.add("given_name", getGivenName());
      objBuilder.add("loyalty_programme_accounts", getLoyaltyProgrammeAccounts().stream()
          .map(OfferRequestPassengerLoyaltyProgrammeAccount::toJson)
          .collect(JsonCollectors.toJsonArray())
      );
    }
    objBuilder.add("type", getType().name());
    return objBuilder.build();
  }

  public JsonObject toJsonWithAge() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder.add("age", getAge());
    if (null != loyaltyProgrammeAccounts) {
      objBuilder.add("family_name", getFamilyName());
      objBuilder.add("given_name", getGivenName());
      objBuilder.add("loyalty_programme_accounts", getLoyaltyProgrammeAccounts().stream()
          .map(OfferRequestPassengerLoyaltyProgrammeAccount::toJson)
          .collect(JsonCollectors.toJsonArray())
      );
    }
    return objBuilder.build();
  }

  // recommended if passing passengers with age
  public JsonObject toJsonWithFareType() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    if (null != age)
      objBuilder.add("age", getAge());
    if (null != loyaltyProgrammeAccounts) 
      objBuilder.add("family_name", getFamilyName());
    objBuilder.add("fare_type", getFareType().name());
    if (null != loyaltyProgrammeAccounts) {
      objBuilder.add("given_name", getGivenName());
      objBuilder.add("loyalty_programme_accounts", getLoyaltyProgrammeAccounts().stream()
          .map(OfferRequestPassengerLoyaltyProgrammeAccount::toJson)
          .collect(JsonCollectors.toJsonArray())
      );
    }
    return objBuilder.build();
  }

  @Override
  public String toString() {
    return "Passenger [age=" + age + ", familyName=" + familyName + ", givenName=" + givenName
        + ", loyaltyProgrammeAccounts=" + loyaltyProgrammeAccounts + ", type=" + type + ", fareType=" + fareType + "]";
  }
  
}
