package vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class PartialOfferSlice {
  
  private String origin;
  private String originName;
  
  private String destination;
  private String destinationName;
  private String departureDate;
  
  public String getOrigin() { return origin; }
  public void setOrigin(String origin) { this.origin = origin; }
  public String getOriginName() { return originName; }
  public void setOriginName(String originName) { this.originName = originName; }
  public String getDestination() { return destination; }
  public void setDestination(String destination) { this.destination = destination; }
  public String getDestinationName() { return destinationName; }
  public void setDestinationName(String destinationName) { this.destinationName = destinationName; }
  public String getDepartureDate() { return departureDate; }
  public void setDepartureDate(String departureDate) { this.departureDate = departureDate; }

  public static PartialOfferSlice create(JsonObject jo) {
    PartialOfferSlice s = new PartialOfferSlice();
    s.setOrigin(jo.getJsonObject("origin").getString("iata_code"));
    s.setDestination(jo.getJsonObject("destination").getString("iata_code"));
    s.setDepartureDate(jo.getString("departure_date"));

    String originName;
    String destinationName;

    if (jo.getJsonObject("origin").getString("type").equals("city")) {
      originName = jo.getJsonObject("origin").getString("name");
    } else {
      originName = jo.getJsonObject("origin").getString("city_name");
    }

    if (jo.getJsonObject("destination").getString("type").equals("city")) {
      destinationName = jo.getJsonObject("destination").getString("name");
    } else {
      destinationName = jo.getJsonObject("destination").getString("city_name");
    }

    if (null != originName) { s.setOriginName(originName); }
    if (null != destinationName) { s.setDestinationName(destinationName); }
    
    return s;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("origin", getOrigin())
        .add("originName", getOriginName())
        .add("destination", getDestination())
        .add("destinationName", getDestinationName())
        .add("departureDate", getDepartureDate())
        .build();
  }

  @Override
  public String toString() {
    return "PartialOfferSlice [origin=" + origin + ", destination=" + destination + ", departureDate=" + departureDate
    + "]";
  }
  
}
                      