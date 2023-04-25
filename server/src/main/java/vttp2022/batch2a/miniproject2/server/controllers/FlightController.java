package vttp2022.batch2a.miniproject2.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonCollectors;
import vttp2022.batch2a.miniproject2.server.Utils;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelairports.Airport;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelofferrequests.OfferRequest;
import vttp2022.batch2a.miniproject2.server.services.FlightService;

@Controller
@RequestMapping(path = "/api/v1/search")
@CrossOrigin(origins="https://od-mini-project-2.vercel.app")
public class FlightController {

  @Autowired
  private FlightService flightSvc;

  @GetMapping(path = "/airports")
  @ResponseBody
  public ResponseEntity<String> getAirports(@RequestParam String query) {

    List<Airport> airports = flightSvc.getAllAirports();
    List<Airport> filteredAirport = airports.stream()
        .filter(v -> {
          String iataCode = v.getIataCode();
          String name = v.getName();
          String city = v.getCityName();
          String country = v.getIataCountryCode();
          String fullName = name + " " + city + " " + country;
          return iataCode.toLowerCase().contains(query.toLowerCase())
            || name.toLowerCase().contains(query.toLowerCase())
            || city.toLowerCase().contains(query.toLowerCase())
            || country.toLowerCase().contains(query.toLowerCase())
            || fullName.toLowerCase().contains(query.toLowerCase());
        })
        .collect(java.util.stream.Collectors.toList());

    JsonArray jsonArr = filteredAirport.stream()
        .map(v -> v.toJson())
        .collect(JsonCollectors.toJsonArray());
    
    return ResponseEntity.ok(jsonArr.toString());
  }

  @PostMapping(path = "/offers")
  @ResponseBody
  public ResponseEntity<String> getOffers(@RequestBody String payload) {

    JsonObject partialOfferObj = Json.createObjectBuilder().build();
    JsonObject payloadObj = Utils.payloadToJsonObj(payload);

    if (payloadObj.containsKey("prq")) {
      
      String prq = payloadObj.getString("prq");
      partialOfferObj = flightSvc.getAllOffers(prq);

    } else if (payloadObj.containsKey("data")) {

      OfferRequest offerRequest = OfferRequest.create(payloadObj.getJsonObject("data"));
      JsonObject orObj = offerRequest.toJson();
      partialOfferObj = flightSvc.getAllOffers(orObj);

    }

    if (partialOfferObj.containsKey("error")) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        Utils.createError(partialOfferObj.getString("error")).toString()
      );
    }

    return ResponseEntity.ok(partialOfferObj.toString());
  }

  @GetMapping(path = "/fullFare")
  @ResponseBody
  public ResponseEntity<String> getOffersTest(
    @RequestParam String partialOfferRequestId,
    @RequestParam String selectedPartialOffer
  ) {
    
    JsonObject po = flightSvc.getFullFares(partialOfferRequestId, selectedPartialOffer);
    
    return ResponseEntity.ok(po.toString());
  }

}
