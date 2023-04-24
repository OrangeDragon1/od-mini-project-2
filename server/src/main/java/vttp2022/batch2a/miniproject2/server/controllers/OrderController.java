package vttp2022.batch2a.miniproject2.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.Utils;
import vttp2022.batch2a.miniproject2.server.services.AuthenticationService;
import vttp2022.batch2a.miniproject2.server.services.FlightService;

@Controller
@RequestMapping(path = "/api/v1/order")
public class OrderController {
  
  @Autowired
  private FlightService flightSvc;

  @Autowired
  private AuthenticationService authSvc;

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

  @PostMapping(path = "/create")
  @ResponseBody
  public ResponseEntity<String> postOrderCreate(@RequestBody String payload) {
    
    JsonObject userObj = authSvc.getUser();
    String userId = userObj.getString("id");
    
    JsonObject payloadObj = Utils.payloadToJsonObj(payload);
    JsonObject orderObj = flightSvc.createOrder(payloadObj, userId);

    if (orderObj.containsKey("error")) {
      Integer statusCode = Integer.parseInt(orderObj.getString("error").substring(0, 3));

      return ResponseEntity.status(statusCode).body(orderObj.toString());
    }
    
    return ResponseEntity.ok(orderObj.toString());
  }

  @GetMapping(path = "/get")
  @ResponseBody
  public ResponseEntity<String> getOrder() {
    return null;
  }

  @DeleteMapping(path = "/delete")
  public ResponseEntity<String> deleteOrder() {
    return null;
  }
}
