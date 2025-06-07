package cn.varin.jedis.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

// Jedis连接池对象
public class JedisConnectionFactory {
    //
    static  public final JedisPool jedisPool;
    static {
        // 创建配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数
        jedisPoolConfig.setMaxTotal(10);
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(10);
        // 最小空闲数
        jedisPoolConfig.setMinIdle(2);
        // 空闲等待时间
        jedisPoolConfig.setMaxWaitMillis(1000);

        jedisPool = new JedisPool(jedisPoolConfig,"varin.cn",6379,100,"Whltaoin08_");
    }
    public static Jedis getResource(){
       return jedisPool.getResource();
    }
}
