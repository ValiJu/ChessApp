package com.example.chess.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

@Configuration
@EnableRedisRepositories()
@ConfigurationProperties(prefix = "spring.redis")
@Getter
@Setter
public class RedisConfigurationProperties {

  @Value("${spring.redis.host:localhost}")
  private String host;
  @Value("${spring.redis.port:6379}")
  private int port;
  @Value("${spring.redis.password:#{null}}")
  private String password;

  @Bean
  @ConditionalOnMissingBean(LettuceConnectionFactory.class)
  public LettuceConnectionFactory redisConnectionFactory(
      RedisStandaloneConfiguration redisConfiguration) {
    return new LettuceConnectionFactory(redisConfiguration);
  }

  @Bean
  @ConditionalOnMissingBean(RedisStandaloneConfiguration.class)
  public RedisStandaloneConfiguration redisStandaloneConfiguration() {
    Assert.notNull(host, "'spring.redis.host' is not configured!");

    final RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
    redisConfiguration.setHostName(host);
    redisConfiguration.setPort(port);

    if (password != null) {
      redisConfiguration.setPassword(RedisPassword.of(password));
    }
    return redisConfiguration;
  }

  @Bean
  public RedisTemplate<String, Integer> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Long.class));
    return redisTemplate;
  }

}
