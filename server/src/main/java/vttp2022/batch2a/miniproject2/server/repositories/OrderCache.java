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
public class OrderCache {
  
  @Autowired @Qualifier(RedisConfig.CACHE) private RedisTemplate<String, String> redisTemplate;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(OrderCache.class);

  // Temp storage for testing
  public void cache(String payload) {
    ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
    valueOps.set("order", payload);
  }

  public String get() {
    ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
    return valueOps.get("order");
  }
}
