package vttp2022.batch2a.miniproject2.server.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers.PartialOffer;
import vttp2022.batch2a.miniproject2.server.services.FlightService;

@Controller
// @CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/search")
public class FlightController {

  @Autowired
  private FlightService flightSvc;

  private static final Logger LOGGER = LoggerFactory.getLogger(FlightService.class);

  @GetMapping(path = "/airports")
  @ResponseBody
  public ResponseEntity<String> getAirports() {

    List<Airport> airports = flightSvc.getAllAirports();
    JsonArray jsonArr = airports.stream()
        .map(v -> v.toJson())
        .collect(JsonCollectors.toJsonArray());
    // JsonArray arr = flightSvc.getAllAirports();
    // try {
    //   List<Airport> airportList = flightSvc.getAirports(query);

    //       JsonArray arr = airportList.stream()
    //           .map(v -> v.toJson())
    //           .collect(JsonCollectors.toJsonArray());
    //   return ResponseEntity.ok(arr.toString());
    // } catch (Exception ex) {
    //   ex.printStackTrace();
    //   return null;
    // }


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
    // System.out.println(po.toString());
    
    return ResponseEntity.ok(po.toString());
  }

}
