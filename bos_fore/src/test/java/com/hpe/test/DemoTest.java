package com.hpe.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;

public class DemoTest {
	

	@Test
	public void test01(){
		Jedis jedis=new Jedis("localhost",6379);
		jedis.set("code", "345345");
		
	}

	@Test
	public void test02(){
		Jedis jedis=new Jedis("localhost");
		System.out.println(jedis.get("code"));
	}
	
	@Test
	public void test03(){
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redis=(RedisTemplate)context.getBean("redisTemplate");
		redis.opsForValue().set("code", "123456",48,TimeUnit.HOURS);
	}
	
	@Test
	public void test04(){
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redis=(RedisTemplate)context.getBean("redisTemplate");
		System.out.println(redis.opsForValue().get("code"));
	}

}
