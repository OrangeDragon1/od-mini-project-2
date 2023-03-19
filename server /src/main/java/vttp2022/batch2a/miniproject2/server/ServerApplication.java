package vttp2022.batch2a.miniproject2.server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import com.amadeus.exceptions.ResponseException;

import vttp2022.batch2a.miniproject2.server.configs.RedisConfig;
import vttp2022.batch2a.miniproject2.server.models.Airport;
import vttp2022.batch2a.miniproject2.server.services.AirService;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

	@Autowired AirService airSvc;
	@Autowired @Qualifier(RedisConfig.CACHE) RedisTemplate<String, String> template;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws ResponseException, OAuthSystemException, OAuthProblemException, UnsupportedEncodingException {
		
		// String encodedString = URLEncoder.encode("new york", "UTF-8");
		// List<Airport> airportList = airSvc.getAirports(encodedString);
		// airportList.forEach(v -> System.out.println(v.toString()));
		
		// System.exit(0);
	}

}
