package vttp2022.batch2a.miniproject2.server.models;

import java.util.Optional;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Airport {
    
  private String name;
  private String id;
  private String timeZoneOffset;
  private String iataCode;
  private GeoCode geoCode;
  private Address address;
  private Integer travelersScore;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getTimeZoneOffset() { return timeZoneOffset; }
  public void setTimeZoneOffset(String timeZoneOffset) { this.timeZoneOffset = timeZoneOffset; }
  public String getIataCode() { return iataCode; }
  public void setIataCode(String iataCode) { this.iataCode = iataCode; }
  public GeoCode getGeoCode() { return geoCode; }
  public void setGeoCode(GeoCode geoCode) { this.geoCode = geoCode; }
  public Address getAddress() { return address; }
  public void setAddress(Address address) { this.address = address; }
  public Integer getTravelersScore() { return travelersScore; }
  public void setTravelersScore(Integer travelersScore) { this.travelersScore = travelersScore; }

  public static Airport createAirportEndPt(JsonObject jo) { 
    Airport a = new Airport();
    a.setName(jo.getString("name"));
    a.setId(jo.getString("id"));
    a.setTimeZoneOffset(jo.getString("timeZoneOffset"));
    a.setIataCode(jo.getString("iataCode"));
    a.setGeoCode(GeoCode.createGeoCode(jo.getJsonObject("geoCode")));
    a.setAddress(Address.createAddress(jo.getJsonObject("address")));
    a.setTravelersScore(Optional.ofNullable(jo.getJsonObject("analytics"))
        .map(v -> v.getJsonObject("travelers"))
        .flatMap(v -> Optional.ofNullable(v.getInt("score", 0)))
        .orElse(0));
    return a;
  }

  public static Airport createAirportDB(JsonObject jo) { 
    return null;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("name", name)
        .add("id", id)
        .add("timeZoneOffset", timeZoneOffset)
        .add("iataCode", iataCode)
        .add("geoCode", geoCode.toJson())
        .add("address", address.toJson())
        .add("travelersScore", travelersScore)
        .build();
  }

  @Override
  public String toString() {
    return "Airport [name=" + name + ", id=" + id + ", timeZoneOffset=" + timeZoneOffset + ", iataCode=" + iataCode
        + ", geoCode=" + geoCode.toString() + ", address=" + address.toString() + ", travelersScore=" + travelersScore + "]";
  }

  

}
