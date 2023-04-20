package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorderrequests;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class OrderRequestPayment {
  private String type;
  private String currency;
  private String amount;

  public String getType() {  return type; }
  public void setType(String type) {  this.type = type; }
  public String getCurrency() {  return currency; }
  public void setCurrency(String currency) {  this.currency = currency; }
  public String getAmount() {  return amount; }
  public void setAmount(String amount) {  this.amount = amount; }

  public static OrderRequestPayment create(JsonObject jo) {
    OrderRequestPayment r = new OrderRequestPayment();
    r.setType(jo.getString("type"));
    r.setCurrency(jo.getString("currency"));
    r.setAmount(jo.getString("amount"));
    return r;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("type", getType())
        .add("currency", getCurrency())
        .add("amount", getAmount())
        .build();
  }
  
  @Override
  public String toString() {
    return "OrderRequestPayment [type=" + type + ", currency=" + currency + ", amount=" + amount + "]";
  }

  
}
