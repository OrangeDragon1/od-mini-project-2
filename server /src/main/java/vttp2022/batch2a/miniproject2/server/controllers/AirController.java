package vttp2022.batch2a.miniproject2.server.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonCollectors;
import vttp2022.batch2a.miniproject2.server.models.Airport;
import vttp2022.batch2a.miniproject2.server.services.AirService;

@Controller
@RequestMapping(path = "/api/v1/search")
public class AirController {

  @Autowired
  private AirService airSvc;

  private static final Logger LOGGER = LoggerFactory.getLogger(AirService.class);

  @GetMapping(path = "/airports")
  @ResponseBody
  public ResponseEntity<String> getAirports(@RequestParam String query) {

    try {
      List<Airport> airportList = airSvc.getAirports(query);
      JsonObject obj = Json.createObjectBuilder()
          .add("data", airportList.stream()
              .map(v -> v.toJson())
              .collect(JsonCollectors.toJsonArray()))
          .build();
      return ResponseEntity.ok(obj.toString());
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }

  }

}
