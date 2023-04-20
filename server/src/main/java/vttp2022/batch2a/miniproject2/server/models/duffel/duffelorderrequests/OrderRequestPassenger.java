package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorderrequests;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.stream.JsonCollectors;

public class OrderRequestPassenger {
  private String type;
  private String title;
  private String phoneNumber;
  private List<OrderRequestPassengerIdentityDocuments> identityDocuments; // nullable
  private String id;
  private String givenName;
  private String gender;
  private String familyName;
  private String email;
  private String bornOn;

  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getPhoneNumber() { return phoneNumber; }
  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
  public List<OrderRequestPassengerIdentityDocuments> getIdentityDocuments() { return identityDocuments; }
  public void setIdentityDocuments(List<OrderRequestPassengerIdentityDocuments> identityDocuments) { this.identityDocuments = identityDocuments; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getGivenName() { return givenName; }
  public void setGivenName(String givenName) { this.givenName = givenName; }
  public String getGender() { return gender; }
  public void setGender(String gender) { this.gender = gender; }
  public String getFamilyName() { return familyName; }
  public void setFamilyName(String familyName) { this.familyName = familyName; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getBornOn() { return bornOn; }
  public void setBornOn(String bornOn) { this.bornOn = bornOn; }

  public static OrderRequestPassenger create(JsonObject jo) {
    OrderRequestPassenger p = new OrderRequestPassenger();
    p.setType(jo.getString("type"));
    p.setTitle(jo.getString("title"));
    p.setPhoneNumber(jo.getString("phoneNumber").replaceAll("[()\\s-]+", ""));
    if(!jo.isNull("identityDocuments")) {
      p.setIdentityDocuments(jo.getJsonArray("identityDocuments").stream()
          .map(v -> OrderRequestPassengerIdentityDocuments.create(v.asJsonObject()))
          .collect(Collectors.toList())
      );
    }
    p.setId(jo.getString("id"));
    p.setGivenName(jo.getString("givenName").trim());
    p.setGender(jo.getString("gender"));
    p.setFamilyName(jo.getString("familyName").trim());
    p.setEmail(jo.getString("email").trim());
    p.setBornOn(jo.getString("bornOn").substring(0, 10));
    return p;
  }
  
  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder
        .add("type", getType())
        .add("title", getTitle())
        .add("phone_number", getPhoneNumber());
    
    if(null != identityDocuments) {
      objBuilder.add("identity_documents", getIdentityDocuments().stream()
          .map(v -> v.toJson())
          .collect(JsonCollectors.toJsonArray())
      );
    }

    objBuilder
        .add("id", getId())
        .add("given_name", getGivenName())
        .add("gender", getGender())
        .add("family_name", getFamilyName())
        .add("email", getEmail())
        .add("born_on", getBornOn());
    
    return objBuilder.build();
  }

  @Override
  public String toString() {
    return "OrderRequestPassenger [type=" + type + ", title=" + title + ", phoneNumber=" + phoneNumber
        + ", identityDocuments=" + identityDocuments + ", id=" + id + ", givenName=" + givenName + ", gender=" + gender
        + ", familyName=" + familyName + ", email=" + email + ", bornOn=" + bornOn + "]";
  } 
}
