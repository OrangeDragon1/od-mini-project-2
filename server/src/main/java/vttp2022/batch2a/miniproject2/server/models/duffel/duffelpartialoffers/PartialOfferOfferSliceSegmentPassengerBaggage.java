package vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.models.duffel.Enum.BaggageType;

public class PartialOfferOfferSliceSegmentPassengerBaggage {
  private BaggageType type;
  private Integer quantity;

  public BaggageType getType() { return type; }
  public void setType(BaggageType type) { this.type = type; }
  public Integer getQuantity() { return quantity; }
  public void setQuantity(Integer quantity) { this.quantity = quantity; }

  public static PartialOfferOfferSliceSegmentPassengerBaggage create(JsonObject jo) {
    PartialOfferOfferSliceSegmentPassengerBaggage b = new PartialOfferOfferSliceSegmentPassengerBaggage();
    b.setType(BaggageType.valueOf(jo.getString("type")));
    b.setQuantity(jo.getInt("quantity"));
    return b;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("type", getType().name())
        .add("quantity", getQuantity())
        .build();
  }
}
