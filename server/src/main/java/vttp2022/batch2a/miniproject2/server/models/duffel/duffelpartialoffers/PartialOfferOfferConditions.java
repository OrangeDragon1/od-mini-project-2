package vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class PartialOfferOfferConditions {
  private PartialOfferOfferConditionsChangeOrRefund changeBeforeDeparture;
  private PartialOfferOfferConditionsChangeOrRefund refundBeforeDeparture;

  public PartialOfferOfferConditionsChangeOrRefund getChangeBeforeDeparture() { return changeBeforeDeparture; }
  public void setChangeBeforeDeparture(PartialOfferOfferConditionsChangeOrRefund changeBeforeDeparture) { this.changeBeforeDeparture = changeBeforeDeparture; }
  public PartialOfferOfferConditionsChangeOrRefund getRefundBeforeDeparture() { return refundBeforeDeparture; }
  public void setRefundBeforeDeparture(PartialOfferOfferConditionsChangeOrRefund refundBeforeDeparture) { this.refundBeforeDeparture = refundBeforeDeparture; }

  public static PartialOfferOfferConditions create(JsonObject jo) {
    PartialOfferOfferConditions c = new PartialOfferOfferConditions();
    if (!jo.isNull("change_before_departure"))
      c.setChangeBeforeDeparture(PartialOfferOfferConditionsChangeOrRefund.create(jo.getJsonObject("change_before_departure")));
    if (!jo.isNull("refund_before_departure"))
      c.setRefundBeforeDeparture(PartialOfferOfferConditionsChangeOrRefund.create(jo.getJsonObject("refund_before_departure")));
    return c;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    if (null != changeBeforeDeparture)
      objBuilder.add("changeBeforeDeparture", getChangeBeforeDeparture().toJson());
    if (null != refundBeforeDeparture)
      objBuilder.add("refundBeforeDeparture", getRefundBeforeDeparture().toJson());
    return objBuilder.build();
  }
}
