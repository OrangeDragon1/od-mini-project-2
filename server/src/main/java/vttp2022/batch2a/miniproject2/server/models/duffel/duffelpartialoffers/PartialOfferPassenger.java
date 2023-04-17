package vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2022.batch2a.miniproject2.server.models.duffel.Enum.PassengerType;

public class PartialOfferPassenger {
  
  private PassengerType type; // nullable
  private String id;
  private Integer age; // nullable

  public PassengerType getType() { return type; }
  public void setType(PassengerType type) { this.type = type; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public Integer getAge() { return age; }
  public void setAge(Integer age) { this.age = age; }

  public static PartialOfferPassenger create(JsonObject jo) {
    PartialOfferPassenger p = new PartialOfferPassenger();
    if (!jo.isNull("type"))
      p.setType(PassengerType.valueOf(jo.getString("type")));
    // if (!jo.isNull("id"))
    p.setId(jo.getString("id"));
    if (!jo.isNull("age"))
      p.setAge(jo.getInt("age"));
    return p;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    if (null != type)
      objBuilder.add("type", getType().name());
    objBuilder.add("id", getId());
    if (null != age) 
      objBuilder.add("age", getAge());
    return objBuilder.build();
  }
  
  @Override
  public String toString() {
    return "PartialOfferPassenger [type=" + type + ", id=" + id + ", age=" + age + "]";
  }
  
}
