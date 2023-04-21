package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.stream.JsonCollectors;
import vttp2022.batch2a.miniproject2.server.models.duffel.Aircraft;
import vttp2022.batch2a.miniproject2.server.models.duffel.Carrier;

public class OrderSliceSegment {
  private List<OrderSliceSegmentPassenger> passengers;
  private String originTerminal;
  private String originName;
  private String originIataCountryCode;
  private String originIataCode;
  private String operatingCarrierFlightNumber;
  private Carrier operatingCarrier;
  private String marketingCarrierFlightNumber;
  private Carrier marketingCarrier;
  private String id;
  private String duration;
  private String distance; // in kilometers
  private String destinationTerminal;
  private String destinationName;
  private String destinationIataCountryCode;
  private String destinationIataCode;
  private String departingAt;
  private String arrivingAt;
  private Aircraft aircraft;

  public List<OrderSliceSegmentPassenger> getPassengers() { return passengers; }
  public void setPassengers(List<OrderSliceSegmentPassenger> passengers) { this.passengers = passengers; }
  public String getOriginTerminal() { return originTerminal; }
  public void setOriginTerminal(String originTerminal) { this.originTerminal = originTerminal; }
  public String getOriginName() { return originName; }
  public void setOriginName(String originName) { this.originName = originName; }
  public String getOriginIataCountryCode() { return originIataCountryCode; }
  public void setOriginIataCountryCode(String originIataCountryCode) { this.originIataCountryCode = originIataCountryCode; }
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
  public String getDistance() { return distance; }
  public void setDistance(String distance) { this.distance = distance; }
  public String getDestinationTerminal() { return destinationTerminal; }
  public void setDestinationTerminal(String destinationTerminal) { this.destinationTerminal = destinationTerminal; }
  public String getDestinationName() { return destinationName; }
  public void setDestinationName(String destinationName) { this.destinationName = destinationName; }
  public String getDestinationIataCountryCode() { return destinationIataCountryCode; }
  public void setDestinationIataCountryCode(String destinationIataCountryCode) { this.destinationIataCountryCode = destinationIataCountryCode; }
  public String getDestinationIataCode() { return destinationIataCode; }
  public void setDestinationIataCode(String destinationIataCode) { this.destinationIataCode = destinationIataCode; }
  public String getDepartingAt() { return departingAt; }
  public void setDepartingAt(String departingAt) { this.departingAt = departingAt; }
  public String getArrivingAt() { return arrivingAt; }
  public void setArrivingAt(String arrivingAt) { this.arrivingAt = arrivingAt; }
  public Aircraft getAircraft() { return aircraft; }
  public void setAircraft(Aircraft aircraft) { this.aircraft = aircraft; }

  public static OrderSliceSegment create(JsonObject jo) {
    OrderSliceSegment s = new OrderSliceSegment();
    s.setPassengers(jo.getJsonArray("passengers").stream()
        .map(v -> OrderSliceSegmentPassenger.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    s.setOriginTerminal(jo.getString("origin_terminal"));
    s.setOriginName(jo.getJsonObject("origin").getString("name"));
    s.setOriginIataCountryCode(jo.getJsonObject("origin").getString("iata_country_code"));
    s.setOriginIataCode(jo.getJsonObject("origin").getString("iata_code"));
    s.setOperatingCarrierFlightNumber("operating_carrier_flight_number");
    s.setOperatingCarrier(Carrier.create(jo.getJsonObject("operating_carrier")));
    s.setMarketingCarrierFlightNumber(jo.getString("marketing_carrier_flight_number"));
    s.setMarketingCarrier(Carrier.create(jo.getJsonObject("marketing_carrier")));
    s.setId(jo.getString("id"));
    s.setDuration(jo.getString("duration"));
    s.setDistance(jo.getString("distance"));
    s.setDestinationTerminal(jo.getString("destination_terminal"));
    s.setDestinationName(jo.getJsonObject("destination").getString("name"));
    s.setDestinationIataCountryCode(jo.getJsonObject("destination").getString("iata_country_code"));
    s.setDestinationIataCode(jo.getJsonObject("destination").getString("iata_code"));
    s.setDepartingAt(jo.getString("departing_at"));
    s.setArrivingAt(jo.getString("arriving_at"));
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
        .add("originTerminal", getOriginTerminal())
        .add("originName", getOriginName())
        .add("originIataCountryCode", getOriginIataCountryCode())
        .add("originIataCode", getOriginIataCode())
        .add("operatingCarrierFlightNumber", getOperatingCarrierFlightNumber())
        .add("operatingCarrier", getOperatingCarrier().toJson())
        .add("marketingCarrierFlightNumber", getMarketingCarrierFlightNumber())
        .add("marketingCarrier", getMarketingCarrier().toJson())
        .add("id", getId())
        .add("duration", getDuration())
        .add("distance", getDistance())
        .add("destinationTerminal", getDestinationTerminal())
        .add("destinationName", getDestinationName())
        .add("destinationIataCountryCode", getDestinationIataCountryCode())
        .add("destinationIataCode", getDestinationIataCode())
        .add("departingAt", getDepartingAt())
        .add("arrivingAt", getArrivingAt())
        .add("aircraft", getAircraft().toJson());
        
    return objBuilder.build();
  }
  
  @Override
  public String toString() {
    return "OrderSliceSegment [passengers=" + passengers + ", originTerminal=" + originTerminal + ", originName="
        + originName + ", originIataCountryCode=" + originIataCountryCode + ", originIataCode=" + originIataCode
        + ", operatingCarrierFlightNumber=" + operatingCarrierFlightNumber + ", operatingCarrier=" + operatingCarrier
        + ", marketingCarrierFlightNumber=" + marketingCarrierFlightNumber + ", marketingCarrier=" + marketingCarrier
        + ", id=" + id + ", duration=" + duration + ", distance=" + distance + ", destinationTerminal="
        + destinationTerminal + ", destinationName=" + destinationName + ", destinationIataCountryCode="
        + destinationIataCountryCode + ", destinationIataCode=" + destinationIataCode + ", departingAt=" + departingAt
        + ", arrivingAt=" + arrivingAt + ", aircraft=" + aircraft + "]";
  }
}
