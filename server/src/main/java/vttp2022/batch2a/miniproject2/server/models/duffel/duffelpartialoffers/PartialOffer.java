package vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonCollectors;
import vttp2022.batch2a.miniproject2.server.models.duffel.Enum.CabinClass;

public class PartialOffer {

  private List<PartialOfferSlice> slices;
  private List<PartialOfferPassenger> passengers;
  private List<PartialOfferOffer> offers;
  private String id;
  private CabinClass cabinClass;

  public List<PartialOfferSlice> getSlices() { return slices; }
  public void setSlices(List<PartialOfferSlice> slices) { this.slices = slices; }
  public List<PartialOfferPassenger> getPassengers() { return passengers; }
  public void setPassengers(List<PartialOfferPassenger> passengers) { this.passengers = passengers; }
  public List<PartialOfferOffer> getOffers() { return offers; }
  public void setOffers(List<PartialOfferOffer> offers) { this.offers = offers; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public CabinClass getCabinClass() { return cabinClass; }
  public void setCabinClass(CabinClass cabinClass) { this.cabinClass = cabinClass; }

  public static PartialOffer create(JsonObject jo) {
    PartialOffer o = new PartialOffer();
    o.setSlices(jo.getJsonArray("slices").stream()
        .map(v -> PartialOfferSlice.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    o.setPassengers(jo.getJsonArray("passengers").stream()
        .map(v -> PartialOfferPassenger.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    o.setOffers(jo.getJsonArray("offers").stream()
        .map(v -> PartialOfferOffer.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    o.setId(jo.getString("id"));
    o.setCabinClass(CabinClass.valueOf(jo.getString("cabin_class")));
    return o;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("slices", getSlices().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        )
        .add("passengers", getPassengers().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        )
        .add("offers", getOffers().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        )
        .add("id", getId())
        .add("cabinClass", getCabinClass().name())
        .build();
  }

  @Override
  public String toString() {
    return "PartialOffer [slices=" + slices + ", passengers=" + passengers + ", offers=" + offers + ", id=" + id
        + ", cabinClass=" + cabinClass + "]";
  }
  
}
