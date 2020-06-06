package com.xperblueray.cache;

import com.xperblueray.cache.bean.Employee;
import com.xperblueray.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;   // 操作键值都是字符串

    @Autowired
    RedisTemplate redisTemplate;   // 简直都是对象

    @Autowired
    RedisTemplate<Object, Employee> employeeRedisTemplate;

    @Test
    public void contextLoads() {
        Employee employee = employeeMapper.getEmpById(1);
        System.out.println("=============="+employee);
    }

    /**
     *stringRedisTemplate.opsForValue()
     */
    @Test
    public void test1() {
//        stringRedisTemplate.opsForValue().append("ss1", "你好世界");
//        stringRedisTemplate.opsForList().leftPush("ll1", "第一个值");
        stringRedisTemplate.opsForSet().add("sset1", "first set");
        stringRedisTemplate.opsForZSet().add("list1", "11", 1);
        stringRedisTemplate.opsForZSet().add("list1", "11", 2);
        stringRedisTemplate.opsForHash().put("hash", "hs01", "asd");

    }

    /**
     * 测试缓存对象
     * 默认保存对象，使用JDK序列化机制
     * Jackson2JsonRedisSerializer将数据以json的方式缓存
     * 1. 把对象转换成json格式
     * 2. 改变了默认的序列化规则
     *
     */
    @Test
    public void test2() {
        Employee employee = employeeMapper.getEmpById(2);
        employeeRedisTemplate.opsForValue().set("em1", employee);
    }

}
