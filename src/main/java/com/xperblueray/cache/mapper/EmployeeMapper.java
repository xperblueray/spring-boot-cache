package com.xperblueray.cache.mapper;

import com.xperblueray.cache.bean.Employee;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    public Employee getEmpById(Integer id);

    @Insert("insert into employee(lastName, email, gender, d_id) values (#{lastName}, #{email}, #{gender}, #{dId})")
    public void insertEmp(Employee employee);

    @Update("update employee set lastName=#{lastName},email=#{email}, gender=#{gender}, d_id =#{dId} where id =#{id}")
    public void updateEmp(Employee employee);

    @Delete("delete from employee where id=#{id}")
    public void deleteEmpById(Integer id);

    @Select("select * from employee where lastName=#{lastName}")
    public Employee getEmpByLastName(String lastName);
}
