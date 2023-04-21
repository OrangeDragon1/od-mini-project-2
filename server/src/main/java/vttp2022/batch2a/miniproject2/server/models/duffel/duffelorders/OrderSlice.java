package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.stream.JsonCollectors;

public class OrderSlice {
  private List<OrderSliceSegment> segments;
  private String originName;
  private String originIataCountryCode;
  private String originIataCode;
  private String id;
  private String fareBrandName;
  private String duration;
  private String destinationName;
  private String destinationIataCountryCode;
  private String destinationIataCode;

  public List<OrderSliceSegment> getSegments() { return segments; }
  public void setSegments(List<OrderSliceSegment> segments) { this.segments = segments; }
  public String getOriginName() { return originName; }
  public void setOriginName(String originName) { this.originName = originName; }
  public String getOriginIataCountryCode() { return originIataCountryCode; }
  public void setOriginIataCountryCode(String originIataCountryCode) { this.originIataCountryCode = originIataCountryCode; }
  public String getOriginIataCode() { return originIataCode; }
  public void setOriginIataCode(String originIataCode) { this.originIataCode = originIataCode; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getFareBrandName() { return fareBrandName; }
  public void setFareBrandName(String fareBrandName) { this.fareBrandName = fareBrandName; }
  public String getDuration() { return duration; }
  public void setDuration(String duration) { this.duration = duration; }
  public String getDestinationName() { return destinationName; }
  public void setDestinationName(String destinationName) { this.destinationName = destinationName; }
  public String getDestinationIataCountryCode() { return destinationIataCountryCode; }
  public void setDestinationIataCountryCode(String destinationIataCountryCode) { this.destinationIataCountryCode = destinationIataCountryCode; }
  public String getDestinationIataCode() { return destinationIataCode; }
  public void setDestinationIataCode(String destinationIataCode) { this.destinationIataCode = destinationIataCode; }

  public static OrderSlice create(JsonObject jo) {
    OrderSlice s = new OrderSlice();
    s.setSegments(jo.getJsonArray("segments").stream()
        .map(v -> OrderSliceSegment.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    s.setOriginName(jo.getJsonObject("origin").getString("name"));
    s.setOriginIataCountryCode(jo.getJsonObject("origin").getString("iata_country_code"));
    s.setOriginIataCode(jo.getJsonObject("origin").getString("iata_code"));
    s.setId(jo.getString("id"));
    s.setFareBrandName(jo.getString("fare_brand_name"));
    s.setDuration(jo.getString("duration"));
    s.setDestinationName(jo.getJsonObject("destination").getString("name"));
    s.setDestinationIataCountryCode(jo.getJsonObject("destination").getString("iata_country_code"));
    s.setDestinationIataCode(jo.getJsonObject("destination").getString("iata_code"));

    return s;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder
        .add("segments", getSegments().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        )
        .add("originName", getOriginName())
        .add("originIataCountryCode", getOriginIataCountryCode())
        .add("originIataCode", getOriginIataCode())
        .add("id", getId())
        .add("fareBrandName", getFareBrandName())
        .add("duration", getDuration())
        .add("destinationName", getDestinationName())
        .add("destinationIataCountryCode", getDestinationIataCountryCode())
        .add("destinationIataCode", getDestinationIataCode());

    return objBuilder.build();
  }
  
  @Override
  public String toString() {
    return "OrderSlice [segments=" + segments + ", originName=" + originName + ", originIataCountryCode="
        + originIataCountryCode + ", originIataCode=" + originIataCode + ", id=" + id + ", fareBrandName="
        + fareBrandName + ", duration=" + duration + ", destinationName=" + destinationName
        + ", destinationIataCountryCode=" + destinationIataCountryCode + ", destinationIataCode=" + destinationIataCode
        + "]";
  }
}
