package vttp2022.batch2a.miniproject2.server.models.duffel.duffelofferrequests;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class OfferRequestPassengerLoyaltyProgrammeAccount {
  
  private String accountNumber;
  private String airlineIataCode;

  public String getAccountNumber() { return accountNumber; }
  public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
  public String getAirlineIataCode() { return airlineIataCode; }
  public void setAirlineIataCode(String airlineIataCode) { this.airlineIataCode = airlineIataCode; }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("account_number", getAccountNumber())
        .add("airline_iata_code", getAirlineIataCode())
        .build();
  }
  
  @Override
  public String toString() {
    return "LoyaltyProgrammeAccount [accountNumber=" + accountNumber + ", airlineIataCode=" + airlineIataCode + "]";
  }
}
