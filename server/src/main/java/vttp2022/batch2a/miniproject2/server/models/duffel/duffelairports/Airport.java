package vttp2022.batch2a.miniproject2.server.models.duffel.duffelairports;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;

public class Airport {

  private String timeZone;
  private String name;
  private Double longitude;
  private Double latitude;
  private String id;
  private String icaoCode; // nullable
  private String iataCountryCode;
  private String iataCode;
  private String iataCityCode;
  private String cityName;
  private City city; // nullable

  public String getTimeZone() { return timeZone; }
  public void setTimeZone(String timeZone) { this.timeZone = timeZone; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Double getLongitude() { return longitude; }
  public void setLongitude(Double longitude) { this.longitude = longitude; }
  public Double getLatitude() { return latitude; }
  public void setLatitude(Double latitude) { this.latitude = latitude; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getIcaoCode() { return icaoCode; }
  public void setIcaoCode(String icaoCode) { this.icaoCode = icaoCode; }
  public String getIataCountryCode() { return iataCountryCode; }
  public void setIataCountryCode(String iataCountryCode) { this.iataCountryCode = iataCountryCode; }
  public String getIataCode() { return iataCode; }
  public void setIataCode(String iataCode) { this.iataCode = iataCode; }
  public String getIataCityCode() { return iataCityCode; }
  public void setIataCityCode(String iataCityCode) { this.iataCityCode = iataCityCode; }
  public String getCityName() { return cityName; }
  public void setCityName(String cityName) { this.cityName = cityName; }
  public City getCity() { return city; }
  public void setCity(City city) { this.city = city; }

  public static Airport create(JsonObject jo) {
    try {
      Airport a = new Airport();
      a.setTimeZone(jo.getString("time_zone"));
      a.setName(jo.getString("name"));
      a.setLongitude(jo.getJsonNumber("longitude").doubleValue());
      a.setLatitude(jo.getJsonNumber("latitude").doubleValue());
      a.setId(jo.getString("id"));
      a.setIcaoCode(jo.isNull("icao_code") ? null : jo.getString("icao_code")); // nullable
      a.setIataCountryCode(jo.getString("iata_country_code"));
      a.setIataCode(jo.getString("iata_code"));
      a.setIataCityCode(jo.getString("iata_city_code"));
      a.setCityName(jo.getString("city_name"));
      a.setCity(jo.isNull("city") ? null : City.create(jo.getJsonObject("city"))); //nullable
      return a;
    } catch (Exception ex) { 
      return null;
    }
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder()
        .add("timeZone", getTimeZone())
        .add("name", getName())
        .add("longitude", getLongitude())
        .add("latitude", getLatitude())
        .add("id", getId());
    if (null == getIcaoCode()) {
      objBuilder.add("icaoCode", JsonValue.NULL);
    } else {
      objBuilder.add("icaoCode", getIcaoCode());
    }
    objBuilder
        .add("iataCountryCode", getIataCountryCode())
        .add("iataCode", getIataCode())
        .add("iataCityCode", getIataCityCode())
        .add("cityName", getCityName());
    if (null == getCity()) {
      objBuilder.add("city", JsonValue.NULL);
    } else {
      objBuilder.add("city", getCity().toJson());
    }
    return objBuilder.build();
  }
  
  @Override
  public String toString() {
    return "Airport [timeZone=" + timeZone + ", name=" + name + ", longitude=" + longitude + ", latitude=" + latitude
        + ", id=" + id + ", icaoCode=" + icaoCode + ", iataCountryCode=" + iataCountryCode + ", iataCode=" + iataCode
        + ", iataCityCode=" + iataCityCode + ", cityName=" + cityName + ", city=" + city + "]";
  }
    
  // public static Airport create(JsonObject jo) {
  //   Airport a = new Airport();
  //   a.setTimeZone(jo.isNull("time_zone") ? null : jo.getString("time_zone"));
  //   a.setName(jo.isNull("name") ? null : jo.getString("name"));
  //   a.setLongitude(jo.isNull("longitude") ? null : jo.getJsonNumber("longitude").doubleValue());
  //   a.setLatitude(jo.isNull("latitude") ? null : jo.getJsonNumber("latitude").doubleValue());
  //   a.setId(jo.isNull("id") ? null : jo.getString("id"));
  //   a.setIcaoCode(jo.isNull("icao_code") ? null : jo.getString("icao_code")); // nullable
  //   a.setIataCountryCode(jo.isNull("iata_country_code") ? null : jo.getString("iata_country_code"));
  //   a.setIataCode(jo.isNull("iata_code") ? null : jo.getString("iata_code"));
  //   a.setIataCityCode(jo.isNull("iata_city_code") ? null : jo.getString("iata_city_code"));
  //   a.setCityName(jo.isNull("city_name") ? null : jo.getString("city_name"));
  //   a.setCity(jo.isNull("city") ? null : City.create(jo.getJsonObject("city"))); //nullable
  //   return a;
  // }

  // private String name;
  // private String id;
  // private String timeZoneOffset;
  // private String iataCode;
  // private GeoCode geoCode;
  // private Address address;
  // private Integer travelersScore;

  // public String getName() { return name; }
  // public void setName(String name) { this.name = name; }
  // public String getId() { return id; }
  // public void setId(String id) { this.id = id; }
  // public String getTimeZoneOffset() { return timeZoneOffset; }
  // public void setTimeZoneOffset(String timeZoneOffset) { this.timeZoneOffset = timeZoneOffset; }
  // public String getIataCode() { return iataCode; }
  // public void setIataCode(String iataCode) { this.iataCode = iataCode; }
  // public GeoCode getGeoCode() { return geoCode; }
  // public void setGeoCode(GeoCode geoCode) { this.geoCode = geoCode; }
  // public Address getAddress() { return address; }
  // public void setAddress(Address address) { this.address = address; }
  // public Integer getTravelersScore() { return travelersScore; }
  // public void setTravelersScore(Integer travelersScore) { this.travelersScore = travelersScore; }

  // public static Airport createAirportEndPt(JsonObject jo) { 
  //   Airport a = new Airport();
  //   a.setName(jo.getString("name"));
  //   a.setId(jo.getString("id"));
  //   a.setTimeZoneOffset(jo.getString("timeZoneOffset"));
  //   a.setIataCode(jo.getString("iataCode"));
  //   a.setGeoCode(GeoCode.createGeoCode(jo.getJsonObject("geoCode")));
  //   a.setAddress(Address.createAddress(jo.getJsonObject("address")));
  //   a.setTravelersScore(Optional.ofNullable(jo.getJsonObject("analytics"))
  //       .map(v -> v.getJsonObject("travelers"))
  //       .flatMap(v -> Optional.ofNullable(v.getInt("score", 0)))
  //       .orElse(0));
  //   return a;
  // }

  // public static Airport createAirportDB(JsonObject jo) { 
  //   return null;
  // }

  // public JsonObject toJson() {
  //   return Json.createObjectBuilder()
  //       .add("name", name)
  //       .add("id", id)
  //       .add("timeZoneOffset", timeZoneOffset)
  //       .add("iataCode", iataCode)
  //       .add("geoCode", geoCode.toJson())
  //       .add("address", address.toJson())
  //       .add("travelersScore", travelersScore)
  //       .build();
  // }

  // @Override
  // public String toString() {
  //   return "Airport [name=" + name + ", id=" + id + ", timeZoneOffset=" + timeZoneOffset + ", iataCode=" + iataCode
  //       + ", geoCode=" + geoCode.toString() + ", address=" + address.toString() + ", travelersScore=" + travelersScore + "]";
  // }
}
