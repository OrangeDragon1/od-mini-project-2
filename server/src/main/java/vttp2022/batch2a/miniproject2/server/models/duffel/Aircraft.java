package vttp2022.batch2a.miniproject2.server.models.duffel;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Aircraft {
  
  private String name;
  private String id;
  private String iataCode;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getIataCode() { return iataCode; }
  public void setIataCode(String iataCode) { this.iataCode = iataCode; }

  public static Aircraft create(JsonObject jo) {
    Aircraft a = new Aircraft();
    a.setName(jo.getString("name"));
    a.setId(jo.getString("id"));
    a.setIataCode(jo.getString("iata_code"));
    return a;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("name", getName())
        .add("id", getId())
        .add("iataCode", getIataCode())
        .build();
  }

  @Override
  public String toString() {
    return "Aircraft [name=" + name + ", id=" + id + ", iataCode=" + iataCode + "]";
  }
  
}
