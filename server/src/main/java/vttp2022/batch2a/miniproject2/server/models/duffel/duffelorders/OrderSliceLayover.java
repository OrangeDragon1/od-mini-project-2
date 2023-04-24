package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class OrderSliceLayover {
  private String duration;
  private String layoverName;
  private String layoverIataCode;

  public String getDuration() { return duration; }
  public void setDuration(String duration) { this.duration = duration; }
  public String getLayoverName() { return layoverName; }
  public void setLayoverName(String layoverName) { this.layoverName = layoverName; }
  public String getLayoverIataCode() { return layoverIataCode; }
  public void setLayoverIataCode(String layoverIataCode) { this.layoverIataCode = layoverIataCode; }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("duration", getDuration())
        .add("layoverName", getLayoverName())
        .add("layoverIataCode", getLayoverIataCode())
        .build();
  }
  
  @Override
  public String toString() {
    return "OrderSliceLayover [duration=" + duration + ", layoverName=" + layoverName + ", layoverIataCode="
        + layoverIataCode + "]";
  }

}
