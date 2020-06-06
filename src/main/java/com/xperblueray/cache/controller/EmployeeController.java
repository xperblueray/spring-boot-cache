package com.xperblueray.cache.controller;

import com.xperblueray.cache.bean.Employee;
import com.xperblueray.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/getEmployee")
    public Employee getEmployee(Integer id) {
        Employee employee = employeeService.getEmp(id);
        return employee;
    }

    @RequestMapping("/updateEmployee")
    public Employee updateEmployee(Employee employee) {
        Employee em = employeeService.updateEmp(employee);
        return em;
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(Integer id) {
        employeeService.deleteEmp(id);
        return "success";
    }


    @GetMapping("/emp/{lastName}")
    public Employee getEmployeeByLastName(@PathVariable("lastName") String lastName) {
        return employeeService.getEmployeeByLastName(lastName);
    }
}
