package cn.varin;


import cn.varin.jedis.utils.JedisConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {
    private Jedis jedis;

    @Before
    public void init(){
        jedis = JedisConnectionFactory.getResource();
        jedis.select(0);
    }
    @Test
    public void StringTest(){
        String set = jedis.set("name", "varya");
        System.out.println("执行set后结果为："+set);
        String name = jedis.get("name");
        System.out.println("key为name的value为："+name);

    }

    @After
    public void close(){
        if(jedis !=null){
            jedis.close();
        }
    }

}
