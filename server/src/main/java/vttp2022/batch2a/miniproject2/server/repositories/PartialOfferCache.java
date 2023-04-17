package vttp2022.batch2a.miniproject2.server.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.batch2a.miniproject2.server.configs.RedisConfig;

@Repository
public class PartialOfferCache {
  
  @Autowired @Qualifier(RedisConfig.CACHE) private RedisTemplate<String, String> redisTemplate;

  private static final Logger LOGGER = LoggerFactory.getLogger(PartialOfferCache.class);
  private static final String PARTIAL_OFFER_KEY = "prq";
  private static final String PARTIAL_OFFER_OFFER_KEY = "off";

  public void cache(String query, String payload) {
    HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
    LOGGER.info("In [cache]: caching {}", query);
    hashOps.put(PARTIAL_OFFER_KEY, query, payload);
  }

  public void cacheOff(String query, String payload) {
    HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
    LOGGER.info("In [cacheOff]: caching {}", query);
    hashOps.put(PARTIAL_OFFER_OFFER_KEY, query, payload);
  }

  public String get(String query) {
    HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
    Boolean hasKey = hashOps.hasKey(PARTIAL_OFFER_KEY, query);

    if (null == hasKey || !hasKey) {
      LOGGER.info("In [get]: {} query is not cached", query);
      return null;
    }
    
    LOGGER.info("In [get]: {} query is cached, retrieving data", query);
    return hashOps.get(PARTIAL_OFFER_KEY, query);
  }

  public String getOff(String query) {
    HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
    Boolean hasKey = hashOps.hasKey(PARTIAL_OFFER_OFFER_KEY, query);

    if (null == hasKey || !hasKey) {
      LOGGER.info("In [get]: {} query is not cached", query);
      return null;
    }
    
    LOGGER.info("In [getOff]: {} query is cached, retrieving data", query);
    return hashOps.get(PARTIAL_OFFER_OFFER_KEY, query);
  }
}
