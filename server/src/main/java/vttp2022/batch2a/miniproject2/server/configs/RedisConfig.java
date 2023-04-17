package vttp2022.batch2a.miniproject2.server.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  public static final String CACHE = "cache";

  @Value("${spring.redis.host}")private String redisHost;
  @Value("${spring.redis.port}")private Integer redisPort;
  @Value("${spring.redis.database}")private Integer redisDatabase;
  // @Value("${spring.redis.user}")private String redisUser;
  // @Value("${spring.redis.password}")private String redisPassword;

  @Bean(CACHE)
  public RedisTemplate<String, String> createRedisTemplate() {

    final RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
    redisConfig.setHostName(redisHost);
    redisConfig.setPort(redisPort);
    redisConfig.setDatabase(redisDatabase);
    // redisConfig.setUsername(redisUser);
    // redisConfig.setPassword(redisPassword);

    final JedisClientConfiguration jedisClient = JedisClientConfiguration
        .builder().build();

    final JedisConnectionFactory jedisFac = new JedisConnectionFactory(redisConfig, jedisClient);
    jedisFac.afterPropertiesSet();

    final RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisFac);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());

    return template;
  }

}
