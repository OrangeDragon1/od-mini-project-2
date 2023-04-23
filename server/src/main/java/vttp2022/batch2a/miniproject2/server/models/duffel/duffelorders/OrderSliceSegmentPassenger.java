package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.stream.JsonCollectors;

public class OrderSliceSegmentPassenger {
  private String passengerId;
  private String cabinClassMarketingName; // nullable
  private String cabinClass; // nullable
  private List<OrderSliceSegmentPassengerBaggage> baggages;

  public String getPassengerId() { return passengerId; }
  public void setPassengerId(String passengerId) { this.passengerId = passengerId; }
  public String getCabinClassMarketingName() { return cabinClassMarketingName; }
  public void setCabinClassMarketingName(String cabinClassMarketingName) { this.cabinClassMarketingName = cabinClassMarketingName; }
  public String getCabinClass() { return cabinClass; }
  public void setCabinClass(String cabinClass) { this.cabinClass = cabinClass; }
  public List<OrderSliceSegmentPassengerBaggage> getBaggages() { return baggages; }
  public void setBaggages(List<OrderSliceSegmentPassengerBaggage> baggages) { this.baggages = baggages; }

  public static OrderSliceSegmentPassenger create(JsonObject jo) {
    OrderSliceSegmentPassenger p = new OrderSliceSegmentPassenger();
    p.setPassengerId(jo.getString("passenger_id"));
    if (!jo.isNull("cabin_class_marketing_name"))
      p.setCabinClassMarketingName(jo.getString("cabin_class_marketing_name"));
    if (!jo.isNull("cabin_class"))
      p.setCabinClass(jo.getString("cabin_class"));
    p.setBaggages(jo.getJsonArray("baggages").stream()
        .map(v -> OrderSliceSegmentPassengerBaggage.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    return p;
  }

  public static OrderSliceSegmentPassenger create(SqlRowSet rs) {
    OrderSliceSegmentPassenger p = new OrderSliceSegmentPassenger();
    p.setPassengerId(rs.getString("passenger_id"));
    p.setCabinClassMarketingName(rs.getString("cabin_class_marketing_name"));
    p.setCabinClass(rs.getString("cabin_class"));
    return p;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder.add("passengerId", getPassengerId());
    if (null != cabinClassMarketingName)
      objBuilder.add("cabinClassMarketingName", getCabinClassMarketingName());
    else
      objBuilder.addNull("cabinClassMarketingName");

    if (null != cabinClass)
      objBuilder.add("cabinClass", getCabinClass());
    else
      objBuilder.addNull("cabinClass");
      
    objBuilder.add("baggages", getBaggages().stream()
        .map(v -> v.toJson())
        .collect(JsonCollectors.toJsonArray())
    );

    return objBuilder.build();
  }
  
  @Override
  public String toString() {
    return "OrderSliceSegmentPassenger [passengerId=" + passengerId + ", cabinClassMarketingName="
        + cabinClassMarketingName + ", cabinClass=" + cabinClass + ", baggages=" + baggages + "]";
  }
  
}
