package vttp2022.batch2a.miniproject2.server.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.Utils;
import vttp2022.batch2a.miniproject2.server.services.AuthenticationService;
import vttp2022.batch2a.miniproject2.server.services.FlightService;

@Controller
@RequestMapping(path = "/api/v1/order")
// @CrossOrigin(origins="*")
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

  @GetMapping(path = "/getByBookingRef")
  @ResponseBody
  public ResponseEntity<String> getOrder(@RequestParam Optional<String> bookingRef) {
    
    if (bookingRef.isEmpty()) {
      // return empty
    }

    String bookingRefStr = bookingRef.get();
    JsonObject orderObj = flightSvc.getOrderByBookingRef(bookingRefStr);
    if (orderObj.containsKey("error")) {
      return ResponseEntity.badRequest().body(orderObj.toString());
    }
    
    return ResponseEntity.ok(orderObj.toString());
  }

  @DeleteMapping(path = "/delete")
  public ResponseEntity<String> deleteOrder(@RequestParam String bookingRef) {

    JsonObject respObj = flightSvc.deleteOrderByBookingRef(bookingRef);
    if (respObj.containsKey("error")) {
      return ResponseEntity.badRequest().body(respObj.toString());
    }

    return ResponseEntity.ok(respObj.toString());
  }

  @GetMapping(path = "/getAllByUserId")
  @ResponseBody
  public ResponseEntity<String> getAllOrders() {
    
    JsonObject userObj = authSvc.getUser();
    JsonArray ordersArr = flightSvc.getOrdersByUserId(userObj.getString("id"));
    // System.out.println(ordersArr.toString());
    
    return ResponseEntity.ok(ordersArr.toString());
  }
}
