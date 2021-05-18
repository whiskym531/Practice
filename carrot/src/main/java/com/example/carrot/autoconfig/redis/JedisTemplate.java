package com.example.carrot.autoconfig.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

public class JedisTemplate {
    private static Logger logger = LoggerFactory.getLogger(JedisTemplate.class);
    private Pool<Jedis> jedisPool;

    public JedisTemplate(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }

    public <T> T execute(JedisTemplate.JedisAction<T> jedisAction) throws JedisException {
        return (T) this.execute((JedisAction)jedisAction, 0);
    }

    public <T> T execute(JedisTemplate.JedisAction<T> jedisAction, int dbIndex) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;

        Object var5;
        try {
            jedis = this.jedisPool.getResource();
            jedis.select(dbIndex);
            var5 = jedisAction.action(jedis);
        } catch (JedisConnectionException var9) {
            logger.error("Redis connection lost.", var9);
            broken = true;
            throw var9;
        } finally {
            this.closeResource(jedis, broken);
        }

        return (T) var5;
    }

    public void execute(JedisTemplate.JedisActionNoResult jedisAction) throws JedisException {
        this.execute(jedisAction, 0);
    }

    public void execute(JedisTemplate.JedisActionNoResult jedisAction, int dbIndex) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;

        try {
            jedis = this.jedisPool.getResource();
            jedis.select(dbIndex);
            jedisAction.action(jedis);
        } catch (JedisConnectionException var9) {
            logger.error("Redis connection lost.", var9);
            broken = true;
            throw var9;
        } finally {
            this.closeResource(jedis, broken);
        }

    }

    protected void closeResource(Jedis jedis, boolean connectionBroken) {
        if (jedis != null) {
            if (connectionBroken) {
                this.jedisPool.returnBrokenResource(jedis);
            } else {
                this.jedisPool.returnResource(jedis);
            }
        }

    }

    public Pool<Jedis> getJedisPool() {
        return this.jedisPool;
    }

    public interface JedisActionNoResult {
        void action(Jedis var1);
    }

    public interface JedisAction<T> {
        T action(Jedis var1);
    }
}