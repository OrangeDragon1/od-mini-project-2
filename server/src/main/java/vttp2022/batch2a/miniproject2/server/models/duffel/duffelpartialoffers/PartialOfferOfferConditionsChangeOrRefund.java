package vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class PartialOfferOfferConditionsChangeOrRefund {
  private Boolean allowed;
  private String penaltyAmount; // nullable
  private String penaltyCurrency; // nullable

  public Boolean getAllowed() { return allowed; }
  public void setAllowed(Boolean allowed) { this.allowed = allowed; }
  public String getPenaltyAmount() { return penaltyAmount; }
  public void setPenaltyAmount(String penaltyAmount) { this.penaltyAmount = penaltyAmount; }
  public String getPenaltyCurrency() { return penaltyCurrency; }
  public void setPenaltyCurrency(String penaltyCurrency) { this.penaltyCurrency = penaltyCurrency; }

  public static PartialOfferOfferConditionsChangeOrRefund create(JsonObject jo) {
    PartialOfferOfferConditionsChangeOrRefund c = new PartialOfferOfferConditionsChangeOrRefund();
    c.setAllowed(jo.getBoolean("allowed"));
    if (!jo.isNull("penalty_amount"))
      c.setPenaltyAmount(jo.getString("penalty_amount"));
    if (!jo.isNull("penalty_currency"))
      c.setPenaltyCurrency(jo.getString("penalty_currency"));
    return c;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder.add("allowed", getAllowed());
    if (null != penaltyAmount)
      objBuilder.add("penaltyAmount", getPenaltyAmount());
    if (null != penaltyCurrency)
      objBuilder.add("penaltyCurrency", getPenaltyCurrency());
    return objBuilder.build();
  }
}
