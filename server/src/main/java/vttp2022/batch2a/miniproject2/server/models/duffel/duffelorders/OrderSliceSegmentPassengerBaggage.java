package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class OrderSliceSegmentPassengerBaggage {
  private String type;
  private Integer quantity;

  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public Integer getQuantity() { return quantity; }
  public void setQuantity(Integer quantity) { this.quantity = quantity; }

  public static OrderSliceSegmentPassengerBaggage create(JsonObject jo) {
    OrderSliceSegmentPassengerBaggage b = new OrderSliceSegmentPassengerBaggage();
    b.setType(jo.getString("type"));
    b.setQuantity(jo.getInt("quantity"));
    return b;
  }

  public static OrderSliceSegmentPassengerBaggage create(SqlRowSet rs) {
    OrderSliceSegmentPassengerBaggage b = new OrderSliceSegmentPassengerBaggage();
    b.setType(rs.getString("type"));
    b.setQuantity(rs.getInt("quantity"));
    return b;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("type", getType())
        .add("quantity", getQuantity())
        .build();
  }
  
  @Override
  public String toString() {
    return "OrderSliceSegmentPassengerBaggage [type=" + type + ", quantity=" + quantity + "]";
  }
}
