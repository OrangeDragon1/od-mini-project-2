package vttp2022.batch2a.miniproject2.server.models.duffel;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Carrier {

  private String name;
  private String logoSymbolUrl; // nullable
  private String logoLockupUrl; // nullable
  private String id; 
  private String iataCode; // nullable
  private String conditionsOfCarriageUrl; // nullable

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getLogoSymbolUrl() { return logoSymbolUrl; }
  public void setLogoSymbolUrl(String logoSymbolUrl) { this.logoSymbolUrl = logoSymbolUrl; }
  public String getLogoLockupUrl() { return logoLockupUrl; }
  public void setLogoLockupUrl(String logoLockupUrl) { this.logoLockupUrl = logoLockupUrl; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getIataCode() { return iataCode; }
  public void setIataCode(String iataCode) { this.iataCode = iataCode; }
  public String getConditionsOfCarriageUrl() { return conditionsOfCarriageUrl; }
  public void setConditionsOfCarriageUrl(String conditionsOfCarriageUrl) { this.conditionsOfCarriageUrl = conditionsOfCarriageUrl; }

  public static Carrier create(JsonObject jo) {
    Carrier c = new Carrier();
    c.setName(jo.getString("name"));
    if (!jo.isNull("logo_symbol_url"))
      c.setLogoSymbolUrl(jo.getString("logo_symbol_url"));
    if (!jo.isNull("logo_lockup_url"))
      c.setLogoLockupUrl(jo.getString("logo_lockup_url"));
    c.setId(jo.getString("id"));
    if (!jo.isNull("iata_code"))
      c.setIataCode(jo.getString("iata_code"));
    if (!jo.isNull("conditions_of_carriage_url"))
      c.setConditionsOfCarriageUrl(jo.getString("conditions_of_carriage_url"));
    return c;
  }

  public JsonObject toJson() {
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder.add("name", getName());
    if (null != logoSymbolUrl)
      objBuilder.add("logoSymbolUrl", getLogoSymbolUrl());
    if (null != logoLockupUrl)
      objBuilder.add("logoLockupUrl", getLogoLockupUrl());
    objBuilder.add("id", getId());
    if (null != iataCode)
      objBuilder.add("iataCode", getIataCode());
    if (null != conditionsOfCarriageUrl)
      objBuilder.add("conditionsOfCarriageUrl", getConditionsOfCarriageUrl());
    return objBuilder.build();   
    }

  @Override
  public String toString() {
    return "Carrier [name=" + name + ", logoSymbolUrl=" + logoSymbolUrl + ", logoLockupUrl=" + logoLockupUrl + ", id="
        + id + ", iataCode=" + iataCode + ", conditionsOfCarriageUrl=" + conditionsOfCarriageUrl + "]";
  }
  
}
