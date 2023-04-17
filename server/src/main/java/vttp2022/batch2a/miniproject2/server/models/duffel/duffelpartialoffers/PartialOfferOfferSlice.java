package vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.catalina.filters.AddDefaultCharsetFilter;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.stream.JsonCollectors;

public class PartialOfferOfferSlice {
  
  private List<PartialOfferOfferSliceSegment> segments;
  private String originIataCode;
  private String id;
  private String fareBrandName; // nullable
  private String duration; // nullable
  private String destinationIataCode;
  private Integer plusDays;
  private List<PartialOfferOfferSliceLayover> layovers;

  public List<PartialOfferOfferSliceSegment> getSegments() { return segments; }
  public void setSegments(List<PartialOfferOfferSliceSegment> segments) { this.segments = segments; }
  public String getOriginIataCode() { return originIataCode; }
  public void setOriginIataCode(String originIataCode) { this.originIataCode = originIataCode; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getFareBrandName() { return fareBrandName; }
  public void setFareBrandName(String fareBrandName) { this.fareBrandName = fareBrandName; }
  public String getDuration() { return duration; }
  public void setDuration(String duration) { this.duration = duration; }
  public String getDestinationIataCode() { return destinationIataCode; }
  public void setDestinationIataCode(String destinationIataCode) { this.destinationIataCode = destinationIataCode; }
  public Integer getPlusDays() { return plusDays; }
  public void setPlusDays(Integer plusDays) { this.plusDays = plusDays; }
  public List<PartialOfferOfferSliceLayover> getLayovers() { return layovers; }
  public void setLayovers(List<PartialOfferOfferSliceLayover> layovers) { this.layovers = layovers; }
  
  public static PartialOfferOfferSlice create(JsonObject jo) {
    PartialOfferOfferSlice s = new PartialOfferOfferSlice();
    s.setSegments(jo.getJsonArray("segments").stream()
        .map(v -> PartialOfferOfferSliceSegment.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    s.setOriginIataCode(jo.getJsonObject("origin").getString("iata_code"));
    s.setId(jo.getString("id"));
    if (!jo.isNull("fare_brand_name"))
      s.setFareBrandName(jo.getString("fare_brand_name"));
    if (!jo.isNull("duration"))
      s.setDuration(jo.getString("duration"));
    s.setDestinationIataCode(jo.getJsonObject("destination").getString("iata_code"));

    String dateDepartingAt = s.getSegments().get(0).getDepartingAt();
    String dateArrivingAt = s.getSegments().get(s.getSegments().size() - 1).getArrivingAt();
    s.setPlusDays(ifPlusDays(dateDepartingAt, dateArrivingAt));
    s.setLayovers(ifLayovers(s.getSegments(), s.getDestinationIataCode()));
    return s;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder
        .add("segments", getSegments().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        )
        .add("originIataCode", getOriginIataCode())
        .add("id", getId());
    if (null != fareBrandName)
      objBuilder.add("fareBrandName", getFareBrandName());
    if (null != duration)
      objBuilder.add("duration", getDuration());
    objBuilder
        .add("destinationIataCode", getDestinationIataCode())
        .add("plusDays", getPlusDays())
        .add("layovers", getLayovers().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        );
    return objBuilder.build();
  }

  private static Integer ifPlusDays(String departure, String arrival) {
    String departingDate = departure.split("T")[0];
    String arrivalDate = arrival.split("T")[0];
    long departingMilli = LocalDate.parse(departingDate).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
    long arrivingMilli = LocalDate.parse(arrivalDate).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
    long dayMilli = 24 * 60 * 60 * 1000;
    int dayDiff = (int) ((arrivingMilli - departingMilli) / dayMilli);
    return dayDiff;
  }

  private static List<PartialOfferOfferSliceLayover> ifLayovers(
    List<PartialOfferOfferSliceSegment> segments,
    String destination
  ) {
    List<PartialOfferOfferSliceLayover> layovers = new LinkedList<>();
    for (int i = 0; i < segments.size(); i++) {
      if (!segments.get(i).getDestinationIataCode().equals(destination)) {
        PartialOfferOfferSliceLayover l = new PartialOfferOfferSliceLayover();
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
    return "PartialOfferOfferSlice [segments=" + segments + ", originIataCode=" + originIataCode + ", id=" + id
        + ", duration=" + duration + ", destinationIataCode=" + destinationIataCode + "]";
  }
  
  
}
