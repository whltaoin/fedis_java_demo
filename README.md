### Jedis使用（单线程）


1. 使用Jedis分为了四步骤：
    1. 导入依赖
    2. 初始化Jedis对象
    3. 执行Jedis中的操作方法
    4. 释放Jedis对象
2. 具体实现
    1. 导入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>jedis-test</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
  <dependencies>
<!--    核心-->
      <dependency>
          <groupId>redis.clients</groupId>
          <artifactId>jedis</artifactId>
          <version>6.0.0</version>
      </dependency>
<!--      测试类-->
      <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.13.2</version>
          <scope>test</scope>
      </dependency>
  </dependencies>


</project>
```

  	b. 测试类方法内容

```java
package cn.varin;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {
    private Jedis jedis;

    @Before
    public void init(){
        jedis = new Jedis("ip",6379);
        jedis.auth("密码");
        jedis.select(0);
    }
    @Test
    public void StringTest(){
        String set = jedis.set("name", "varin");
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

```

    3. 执行结果

![](https://cdn.nlark.com/yuque/0/2025/png/38516294/1749261153838-0f038de1-f60b-4c97-b150-d212808fc365.png)

### Jedis使用（使用连接池）
1. 使用步骤：
    1. 创建连接池
    2. 获取Jedis对象
    3. 操作Jedis对象
    4. 归还连接池对象
2. 具体代码
    1. 创建连接池对象

```java
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

        jedisPool = new JedisPool(jedisPoolConfig,"host",6379,100,"password");
    }
    public static Jedis getResource(){
       return jedisPool.getResource();
    }
}

```



    2. 测试类代码

```java
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

```

    3. 执行结果：

![](https://cdn.nlark.com/yuque/0/2025/png/38516294/1749262422190-2acf71aa-4822-4d58-a4e1-29cdddfe0745.png)

