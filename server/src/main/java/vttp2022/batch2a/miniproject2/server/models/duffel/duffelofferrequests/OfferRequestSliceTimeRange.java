package vttp2022.batch2a.miniproject2.server.models.duffel.duffelofferrequests;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class OfferRequestSliceTimeRange {
  
  private String to;
  private String from;

  public String getTo() { return to; }
  public void setTo(String to) { this.to = to; }
  public String getFrom() { return from; }
  public void setFrom(String from) { this.from = from; }
  
  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("to", getTo())
        .add("from", getFrom())
        .build();
  }
  
  @Override
  public String toString() {
    return "TimeRange [to=" + to + ", from=" + from + "]";
  }
  
}
