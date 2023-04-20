package vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2022.batch2a.miniproject2.server.models.duffel.Enum.PassengerType;

public class PartialOfferOfferPassenger {
  private PassengerType type;
  private String id;

  public PassengerType getType() { return type; }
  public void setType(PassengerType type) { this.type = type; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public static PartialOfferOfferPassenger create(JsonObject jo) {
    PartialOfferOfferPassenger p = new PartialOfferOfferPassenger();
    if (!jo.isNull("type"))
      p.setType(PassengerType.valueOf(jo.getString("type")));
    p.setId(jo.getString("id"));
    return p;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    if (null != type)
      objBuilder.add("type", getType().name());
    objBuilder.add("id", getId());
    return objBuilder.build();
  }

  @Override
  public String toString() {
    return "PartialOfferOfferPassenger [type=" + type + ", id=" + id + "]";
  }
  
}
