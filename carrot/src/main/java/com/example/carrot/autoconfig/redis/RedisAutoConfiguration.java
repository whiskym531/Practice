package com.example.carrot.autoconfig.redis;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

import java.util.Set;

@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class RedisAutoConfiguration {
    @Autowired
    private RedisProperties properties;

    public RedisAutoConfiguration() {
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(this.properties.getMaxTotal());
        config.setMaxIdle(this.properties.getMaxIdle());
        config.setMaxWaitMillis(this.properties.getMaxWaitMillis());
        config.setTestOnBorrow(this.properties.isTestOnBorrow());
        return config;
    }

    @Bean
    public Pool<Jedis> jedisPool(JedisPoolConfig poolConfig) {
        if (this.properties.isCluster()) {
            String sentinelProps = this.properties.getSentinelHosts();
            Iterable<String> parts = Splitter.on(',').trimResults().omitEmptyStrings().split(sentinelProps);
            Set<String> sentinelHosts = Sets.newHashSet(parts);
            String masterName = this.properties.getSentinelMasterName();
            return Strings.isNullOrEmpty(this.properties.getAuth()) ? new JedisSentinelPool(masterName, sentinelHosts, poolConfig) : new JedisSentinelPool(masterName, sentinelHosts, poolConfig, 2000, this.properties.getAuth());
        } else {
            return Strings.isNullOrEmpty(this.properties.getAuth()) ? new JedisPool(poolConfig, this.properties.getHost(), this.properties.getPort()) : new JedisPool(poolConfig, this.properties.getHost(), this.properties.getPort(), 2000, this.properties.getAuth());
        }
    }

    @Bean
    public JedisTemplate jedisTemplate(Pool<Jedis> pool) {
        return new JedisTemplate(pool);
    }
}

