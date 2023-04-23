package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class OrderConditionsRefundBeforeDeparture {
  private String penaltyCurrency; // nullable
  private String penaltyAmount; // nullable
  private Boolean allowed;

  public String getPenaltyCurrency() { return penaltyCurrency; }
  public void setPenaltyCurrency(String penaltyCurrency) { this.penaltyCurrency = penaltyCurrency; }
  public String getPenaltyAmount() { return penaltyAmount; }
  public void setPenaltyAmount(String penaltyAmount) { this.penaltyAmount = penaltyAmount; }
  public Boolean getAllowed() { return allowed; }
  public void setAllowed(Boolean allowed) { this.allowed = allowed; }

  public static OrderConditionsRefundBeforeDeparture create(JsonObject jo) {
    OrderConditionsRefundBeforeDeparture r = new OrderConditionsRefundBeforeDeparture();
    r.setAllowed(jo.getBoolean("allowed"));
    if (!r.allowed) {
      // if c.allowed is false, no need to assign the rest
      return r;
    }
    if (!jo.isNull("penalty_amount"))
      r.setPenaltyAmount(jo.getString("penalty_amount"));
    if (!jo.isNull("penalty_currency"))
      r.setPenaltyCurrency(jo.getString("penalty_currency"));
    return r;
  }

  public static OrderConditionsRefundBeforeDeparture create(SqlRowSet rs) {
    OrderConditionsRefundBeforeDeparture r = new OrderConditionsRefundBeforeDeparture();
    r.setPenaltyCurrency(rs.getString("penalty_currency"));
    r.setPenaltyAmount(rs.getString("penalty_amount"));
    r.setAllowed(rs.getBoolean("allowed"));
    return r;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    if (null != penaltyCurrency)
      objBuilder.add("penaltyCurrency", getPenaltyCurrency());
    else
      objBuilder.addNull("penaltyCurrency");
    if (null != penaltyAmount)
      objBuilder.add("penaltyAmount", getPenaltyAmount());
    else
      objBuilder.addNull("penaltyAmount");
    objBuilder.add("allowed", getAllowed());
    return objBuilder.build();
  }
  
  @Override
  public String toString() {
    return "OrderConditionsRefundBeforeDeparture [penaltyCurrency=" + penaltyCurrency + ", penaltyAmount="
        + penaltyAmount + ", allowed=" + allowed + "]";
  }
  
}
