package vttp2022.batch2a.miniproject2.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import vttp2022.batch2a.miniproject2.server.configs.RedisConfig;
import vttp2022.batch2a.miniproject2.server.services.FlightService;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

	@Autowired
	FlightService flightSvc;
	@Autowired
	@Qualifier(RedisConfig.CACHE)
	RedisTemplate<String, String> template;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		
	}

}
