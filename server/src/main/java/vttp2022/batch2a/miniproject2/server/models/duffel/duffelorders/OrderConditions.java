package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class OrderConditions {
  private OrderConditionsRefundBeforeDeparture refundBeforeDeparture; // nullable
  private OrderConditionsChangeBeforeDeparture changeBeforeDeparture; // nullable

  public OrderConditionsRefundBeforeDeparture getRefundBeforeDeparture() { return refundBeforeDeparture; }
  public void setRefundBeforeDeparture(OrderConditionsRefundBeforeDeparture refundBeforeDeparture) { this.refundBeforeDeparture = refundBeforeDeparture; }
  public OrderConditionsChangeBeforeDeparture getChangeBeforeDeparture() { return changeBeforeDeparture; }
  public void setChangeBeforeDeparture(OrderConditionsChangeBeforeDeparture changeBeforeDeparture) { this.changeBeforeDeparture = changeBeforeDeparture; }

  public static OrderConditions create(JsonObject jo) {
    OrderConditions c = new OrderConditions();
    if (!jo.isNull("refund_before_departure"))
      c.setRefundBeforeDeparture(OrderConditionsRefundBeforeDeparture.create(jo.getJsonObject("refund_before_departure")));
    if (!jo.isNull("change_before_departure"))
      c.setChangeBeforeDeparture(OrderConditionsChangeBeforeDeparture.create(jo.getJsonObject("change_before_departure")));
    return c;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    if (null != refundBeforeDeparture)
      objBuilder.add("refundBeforeDeparture", getRefundBeforeDeparture().toJson());
    else
      objBuilder.addNull("refundBeforeDeparture");
    if (null != changeBeforeDeparture)
      objBuilder.add("changeBeforeDeparture", getChangeBeforeDeparture().toJson());
    else
      objBuilder.addNull("changeBeforeDeparture");
      
    return objBuilder.build();
  }
  
  @Override
  public String toString() {
    return "OrderConditions [refundBeforeDeparture=" + refundBeforeDeparture + ", changeBeforeDeparture="
        + changeBeforeDeparture + "]";
  }

}
