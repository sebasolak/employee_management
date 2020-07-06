package com.example.employee_management.dao;

import com.example.employee_management.model.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeDao {

    List<Employee> selectAllEmployees();

    Employee selectEmployeeById(UUID employeeId);

    int persistNewEmployee(UUID employeeId, Employee newEmployee);

    int updateEmployeeById(UUID employeeId, Employee update);

    int deleteEmployeeById(UUID employeeId);
}
