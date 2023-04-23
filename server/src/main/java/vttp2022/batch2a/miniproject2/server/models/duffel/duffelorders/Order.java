package vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.stream.JsonCollectors;
import vttp2022.batch2a.miniproject2.server.models.duffel.Carrier;

public class Order {
  private String totalCurrency;
  private String totalAmount;
  private String taxCurrency; // nullable
  private String taxAmount; // nullable
  private List<OrderSlice> slices;
  private List<OrderPassenger> passengers;
  private Carrier owner;
  private String id;
  private String createdAt;
  private OrderConditions conditions;
  private String bookingReference;
  private String baseCurrency; // nullable
  private String baseAmount; // nullable
  
  public String getTotalCurrency() { return totalCurrency; }
  public void setTotalCurrency(String totalCurrency) { this.totalCurrency = totalCurrency; }
  public String getTotalAmount() { return totalAmount; }
  public void setTotalAmount(String totalAmount) { this.totalAmount = totalAmount; }
  public String getTaxCurrency() { return taxCurrency; }
  public void setTaxCurrency(String taxCurrency) { this.taxCurrency = taxCurrency; }
  public String getTaxAmount() { return taxAmount; }
  public void setTaxAmount(String taxAmount) { this.taxAmount = taxAmount; }
  public List<OrderSlice> getSlices() { return slices; }
  public void setSlices(List<OrderSlice> slices) { this.slices = slices; }
  public List<OrderPassenger> getPassengers() { return passengers; }
  public void setPassengers(List<OrderPassenger> passengers) { this.passengers = passengers; }
  public Carrier getOwner() { return owner; }
  public void setOwner(Carrier owner) { this.owner = owner; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getCreatedAt() { return createdAt; }
  public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
  public OrderConditions getConditions() { return conditions; }
  public void setConditions(OrderConditions conditions) { this.conditions = conditions; }
  public String getBookingReference() { return bookingReference; }
  public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
  public String getBaseCurrency() { return baseCurrency; }
  public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
  public String getBaseAmount() { return baseAmount; }
  public void setBaseAmount(String baseAmount) { this.baseAmount = baseAmount; }

  public static Order create(JsonObject jo) {
    Order o = new Order();
    o.setTotalCurrency(jo.getString("total_currency"));
    o.setTotalAmount(jo.getString("total_amount"));
    if (!jo.isNull("tax_currency")) 
      o.setTaxCurrency(jo.getString("tax_currency"));
    if (!jo.isNull("tax_amount")) 
      o.setTaxAmount(jo.getString("tax_amount"));
    o.setSlices(jo.getJsonArray("slices").stream()
        .map(v -> OrderSlice.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    o.setPassengers(jo.getJsonArray("passengers").stream()
        .map(v -> OrderPassenger.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    o.setOwner(Carrier.create(jo.getJsonObject("owner")));
    o.setId(jo.getString("id"));
    o.setCreatedAt(jo.getString("created_at"));
    o.setConditions(OrderConditions.create(jo.getJsonObject("conditions")));
    o.setBookingReference(jo.getString("booking_reference"));
    if (!jo.isNull("base_currency")) 
      o.setBaseCurrency(jo.getString("base_currency"));
    if (!jo.isNull("base_amount"))
      o.setBaseAmount(jo.getString("base_amount"));
    return o;
  }

  public static Order create(SqlRowSet rs) {
    Order o = new Order();
    o.setTotalCurrency(rs.getString("total_currency"));
    o.setTotalAmount(rs.getString("total_amount"));
    o.setTaxCurrency(rs.getString("tax_currency"));
    o.setTaxAmount(rs.getString("tax_amount"));
    o.setId(rs.getString("id"));
    o.setCreatedAt(rs.getString("created_at"));
    o.setBookingReference(rs.getString("booking_reference"));
    o.setBaseCurrency(rs.getString("base_currency"));
    o.setBaseAmount(rs.getString("base_amount"));
    return o;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder
        .add("totalCurrency", getTotalCurrency())
        .add("totalAmount", getTotalAmount());

    if (null != taxCurrency)
      objBuilder.add("taxCurrency", getTaxCurrency());
    else
      objBuilder.addNull("taxCurrency");

    if (null != taxAmount)
      objBuilder.add("taxAmount", getTaxAmount());
    else
      objBuilder.addNull("taxAmount");

    objBuilder
        .add("slices", getSlices().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        )
        .add("passengers", getPassengers().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        )
        .add("owner", getOwner().toJson())
        .add("id", getId())
        .add("createdAt", getCreatedAt())
        .add("conditions", getConditions().toJson())
        .add("bookingReference", getBookingReference());
    
    if (null != baseCurrency)
      objBuilder.add("baseCurrency", getBaseCurrency());
    else
      objBuilder.addNull("baseCurrency");
    
    if (null != baseAmount)
      objBuilder.add("baseAmount", getBaseAmount());
    else
      objBuilder.addNull("baseAmount");
    return objBuilder.build();
  }
  
  @Override
  public String toString() {
    return "Order [totalCurrency=" + totalCurrency + ", totalAmount=" + totalAmount + ", taxCurrency=" + taxCurrency
        + ", taxAmount=" + taxAmount + ", slices=" + slices + ", passengers=" + passengers + ", owner=" + owner
        + ", id=" + id + ", createdAt=" + createdAt + ", conditions=" + conditions + ", bookingReference="
        + bookingReference + ", baseCurrency=" + baseCurrency + ", baseAmount=" + baseAmount + "]";
  }
}
