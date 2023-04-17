package vttp2022.batch2a.miniproject2.server.models.duffel.duffelofferrequests;

import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class OfferRequestSlice {

  private String origin; // required
  private String destination; // required 
  private OfferRequestSliceTimeRange departureTimeRange;
  private String departureDate; // required, ISO 8601, YYYY-MM-DD
  private OfferRequestSliceTimeRange arrivalTimeRange;

  public String getOrigin() { return origin; }
  public void setOrigin(String origin) { this.origin = origin; }
  public String getDestination() { return destination; }
  public void setDestination(String destination) { this.destination = destination; }
  public OfferRequestSliceTimeRange getDepartureTimeRange() { return departureTimeRange; }
  public void setDepartureTimeRange(OfferRequestSliceTimeRange departureTimeRange) { this.departureTimeRange = departureTimeRange; }
  public String getDepartureDate() { return departureDate; }
  public void setDepartureDate(String departureDate) { this.departureDate = departureDate; }
  public OfferRequestSliceTimeRange getArrivalTimeRange() { return arrivalTimeRange; }
  public void setArrivalTimeRange(OfferRequestSliceTimeRange arrivalTimeRange) { this.arrivalTimeRange = arrivalTimeRange; }
  
  public static OfferRequestSlice create(JsonObject jo) {
    OfferRequestSlice s = new OfferRequestSlice();
    s.setOrigin(jo.getString("origin"));
    s.setDestination(jo.getString("destination"));
    // s.setDepartureTimeRange(null);
    s.setDepartureDate(jo.getString("departureDate"));
    // s.setArrivalTimeRange(null);
    return s;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder.add("origin", getOrigin());
    objBuilder.add("destination", getDestination());
    if (null != departureTimeRange)
      objBuilder.add("departure_time", getDepartureTimeRange().toJson());
    objBuilder.add("departure_date", getDepartureDate());
    if (null != arrivalTimeRange)
      objBuilder.add("arrival_time", getArrivalTimeRange().toJson());
    return objBuilder.build();
  }
  
  @Override
  public String toString() {
    return "Slice [origin=" + origin + ", destination=" + destination + ", departureTimeRange=" + departureTimeRange
        + ", departureDate=" + departureDate + ", arrivalTimeRange=" + arrivalTimeRange + "]";
  }

}
