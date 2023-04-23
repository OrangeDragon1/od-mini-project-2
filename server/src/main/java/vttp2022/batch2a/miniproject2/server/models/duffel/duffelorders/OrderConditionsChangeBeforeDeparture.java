package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class OrderConditionsChangeBeforeDeparture {
  private String penaltyCurrency; // nullable
  private String penaltyAmount; // nullable
  private Boolean allowed;

  public String getPenaltyCurrency() { return penaltyCurrency; }
  public void setPenaltyCurrency(String penaltyCurrency) { this.penaltyCurrency = penaltyCurrency; }
  public String getPenaltyAmount() { return penaltyAmount; }
  public void setPenaltyAmount(String penaltyAmount) { this.penaltyAmount = penaltyAmount; }
  public Boolean getAllowed() { return allowed; }
  public void setAllowed(Boolean allowed) { this.allowed = allowed; }
  
  public static OrderConditionsChangeBeforeDeparture create(JsonObject jo) {
    OrderConditionsChangeBeforeDeparture c = new OrderConditionsChangeBeforeDeparture();
    c.setAllowed(jo.getBoolean("allowed"));
    if (!c.allowed) {
      // if c.allowed is false, no need to assign the rest
      return c;
    }
    if (!jo.isNull("penalty_amount"))
      c.setPenaltyAmount(jo.getString("penalty_amount"));
    if (!jo.isNull("penalty_currency"))
      c.setPenaltyCurrency(jo.getString("penalty_currency"));
    return c;
  }

  public static OrderConditionsChangeBeforeDeparture create(SqlRowSet rs) {
    OrderConditionsChangeBeforeDeparture c = new OrderConditionsChangeBeforeDeparture();
    c.setPenaltyCurrency(rs.getString("penalty_currency"));
    c.setPenaltyAmount(rs.getString("penalty_amount"));
    c.setAllowed(rs.getBoolean("allowed"));
    return c;
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
    return "OrderConditionsChangeBeforeDeparture [penaltyCurrency=" + penaltyCurrency + ", penaltyAmount="
        + penaltyAmount + ", allowed=" + allowed + "]";
  }

}
