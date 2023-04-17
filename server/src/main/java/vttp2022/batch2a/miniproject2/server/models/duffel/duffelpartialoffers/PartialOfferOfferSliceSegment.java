package vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.stream.JsonCollectors;
import vttp2022.batch2a.miniproject2.server.models.duffel.Aircraft;
import vttp2022.batch2a.miniproject2.server.models.duffel.Carrier;

public class PartialOfferOfferSliceSegment {
  
  private List<PartialOfferOfferSliceSegmentPassenger> passengers;
  private String originTimeZone;
  private String originName;
  private String originIataCode;
  private String operatingCarrierFlightNumber; // nullable
  private Carrier operatingCarrier;
  private String marketingCarrierFlightNumber;
  private Carrier marketingCarrier;
  private String id; 
  private String duration;
  private String destinationTimeZone;
  private String destinationName;
  private String destinationIataCode;
  private String departingAt;
  private String arrivingAt;
  private Aircraft aircraft; // nullable
  
  public List<PartialOfferOfferSliceSegmentPassenger> getPassengers() { return passengers; }
  public void setPassengers(List<PartialOfferOfferSliceSegmentPassenger> passengers) { this.passengers = passengers; }
  public String getOriginName() { return originName; }
  public String getOriginTimeZone() { return originTimeZone; }
  public void setOriginTimeZone(String originTimeZone) { this.originTimeZone = originTimeZone; }
  public void setOriginName(String originName) { this.originName = originName; }
  public String getOriginIataCode() { return originIataCode; }
  public void setOriginIataCode(String originIataCode) { this.originIataCode = originIataCode; }
  public String getOperatingCarrierFlightNumber() { return operatingCarrierFlightNumber; }
  public void setOperatingCarrierFlightNumber(String operatingCarrierFlightNumber) { this.operatingCarrierFlightNumber = operatingCarrierFlightNumber; }
  public Carrier getOperatingCarrier() { return operatingCarrier; }
  public void setOperatingCarrier(Carrier operatingCarrier) { this.operatingCarrier = operatingCarrier; }
  public String getMarketingCarrierFlightNumber() { return marketingCarrierFlightNumber; }
  public void setMarketingCarrierFlightNumber(String marketingCarrierFlightNumber) { this.marketingCarrierFlightNumber = marketingCarrierFlightNumber; }
  public Carrier getMarketingCarrier() { return marketingCarrier; }
  public void setMarketingCarrier(Carrier marketingCarrier) { this.marketingCarrier = marketingCarrier; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getDuration() { return duration; }
  public void setDuration(String duration) { this.duration = duration; }
  public String getDestinationTimeZone() { return destinationTimeZone; }
  public void setDestinationTimeZone(String destinationTimeZone) { this.destinationTimeZone = destinationTimeZone; }
  public String getDestinationName() { return destinationName; }
  public void setDestinationName(String destinationName) { this.destinationName = destinationName; }
  public String getDestinationIataCode() { return destinationIataCode; }
  public void setDestinationIataCode(String destinationIataCode) { this.destinationIataCode = destinationIataCode; }
  public String getDepartingAt() { return departingAt; }
  public void setDepartingAt(String departingAt) { this.departingAt = departingAt; }
  public String getArrivingAt() { return arrivingAt; }
  public void setArrivingAt(String arrivingAt) { this.arrivingAt = arrivingAt; }
  public Aircraft getAircraft() { return aircraft; }
  public void setAircraft(Aircraft aircraft) { this.aircraft = aircraft; }

  public static PartialOfferOfferSliceSegment create(JsonObject jo) {
    PartialOfferOfferSliceSegment s = new PartialOfferOfferSliceSegment();
    s.setPassengers(jo.getJsonArray("passengers").stream()
        .map(v -> PartialOfferOfferSliceSegmentPassenger.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    s.setOriginName(jo.getJsonObject("origin").getString("name"));
    s.setOriginIataCode(jo.getJsonObject("origin").getString("iata_code"));
    if (!jo.isNull("operating_carrier_flight_number"))
      s.setOperatingCarrierFlightNumber(jo.getString("operating_carrier_flight_number"));
    s.setOperatingCarrier(Carrier.create(jo.getJsonObject("operating_carrier")));
    s.setMarketingCarrierFlightNumber(jo.getString("marketing_carrier_flight_number"));
    s.setMarketingCarrier(Carrier.create(jo.getJsonObject("marketing_carrier")));
    s.setId(jo.getString("id"));
    s.setDuration(jo.getString("duration"));
    s.setDestinationName(jo.getJsonObject("destination").getString("name"));
    s.setDestinationIataCode(jo.getJsonObject("destination").getString("iata_code"));
    s.setDepartingAt(jo.getString("departing_at"));
    s.setArrivingAt(jo.getString("arriving_at"));
    if (!jo.isNull("aircraft"))
      s.setAircraft(Aircraft.create(jo.getJsonObject("aircraft")));
    return s;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder
        .add("passengers", getPassengers().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        )
        .add("originName", getOriginName())
        .add(("originIataCode"), getOriginIataCode());
    if (null != operatingCarrierFlightNumber)
      objBuilder.add("operatingCarrierFlightNumber", getOperatingCarrierFlightNumber());
    objBuilder
        .add("operatingCarrier", getOperatingCarrier().toJson())
        .add("marketingCarrierFlightNumber", getMarketingCarrierFlightNumber())
        .add("marketingCarrier", getMarketingCarrier().toJson())
        .add("id", getId())
        .add("duration", getDuration())
        .add("destinationName", getDestinationName())
        .add("destinationIataCode", getDestinationIataCode())
        .add("departingAt", getDepartingAt())
        .add("arrivingAt", getArrivingAt());
    if (null != aircraft) 
      objBuilder.add("aircraft", getAircraft().toJson());
    return objBuilder.build();
  }

  @Override
  public String toString() {
    return "PartialOfferOfferSliceSegment [originName=" + originName + ", originIataCode=" + originIataCode
        + ", operatingCarrierFlightNumber=" + operatingCarrierFlightNumber + ", operatingCarrier=" + operatingCarrier
        + ", id=" + id + ", duration=" + duration + ", destinationName=" + destinationName + ", destinationIataCode="
        + destinationIataCode + ", departingAt=" + departingAt + ", arrivingAt=" + arrivingAt + ", aircraft=" + aircraft
        + "]";
  }

}
