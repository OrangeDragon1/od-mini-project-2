package vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonString;
import jakarta.json.stream.JsonCollectors;
import vttp2022.batch2a.miniproject2.server.models.duffel.Carrier;

public class PartialOfferOffer {
  
  private String totalEmissionsKg;
  private String totalCurrency;
  private String totalAmount;
  private String taxCurrency; // nullable
  private String taxAmount; // nullable
  private List<PartialOfferOfferSlice> slices;
  private List<PartialOfferOfferPassenger> passengers;
  private boolean passengerIdentityDocumentsRequired;
  private Carrier owner;
  private String id;
  private PartialOfferOfferConditions conditions;
  private List<String> allowedPassengerIdentityDocumentTypes;
  
  public String getTotalEmissionsKg() { return totalEmissionsKg; }
  public void setTotalEmissionsKg(String totalEmissionsKg) { this.totalEmissionsKg = totalEmissionsKg; }
  public String getTotalCurrency() { return totalCurrency; }
  public void setTotalCurrency(String totalCurrency) { this.totalCurrency = totalCurrency; }
  public String getTotalAmount() { return totalAmount; }
  public void setTotalAmount(String totalAmount) { this.totalAmount = totalAmount; }
  public String getTaxCurrency() { return taxCurrency; }
  public void setTaxCurrency(String taxCurrency) { this.taxCurrency = taxCurrency; }
  public String getTaxAmount() { return taxAmount; }
  public void setTaxAmount(String taxAmount) { this.taxAmount = taxAmount; }
  public List<PartialOfferOfferSlice> getSlices() { return slices; }
  public void setSlices(List<PartialOfferOfferSlice> slices) { this.slices = slices; }
  public List<PartialOfferOfferPassenger> getPassengers() { return passengers; }
  public void setPassengers(List<PartialOfferOfferPassenger> passengers) { this.passengers = passengers; }
  public boolean isPassengerIdentityDocumentsRequired() { return passengerIdentityDocumentsRequired; }
  public void setPassengerIdentityDocumentsRequired(boolean passengerIdentityDocumentsRequired) { this.passengerIdentityDocumentsRequired = passengerIdentityDocumentsRequired; }
  public Carrier getOwner() { return owner; }
  public void setOwner(Carrier owner) { this.owner = owner; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public PartialOfferOfferConditions getConditions() { return conditions; }
  public void setConditions(PartialOfferOfferConditions conditions) { this.conditions = conditions; }
  public List<String> getAllowedPassengerIdentityDocumentTypes() { return allowedPassengerIdentityDocumentTypes; }
  public void setAllowedPassengerIdentityDocumentTypes(List<String> allowedPassengerIdentityDocumentTypes) { this.allowedPassengerIdentityDocumentTypes = allowedPassengerIdentityDocumentTypes; }

  public static PartialOfferOffer create(JsonObject jo) {
    PartialOfferOffer o = new PartialOfferOffer();
    if (!jo.isNull("total_emissions_kg"))
      o.setTotalEmissionsKg(jo.getString("total_emissions_kg"));

    o.setTotalCurrency(jo.getString("total_currency"));
    o.setTotalAmount(jo.getString("total_amount"));
    
    if (!jo.isNull("tax_currency")) 
      o.setTaxCurrency(jo.getString("tax_currency"));
    if (!jo.isNull("tax_amount"))
      o.setTaxAmount(jo.getString("tax_amount"));

    o.setSlices(jo.getJsonArray("slices").stream()
        .map(v -> PartialOfferOfferSlice.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    o.setPassengers(jo.getJsonArray("passengers").stream()
        .map(v -> PartialOfferOfferPassenger.create(v.asJsonObject()))
        .collect(Collectors.toList())
    );
    o.setPassengerIdentityDocumentsRequired(jo.getBoolean("passenger_identity_documents_required"));
    o.setOwner(Carrier.create(jo.getJsonObject("owner")));
    o.setId(jo.getString("id"));
    o.setConditions(PartialOfferOfferConditions.create(jo.getJsonObject("conditions")));
    o.setAllowedPassengerIdentityDocumentTypes(jo.getJsonArray("allowed_passenger_identity_document_types").getValuesAs(JsonString.class)
        .stream()
        .map(JsonString::getString)
        .collect(Collectors.toList())
    );
    
    // Test Codes
    // o.setPassengerIdentityDocumentsRequired(true);
    // JsonArray arr = Json.createArrayBuilder()
    //     .add("passport")
    //     .add("tax_id")
    //     .build();

    // o.setAllowedPassengerIdentityDocumentTypes(arr.getValuesAs(JsonString.class)
    //     .stream()
    //     .map(JsonString::getString)
    //     .collect(Collectors.toList())
    // );

    return o;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    if (null != totalEmissionsKg)
      objBuilder.add("totalEmissionsKg", getTotalEmissionsKg());

    objBuilder
        .add("totalCurrency", getTotalCurrency())
        .add("totalAmount", getTotalAmount());

    if (null != taxCurrency)
      objBuilder.add("taxCurrency", getTaxCurrency());
    if (null != taxAmount)
      objBuilder.add("taxAmount", getTaxAmount());

    objBuilder
        .add("slices", getSlices().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        )
        .add("passengers", getPassengers().stream()
            .map(v -> v.toJson())
            .collect(JsonCollectors.toJsonArray())
        )
        .add("passengerIdentityDocumentsRequired", isPassengerIdentityDocumentsRequired())
        .add("owner", getOwner().toJson())
        .add("id", id)
        .add("conditions", getConditions().toJson())
        .add("allowedPassengerIdentityDocumentTypes", getAllowedPassengerIdentityDocumentTypes().stream()
            .map(Json::createValue)
            .collect(JsonCollectors.toJsonArray())
        );
    
    return objBuilder.build();
  }

  @Override
  public String toString() {
    return "PartialOfferOffer [totalCurrency=" + totalCurrency + ", totalAmount=" + totalAmount + ", slices=" + slices
        + ", owner=" + owner + ", id=" + id + "]";
  }
  
}
