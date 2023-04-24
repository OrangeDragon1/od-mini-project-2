package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.support.rowset.SqlRowSet;

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
  private String fareBrandName; // nullable
  private String duration;
  private String destinationName;
  private String destinationIataCountryCode;
  private String destinationIataCode;
  private Integer plusDays;
  private List<OrderSliceLayover> layovers;

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
  public Integer getPlusDays() { return plusDays; }
  public void setPlusDays(Integer plusDays) { this.plusDays = plusDays; }
  public List<OrderSliceLayover> getLayovers() { return layovers; }
  public void setLayovers(List<OrderSliceLayover> layovers) { this.layovers = layovers; }

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
    if (!jo.isNull("fare_brand_name"))
      s.setFareBrandName(jo.getString("fare_brand_name"));
    if (!jo.isNull("duration"))
      s.setDuration(jo.getString("duration"));
    s.setDestinationName(jo.getJsonObject("destination").getString("name"));
    s.setDestinationIataCountryCode(jo.getJsonObject("destination").getString("iata_country_code"));
    s.setDestinationIataCode(jo.getJsonObject("destination").getString("iata_code"));

    String dateDepartingAt = s.getSegments().get(0).getDepartingAt();
    String dateArrivingAt = s.getSegments().get(s.getSegments().size() - 1).getArrivingAt();
    s.setPlusDays(ifPlusDays(dateDepartingAt, dateArrivingAt));
    s.setLayovers(ifLayovers(s.getSegments(), s.getDestinationIataCode()));

    return s;
  }

  public static OrderSlice create(SqlRowSet rs) {
    OrderSlice s = new OrderSlice();
    s.setOriginName(rs.getString("origin_name"));
    s.setOriginIataCountryCode(rs.getString("origin_iata_country_code"));
    s.setOriginIataCode(rs.getString("origin_iata_code"));
    s.setId(rs.getString("id"));
    s.setFareBrandName(rs.getString("fare_brand_name"));
    s.setDuration(rs.getString("duration"));
    s.setDestinationName(rs.getString("destination_name"));
    s.setDestinationIataCountryCode(rs.getString("destination_iata_country_code"));
    s.setDestinationIataCode(rs.getString("destination_iata_code"));

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
        .add("id", getId());
        
    if (null != fareBrandName)
      objBuilder.add("fareBrandName", getFareBrandName());
    else
      objBuilder.addNull("fareBrandName");

    if (null != duration)
      objBuilder.add("duration", getDuration());
    else
      objBuilder.addNull("duration");
    
    objBuilder
        .add("destinationName", getDestinationName())
        .add("destinationIataCountryCode", getDestinationIataCountryCode())
        .add("destinationIataCode", getDestinationIataCode())
        .add("plusDays", getPlusDays())
        .add("layovers", getLayovers().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        );

    return objBuilder.build();
  }

  public static Integer ifPlusDays(String departure, String arrival) {
    String departingDate = departure.split("T")[0];
    String arrivalDate = arrival.split("T")[0];
    long departingMilli = LocalDate.parse(departingDate).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
    long arrivingMilli = LocalDate.parse(arrivalDate).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
    long dayMilli = 24 * 60 * 60 * 1000;
    int dayDiff = (int) ((arrivingMilli - departingMilli) / dayMilli);
    return dayDiff;
  }

  public static List<OrderSliceLayover> ifLayovers(
    List<OrderSliceSegment> segments,
    String destination
  ) {
    List<OrderSliceLayover> layovers = new LinkedList<>();
    for (int i = 0; i < segments.size(); i++) {
      if (!segments.get(i).getDestinationIataCode().equals(destination)) {
        OrderSliceLayover l = new OrderSliceLayover();
        String arrivalDT = segments.get(i).getArrivingAt();
        String departingDT = segments.get(i+1).getDepartingAt();
        long arrivingMilli = Instant.parse(arrivalDT + "Z").toEpochMilli();
        long departingMilli = Instant.parse(departingDT + "Z").toEpochMilli();
        long diff = departingMilli - arrivingMilli;

        l.setDuration(Duration.ofMillis(diff).toString());
        l.setLayoverName(segments.get(i).getDestinationName());
        l.setLayoverIataCode(segments.get(i).getDestinationIataCode());
        layovers.add(l);
      }
    }
    return layovers;
  }

  @Override
  public String toString() {
    return "OrderSlice [segments=" + segments + ", originName=" + originName + ", originIataCountryCode="
        + originIataCountryCode + ", originIataCode=" + originIataCode + ", id=" + id + ", fareBrandName="
        + fareBrandName + ", duration=" + duration + ", destinationName=" + destinationName
        + ", destinationIataCountryCode=" + destinationIataCountryCode + ", destinationIataCode=" + destinationIataCode
        + ", plusDays=" + plusDays + ", layovers=" + layovers + "]";
  }
  
}
