package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class OrderPassenger {
  private String type; // nullable
  private String title; 
  private String phoneNumber;
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

  public static OrderPassenger create(JsonObject jo) {
    OrderPassenger p = new OrderPassenger();
    if (!jo.isNull("type"))
      p.setType(jo.getString("type"));
    p.setTitle(jo.getString("title"));
    p.setPhoneNumber(jo.getString("phone_number"));
    p.setId(jo.getString("id"));
    p.setGivenName(jo.getString("given_name"));
    p.setGender(jo.getString("gender"));
    p.setFamilyName(jo.getString("family_name"));
    p.setEmail(jo.getString("email"));
    p.setBornOn(jo.getString("born_on"));
    return p;
  }

  public static OrderPassenger create(SqlRowSet rs) {
    OrderPassenger p = new OrderPassenger();
    p.setType(rs.getString("type"));
    p.setTitle(rs.getString("title"));
    p.setPhoneNumber(rs.getString("phone_number"));
    p.setId(rs.getString("id"));
    p.setGivenName(rs.getString("given_name"));
    p.setGender(rs.getString("gender"));
    p.setFamilyName(rs.getString("family_name"));
    p.setEmail(rs.getString("email"));
    p.setBornOn(rs.getString("born_on"));
    return p;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    if (null != type)
      objBuilder.add("type", getType());
    else
      objBuilder.addNull("type");
    objBuilder
        .add("title", getTitle())
        .add("phoneNumber", getPhoneNumber())
        .add("id", getId())
        .add("givenName", getGivenName())
        .add("gender", getGender())
        .add("familyName", getFamilyName())
        .add("email", getEmail())
        .add("bornOn", getBornOn());

    return objBuilder.build();
  }
  
  @Override
  public String toString() {
    return "OrderPassenger [type=" + type + ", title=" + title + ", phoneNumber=" + phoneNumber + ", id=" + id
        + ", givenName=" + givenName + ", gender=" + gender + ", familyName=" + familyName + ", email=" + email
        + ", bornOn=" + bornOn + "]";
  }
}
