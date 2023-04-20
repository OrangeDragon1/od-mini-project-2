package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorderrequests;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.stream.JsonCollectors;

public class OrderRequest {
  private String type;
  private List<String> selectedOffers;
  private List<OrderRequestPayment> payments;
  private List<OrderRequestPassenger> passengers;
  private OrderRequestMetaData metaData; // nullable

  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public List<String> getSelectedOffers() { return selectedOffers; }
  public void setSelectedOffers(List<String> selectedOffers) { this.selectedOffers = selectedOffers; }
  public List<OrderRequestPayment> getPayments() { return payments; }
  public void setPayments(List<OrderRequestPayment> payments) { this.payments = payments; }
  public List<OrderRequestPassenger> getPassengers() { return passengers; }
  public void setPassengers(List<OrderRequestPassenger> passengers) { this.passengers = passengers; }
  public OrderRequestMetaData getMetaData() { return metaData; }
  public void setMetaData(OrderRequestMetaData metaData) { this.metaData = metaData; }

  public static OrderRequest create(JsonObject jo) {
    OrderRequest r = new OrderRequest();
    r.setType(jo.getString("type"));
    r.setSelectedOffers(jo.getJsonArray("selectedOffers")
        .getValuesAs(JsonString.class)
        .stream()
        .map(JsonString::getString)
        .collect(Collectors.toList())
    );
    r.setPayments(jo.getJsonArray("payments").stream()
        .map(v -> OrderRequestPayment.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    r.setPassengers(jo.getJsonArray("passengers").stream()
        .map(v -> OrderRequestPassenger.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    return r;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("type", getType())
        .add("selected_offers", getSelectedOffers().stream()
            .map(Json::createValue)
            .collect(JsonCollectors.toJsonArray())
        )
        .add("payments", getPayments().stream()
            .map(OrderRequestPayment::toJson)
            .collect(JsonCollectors.toJsonArray())
        )
        .add("passengers", getPassengers().stream()
            .map(OrderRequestPassenger::toJson)
            .collect(JsonCollectors.toJsonArray())
        )
        .build();
  }
  
  @Override
  public String toString() {
    return "OrderRequest [type=" + type + ", selectedOffers=" + selectedOffers + ", payments=" + payments
        + ", passengers=" + passengers + ", metaData=" + metaData + "]";
  }
}
