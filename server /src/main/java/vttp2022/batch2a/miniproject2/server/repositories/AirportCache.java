package vttp2022.batch2a.miniproject2.server.repositories;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import vttp2022.batch2a.miniproject2.server.configs.RedisConfig;

@Repository
public class AirportCache {
  
  @Autowired @Qualifier(RedisConfig.CACHE) RedisTemplate<String, String> redisTemplate;

  private static final Logger LOGGER = LoggerFactory.getLogger(AirportCache.class);

  public void cache(String query, String payload) {
    ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
    valueOps.set(query, payload/*, Duration.ofMinutes(1) */);
  }

  public String get(String query) {
    ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
    LOGGER.info("In [get]");
    Boolean hasKey = valueOps.getOperations().hasKey(query);

    if (null == hasKey || !hasKey) {
      LOGGER.info("In [get]: {} query is not cached", query);
      return null;
    }
    
    LOGGER.info("In [get]: {} query is cached, retrieving data", query);
    return valueOps.get(query);
  }
  
}
