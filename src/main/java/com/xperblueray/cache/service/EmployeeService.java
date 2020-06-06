package com.xperblueray.cache.service;

import com.xperblueray.cache.bean.Employee;
import com.xperblueray.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp")   // 抽取缓存的公共配置,仅能使用cacheNames，不能使用value
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    // 使用编码进行redis缓存
//    @Qualifier("cacheManager")
//    @Autowired
//    RedisCacheManager cacheManager;

    /**
     * Cacheable的运行流程
     * 1. 在方法运行之前，先去缓存按value查找cache，第一次获取不到就会创建
     * 2. 去Cache中查找Entry，使用一个key，默认用方法的参数
     *      key是按某种规则创建的，默认时使用的SimpleKeyGenerator生成
     *      SimpleKeyGenerator的生成策略
     *          如果没有参数，key = new SimpleKey();
     *          如果有一个参数，key = 参数的值
     *          如果有多个参数， key = new SimpleKey(params)
     * 3. 如果查到，直接返回，没有查到就调用目标方法
     * 4. 将目标方法返回的结果放到缓存里
     *
     * cacheNames/value: 指定缓存组件（）的名字，将方法的返回结果放到哪个缓存中，是数组的方式，可以指定多个缓存
     *
     * key:
     *  Entry里的key，默认是使用的SimpleKeyGenerator生成
     *  也可以使用SpEL表达式来生成#a0 #p0 #root.args[0] #id
     *
     * keyGenrator:
     *  key的生成器，可以自定义一个
     * cacheManager:
     *  指定缓存管理器
     * condition:
     *  指定符合条件的情况下才缓存
     * unless:
     *  否定缓存，和condition正好相反
     *
     * sync:
     *  是否使用异步模式
     */
    /**
     * Get Employee by id
     * @param id
     * @return
     */
//    @Cacheable( /*key = "#root.args[0]"*/ /*key = "#p0",*/ keyGenerator = "myKeyGenerator", unless = "#a0>1")
    @Cacheable( key = "#a0")
    public Employee getEmp(Integer id) {
        System.out.println("查询"+ id +"员工");

        return employeeMapper.getEmpById(id);
    }


    /**
     * @CachePut :
     *  既调用方法，又更新缓存
     *  修改了数据库的数据并且同时更新缓存
     *  运行机制：
     *      1. 先调用目标方法
     *      2. 然后将目标方法的结果缓存起来
     */
    /**
     * 更新employee
     * @param employee
     * @return
     */
    @CachePut( key = "#result.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("update" + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }


    /**
     * @CacheEvicet:清除缓存
     *  allEntries=true 清空缓存中的所有数据
     *  beforeInvacation=treu 代表情况缓存的操作是在方法执行前就执行了，无论方法是否出现异常，缓存都会清楚。方法执行前清空缓存，失败了也会清空
     */

    @CacheEvict( key = "#p0" /*allEntries = true*/, beforeInvocation = true)
    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp:" + id);
//        employeeMapper.deleteEmpById(id);
    }


    /**
     * @Caching 定义复杂的缓存规则
     */

    /**
     *
     * @param lastName
     * @return
     */
    @Caching(
            cacheable = {
                    @Cacheable( key = "#lastName")
            },
            put = {
                    @CachePut( key = "#result.id")
            }
    )
    public Employee getEmployeeByLastName(String lastName) {
        System.out.println("getEmployeeByLastName" + lastName);
        return employeeMapper.getEmpByLastName(lastName);
    }

    // 使用编码缓存redis
//    public Employee getEmp(Integer id) {
//
//        System.out.println("查询"+ id +"员工");
//
//        Employee employee = employeeMapper.getEmpById(id);
//        Cache cache = cacheManager.getCache("emp");
//        cache.put("emp:" + id, employee);
//
//        return employeeMapper.getEmpById(id);
//    }

}
