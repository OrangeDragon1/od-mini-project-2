package vttp2022.batch2a.miniproject2.server.models.duffel.duffelairports;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class City {
  private String name;
  private String id;
  private String iataCountryCode;
  private String iataCode;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getIataCountryCode() { return iataCountryCode; }
  public void setIataCountryCode(String iataCountryCode) { this.iataCountryCode = iataCountryCode; }
  public String getIataCode() { return iataCode; }
  public void setIataCode(String iataCode) { this.iataCode = iataCode; }

  public static City create(JsonObject jo) {
    City c = new City();
    c.setName(jo.getString("name"));
    c.setId(jo.getString("id"));
    c.setIataCountryCode(jo.getString("iata_country_code"));
    c.setIataCode(jo.getString("iata_code"));
    return c;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("name", getName())
        .add("id", getId())
        .add("iata_country_code", getIataCountryCode())
        .add("iata_code", getIataCode())
        .build();
  }
  
  @Override
  public String toString() {
    return "City [name=" + name + ", id=" + id + ", iataCountryCode=" + iataCountryCode + ", iataCode=" + iataCode
        + "]";
  }
  
}
