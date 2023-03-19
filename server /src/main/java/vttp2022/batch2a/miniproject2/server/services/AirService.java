package vttp2022.batch2a.miniproject2.server.services;

import static vttp2022.batch2a.miniproject2.server.Utils.getAccessTokenResponse;
import static vttp2022.batch2a.miniproject2.server.Utils.jsonArrToJsonObjs;
import static vttp2022.batch2a.miniproject2.server.Utils.payloadToJson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.models.Airport;
import vttp2022.batch2a.miniproject2.server.repositories.AirportCache;

@Service
public class AirService {

  @Autowired
  AirportCache airportCache;

  @Value("${amadeus.api.key}")
  private String clientID;
  @Value("${amadeus.api.secret}")
  private String clientSecret;

  private OAuthAccessTokenResponse acsTknResp;
  private String accessToken = "";

  private static final String AMADEUS_BASE_URL = "https://test.api.amadeus.com/";
  private static final String AIRPORT_SEARCH_EP = "v1/reference-data/locations";
  private static final Logger LOGGER = LoggerFactory.getLogger(AirService.class);

  public List<Airport> getAirports(String query)
      throws OAuthSystemException, OAuthProblemException, UnsupportedEncodingException {
    String encodedQuery = URLEncoder.encode(query, "UTF-8");
    String payload = airportCache.get(encodedQuery);

    if (null == payload) {

      if (null == acsTknResp || acsTknResp.getExpiresIn() <= 0) {
        LOGGER.info("In [getAirports]: Requesting new access token");
        accessToken = getAccessToken();
      }

      LOGGER.info("In [getAirports]: Retrieving data from endpoint");
      String url = UriComponentsBuilder.fromUriString(AMADEUS_BASE_URL + AIRPORT_SEARCH_EP)
          .queryParam("subType", "AIRPORT")
          .queryParam("keyword", encodedQuery)
          .query("sort=analytics.travelers.score")
          .query("view=FULL")
          .toUriString();
      RequestEntity<Void> requestEntity = RequestEntity.get(url)
          .header(HttpHeaders.ACCEPT, "application/vnd.amadeus+json")
          .header(HttpHeaders.AUTHORIZATION, accessToken)
          .build();
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> respEntity = restTemplate.exchange(requestEntity, String.class);
      payload = respEntity.getBody();
      airportCache.cache(encodedQuery, payload);
    }

    JsonObject payloadObj = payloadToJson(payload);
    List<Airport> airportList = jsonArrToJsonObjs(payloadObj.getJsonArray("data"))
        .stream()
        .map(Airport::createAirportEndPt)
        .collect(Collectors.toList());

    // JsonObject obj = Json.createObjectBuilder()
    // .add("data", airportList.stream()
    // .map(v -> v.toJson())
    // .collect(JsonCollectors.toJsonArray())
    // )
    // .build();

    return airportList;
  }

  public String getAccessToken() throws OAuthSystemException, OAuthProblemException {
    acsTknResp = getAccessTokenResponse(clientID, clientSecret);
    return "Bearer " + acsTknResp.getAccessToken();
  }

}
