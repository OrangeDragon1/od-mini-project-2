package vttp2022.batch2a.miniproject2.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Address {
  
  private String cityName;
  private String cityCode;
  private String countryName;
  private String countryCode;
  private String stateCode;
  private String regionCode;

  public String getCityName() { return cityName; }
  public void setCityName(String cityName) { this.cityName = cityName; }
  public String getCityCode() { return cityCode; }
  public void setCityCode(String cityCode) { this.cityCode = cityCode; }
  public String getCountryName() { return countryName; }
  public void setCountryName(String countryName) { this.countryName = countryName; }
  public String getCountryCode() { return countryCode; }
  public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
  public String getStateCode() { return stateCode; }
  public void setStateCode(String stateCode) { this.stateCode = stateCode; }
  public String getRegionCode() { return regionCode; }
  public void setRegionCode(String regionCode) { this.regionCode = regionCode; }

  public static Address createAddress(JsonObject jo) {
    Address a = new Address();
    a.setCityName(jo.getString("cityName"));
    a.setCityCode(jo.getString("cityCode"));
    a.setCountryName(jo.getString("countryName"));
    a.setCountryCode(jo.getString("countryCode"));
    a.setStateCode(jo.getString("stateCode"));
    a.setRegionCode(jo.getString("regionCode"));
    return a;
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("cityName", cityName)
        .add("cityCode", cityCode)
        .add("countryName", countryName)
        .add("countryCode", countryCode)
        .add("stateCode", stateCode)
        .add("regionCode", regionCode)
        .build();
  }
  
  @Override
  public String toString() {
    return "Address [cityName=" + cityName + ", cityCode=" + cityCode + ", countryName=" + countryName
        + ", countryCode=" + countryCode + ", stateCode=" + stateCode + ", regionCode=" + regionCode + "]";
  }

}
