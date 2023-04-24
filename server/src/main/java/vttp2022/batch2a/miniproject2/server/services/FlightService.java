package vttp2022.batch2a.miniproject2.server.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.Utils;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelairports.Airport;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorderrequests.OrderRequest;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelorders.Order;
import vttp2022.batch2a.miniproject2.server.models.duffel.duffelpartialoffers.PartialOffer;
import vttp2022.batch2a.miniproject2.server.repositories.AirportCache;
import vttp2022.batch2a.miniproject2.server.repositories.OrderCache;
import vttp2022.batch2a.miniproject2.server.repositories.OrderRepository;
import vttp2022.batch2a.miniproject2.server.repositories.PartialOfferCache;

@Service
public class FlightService {

  @Value("${duffel.api.test.oauth2.token}") private String apiToken;
  @Autowired private OrderRepository orderRepo;
  @Autowired private AirportCache airportCache;
  @Autowired private PartialOfferCache partialOfferCache;
  @Autowired private OrderCache orderCache;

  private static final String DUFFEL_AIRPORTS = "https://api.duffel.com/air/airports";
  private static final String DUFFEL_PARTIAL_OFFER_REQUESTS = "https://api.duffel.com/air/partial_offer_requests";
  private static final String DUFFEL_ORDERS = "https://api.duffel.com/air/orders";

  /*
   * Get all airports from Duffel API 
   */
  public List<Airport> getAllAirports() {
    String dataString = airportCache.get("data");
    JsonArray airportArr;
    
    if (dataString == null) {
      Optional<String> after = Optional.empty();
      boolean isAfter = false;
      JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

      while (!isAfter) {
        String url = UriComponentsBuilder.fromUriString(DUFFEL_AIRPORTS)
            .queryParam("limit", "200")
            .queryParamIfPresent("after", after)
            .build()
            .toString();
  
        RequestEntity<Void> requestEntity = RequestEntity.get(url)
            .header(HttpHeaders.ACCEPT_ENCODING, "*/*")
            .header(HttpHeaders.ACCEPT, "application/json")
            .header("Duffel-Version", "v1")
            .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiToken))
            .build();
  
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> respEntity = restTemplate.exchange(requestEntity, String.class);
        String payload = respEntity.getBody();
        JsonObject payloadObj = Utils.payloadToJsonObj(payload);
        JsonArray dataArr = payloadObj.getJsonArray("data");
        dataArr.forEach(v -> arrBuilder.add(v.asJsonObject()));
        isAfter = payloadObj.getJsonObject("meta").isNull("after");

        if (isAfter) {
          break;
        }
  
        after = Optional.of(payloadObj.getJsonObject("meta").getString("after"));
      }
      airportArr = arrBuilder.build();
      airportCache.cache("data", airportArr.toString());
    } else {
      airportArr = Utils.payloadToJsonArr(dataString);
    }

    List<Airport> airports = airportArr.stream()
        .map(v -> Airport.create(v.asJsonObject()))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    return airports;
  }

  /*
   * Get all partial offers from Duffel API
   */
  public JsonObject getAllOffers(JsonObject orObj) {
    
    JsonObject data = Json.createObjectBuilder()
        .add("data", orObj)
        .build();

    String url = UriComponentsBuilder.fromUriString(DUFFEL_PARTIAL_OFFER_REQUESTS)
        .queryParam("supplier_timeout", "20000")
        .build()
        .toString();
    
    RequestEntity<String> requestEntity = RequestEntity.post(url)
        .header(HttpHeaders.ACCEPT_ENCODING, "*/*")
        .header(HttpHeaders.ACCEPT, "application/json")
        .header(HttpHeaders.CONTENT_TYPE, "application/json")
        .header("Duffel-Version", "v1")
        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiToken))
        .body(data.toString());
    
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> respEntity = restTemplate.exchange(requestEntity, String.class);
    String payload = respEntity.getBody();
    JsonObject dataObject =  Utils.payloadToJsonObj(payload).getJsonObject("data");
    partialOfferCache.cache(dataObject.getString("id"), dataObject.toString());
    
    return PartialOffer.create(dataObject).toJson();
  }

  /*
   * Get all partial offers from cache
   */
  public JsonObject getAllOffers(String prq) {
    String dataObjectPayload = partialOfferCache.get(prq);
    if (null == dataObjectPayload) {
      return Utils.createError("PRQ not found");
    }
    JsonObject dataObject = Utils.payloadToJsonObj(dataObjectPayload);
    return PartialOffer.create(dataObject).toJson();
  }

  /*
   * Get full fares given selected partial offers 
   */
  public JsonObject getFullFares(String prq, String off) {

    JsonObject dataObject;
    String dataObjectPayload = partialOfferCache.getOff(off);

    if (null == dataObjectPayload) {
      String url = UriComponentsBuilder.fromUriString(DUFFEL_PARTIAL_OFFER_REQUESTS)
          .pathSegment(prq)
          .pathSegment("fares")
          .queryParam("selected_partial_offer[]", off)
          .build()
          .toString();
  
      RequestEntity<Void> requestEntity = RequestEntity.get(url)
          .header(HttpHeaders.ACCEPT_ENCODING, "*/*")
          .header(HttpHeaders.ACCEPT, "application/json")
          .header(HttpHeaders.CONTENT_TYPE, "application/json")
          .header("Duffel-Version", "v1")
          .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiToken))
          .build();
      
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> respEntity = restTemplate.exchange(requestEntity, String.class);
      String payload = respEntity.getBody();
  
      dataObject =  Utils.payloadToJsonObj(payload).getJsonObject("data");
      partialOfferCache.cacheOff(off, dataObject.toString());
    } else {
      dataObject = Utils.payloadToJsonObj(dataObjectPayload);
    }

    PartialOffer po = PartialOffer.create(dataObject);
    
    return PartialOffer.create(dataObject).toJson();
  }

  /*
   * Create order
   */

   public JsonObject createOrder(JsonObject payloadObj, String userId) {
    OrderRequest r = OrderRequest.create(payloadObj);
    JsonObject rObj = r.toJson();

    JsonObject data = Json.createObjectBuilder()
        .add("data", rObj)
        .build();

    String url = UriComponentsBuilder.fromUriString(DUFFEL_ORDERS)
        .build()
        .toString();

    RequestEntity<String> requestEntity = RequestEntity.post(url)
        .header(HttpHeaders.ACCEPT_ENCODING, "*/*")
        .header(HttpHeaders.ACCEPT, "application/json")
        .header(HttpHeaders.CONTENT_TYPE, "application/json")
        .header("Duffel-Version", "v1")
        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiToken))
        .body(data.toString());

    try {
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> respEntity = restTemplate.exchange(requestEntity, String.class);
      
      String payload = respEntity.getBody();

      JsonObject dataObj = Utils.payloadToJsonObj(payload).getJsonObject("data");
      Order o = Order.create(dataObj);
      orderRepo.addOrder(o, userId);

      JsonObject orderObj = Json.createObjectBuilder()
          .add("bookingReference", o.getBookingReference())
          .build();
      return orderObj;

    } catch (HttpClientErrorException | HttpServerErrorException ex) {
      Integer statusCode = Integer.parseInt(ex.getStatusCode().toString().substring(0, 3));
      JsonObject errObj = Utils.payloadToJsonObj(ex.getResponseBodyAsString());
      JsonArray errArr = errObj.getJsonArray("errors");
      List<String> errTitles = errArr.stream()
          .map(v -> v.asJsonObject().getString("title"))
          .collect(Collectors.toList());
      
      StringBuilder strBuilder = new StringBuilder();
      for (int i = 0; i < errTitles.size(); i++) {
        strBuilder.append(errTitles.get(i));
        if ((i+1) < errTitles.size()) {
          strBuilder.append(", ");
        }
      }

      String errStr = strBuilder.toString();

      if (statusCode >= 500) {
        return Utils.createError(statusCode.toString() + " API client error: " + errStr);
      } else if (statusCode >= 400) {
        return Utils.createError(statusCode.toString() + " API server error: " + errStr);
      }
      return Utils.createError("Unknown API error");
    }
   }

   public JsonArray getOrdersByUserId(String userId) {
    orderRepo.getOrdersByUserId(userId);
    return null;
   }

   public JsonObject createOrder() {

    return null;
   }

   public JsonObject createOrder(SqlRowSet rs) {
    return null;
   }
}
