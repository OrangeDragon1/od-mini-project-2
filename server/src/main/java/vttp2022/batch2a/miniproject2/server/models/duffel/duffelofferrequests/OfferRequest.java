package vttp2022.batch2a.miniproject2.server.models.duffel.duffelofferrequests;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonCollectors;
import vttp2022.batch2a.miniproject2.server.models.duffel.Enum.CabinClass;

public class OfferRequest {

  private List<OfferRequestSlice> slices;
  private List<OfferRequestPassenger> passengers;
  private Integer maxConnections;
  private CabinClass cabinClass;

  public List<OfferRequestSlice> getSlices() { return slices; }
  public void setSlices(List<OfferRequestSlice> slices) { this.slices = slices; }
  public List<OfferRequestPassenger> getPassengers() { return passengers; }
  public void setPassengers(List<OfferRequestPassenger> passengers) { this.passengers = passengers; }
  public Integer getMaxConnections() { return maxConnections; }
  public void setMaxConnections(Integer maxConnections) { this.maxConnections = maxConnections; }
  public CabinClass getCabinClass() { return cabinClass; }
  public void setCabinClass(CabinClass cabinClass) { this.cabinClass = cabinClass; }

  public static OfferRequest create(JsonObject jo) {
    OfferRequest or = new OfferRequest();
    or.setSlices(jo.getJsonArray("slices").stream()
        .map(v -> OfferRequestSlice.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    or.setPassengers(jo.getJsonArray("passengers").stream()
        .map(v -> OfferRequestPassenger.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    or.setMaxConnections(jo.getInt("maxConnections"));
    or.setCabinClass(CabinClass.valueOf(jo.getString("cabinClass")));
    return or;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("slices", slices.stream()
            .map(OfferRequestSlice::toJson)
            .collect(JsonCollectors.toJsonArray())
        )
        .add("passengers", passengers.stream()
            .map(OfferRequestPassenger::toJson)
            .collect(JsonCollectors.toJsonArray())
        )
        .add("max_connections", getMaxConnections())
        .add("cabin_class", getCabinClass().name())
        .build();
  }
  
  public JsonObject toJsonWithType() {
    return Json.createObjectBuilder()
        .add("slices", slices.stream()
            .map(OfferRequestSlice::toJson)
            .collect(JsonCollectors.toJsonArray())
        )
        .add("passengers", passengers.stream()
            .map(OfferRequestPassenger::toJsonWithType)
            .collect(JsonCollectors.toJsonArray())
        )
        .add("max_connections", getMaxConnections())
        .add("cabin_class", getCabinClass().name())
        .build();
  }

  public JsonObject toJsonWithAge() {
    return Json.createObjectBuilder()
        .add("slices", slices.stream()
            .map(OfferRequestSlice::toJson)
            .collect(JsonCollectors.toJsonArray())
        )
        .add("passengers", passengers.stream()
            .map(OfferRequestPassenger::toJsonWithAge)
            .collect(JsonCollectors.toJsonArray())
        )
        .add("max_connections", getMaxConnections())
        .add("cabin_class", getCabinClass().name())
        .build();
  }

  public JsonObject toJsonWithFareType() {
    return Json.createObjectBuilder()
        .add("slices", slices.stream()
            .map(OfferRequestSlice::toJson)
            .collect(JsonCollectors.toJsonArray())
        )
        .add("passengers", passengers.stream()
            .map(OfferRequestPassenger::toJsonWithFareType)
            .collect(JsonCollectors.toJsonArray())
        )
        .add("max_connections", getMaxConnections())
        .add("cabin_class", getCabinClass().name())
        .build();
  }

  @Override
  public String toString() {
    return "OfferRequest [slices=" + slices + ", passengers=" + passengers + ", maxConnections=" + maxConnections
        + ", cabinClass=" + cabinClass + "]";
  }
  
}
