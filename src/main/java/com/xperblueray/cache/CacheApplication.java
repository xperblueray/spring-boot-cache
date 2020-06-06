package com.xperblueray.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 一、 搭建基本环境
 *  1. 创建数据库cache和表department、employee
 *  2. 创建javaBean封装数据
 *  3. 整合Mybatis操作数据
 *      a. 配置数据源
 *      b. 使用注解版Mybatis
 *          MapperScan指定需要扫描的mapper接口所在的包
 * 二、 快速体验缓存
 *  1. 开启基于注解的缓存@EnableCaching
 *  2. @cacheable
 *     @CacheEvict
 *     @CachePut
 *
 *  SpringBoot 默认使用的缓存是ConcurrentMapCacheManager --> ConcurrentMapCahce; 把数据保存在ConcurrentMap中
 *  开发中常会使用一些缓存中间件: redis, memcached, ehcache
 */
@SpringBootApplication
@MapperScan("com.xperblueray.cache.mapper")
@EnableCaching
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

}
