package vttp2022.batch2a.miniproject2.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class GeoCode {
  
  private Double latitude;
  private Double longitude;

  public Double getLatitude() { return latitude; }
  public void setLatitude(Double latitude) { this.latitude = latitude; }
  public Double getLongitude() { return longitude; }
  public void setLongitude(Double longitude) { this.longitude = longitude; }  

  public static GeoCode createGeoCode(JsonObject jo) {
    GeoCode g = new GeoCode();
    g.setLatitude(jo.getJsonNumber("latitude").doubleValue());
    g.setLongitude(jo.getJsonNumber("longitude").doubleValue());
    return g;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("latitude", latitude)
        .add("longitude", longitude)
        .build();
  }
  
  @Override
  public String toString() {
    return "GeoCode [latitude=" + latitude + ", longitude=" + longitude + "]";
  }

}
