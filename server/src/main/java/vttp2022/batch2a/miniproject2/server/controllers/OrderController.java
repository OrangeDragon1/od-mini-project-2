package vttp2022.batch2a.miniproject2.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.Utils;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorderrequests.OrderRequest;
import vttp2022.batch2a.miniproject2.server.services.FlightService;

@Controller
@RequestMapping(path = "/api/v1/order")
public class OrderController {
  
  @Autowired
  private FlightService flightSvc;

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

  @PostMapping(path = "/create")
  @ResponseBody
  public ResponseEntity<String> postOrderCreate(@RequestBody String payload) {

    JsonObject payloadObj = Utils.payloadToJsonObj(payload);
    System.out.println(payloadObj.toString());
    System.out.println();
    System.out.println("*****************************************************");
    System.out.println();
    OrderRequest r = OrderRequest.create(payloadObj);
    System.out.println(r.getType().toString());
    System.out.println(r.getSelectedOffers().toString());
    System.out.println(r.getPayments().toString());
    System.out.println(r.getPassengers().toString());
    System.out.println();
    System.out.println("*****************************************************");
    System.out.println();
    JsonObject dataObj = Json.createObjectBuilder().add("data", r.toJson()).build();
    System.out.println(dataObj.toString());

    return null;
  }
}
