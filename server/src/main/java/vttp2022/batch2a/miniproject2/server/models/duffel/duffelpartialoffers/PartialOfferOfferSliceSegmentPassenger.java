package vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.stream.JsonCollectors;
import vttp2022.batch2a.miniproject2.server.models.duffel.Enum.CabinClass;

public class PartialOfferOfferSliceSegmentPassenger {
  private String passengerId;
  private String fareBasisCode; // nullable
  private String cabinClassMarketingName;
  private CabinClass cabinClass;
  // private Cabin cabin; 
  private List<PartialOfferOfferSliceSegmentPassengerBaggage> baggages;

  public String getPassengerId() { return passengerId; }
  public void setPassengerId(String passengerId) { this.passengerId = passengerId; }
  public String getFareBasisCode() { return fareBasisCode; }
  public void setFareBasisCode(String fareBasisCode) { this.fareBasisCode = fareBasisCode; }
  public String getCabinClassMarketingName() { return cabinClassMarketingName; }
  public void setCabinClassMarketingName(String cabinClassMarketingName) { this.cabinClassMarketingName = cabinClassMarketingName; }
  public CabinClass getCabinClass() { return cabinClass; }
  public void setCabinClass(CabinClass cabinClass) { this.cabinClass = cabinClass; }
  public List<PartialOfferOfferSliceSegmentPassengerBaggage> getBaggages() { return baggages; }
  public void setBaggages(List<PartialOfferOfferSliceSegmentPassengerBaggage> baggages) { this.baggages = baggages; }

  public static PartialOfferOfferSliceSegmentPassenger create(JsonObject jo) {
    PartialOfferOfferSliceSegmentPassenger p = new PartialOfferOfferSliceSegmentPassenger();
    p.setPassengerId(jo.getString("passenger_id"));
    if (!jo.isNull("fare_basis_code"))
      p.setFareBasisCode(jo.getString("fare_basis_code"));
    p.setCabinClassMarketingName(jo.getString("cabin_class_marketing_name"));
    p.setCabinClass(CabinClass.valueOf(jo.getString("cabin_class")));
    p.setBaggages(jo.getJsonArray("baggages").stream()
        .map(v -> PartialOfferOfferSliceSegmentPassengerBaggage.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    return p;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder.add("passengerId", getPassengerId());
    if (null != fareBasisCode) 
      objBuilder.add("fareBasisCode", getFareBasisCode());
    objBuilder
        .add("cabinClassMarketingName", getCabinClassMarketingName())
        .add("cabinClass", getCabinClass().name())
        .add("baggages", getBaggages().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        );
    return objBuilder.build();
  }
}
