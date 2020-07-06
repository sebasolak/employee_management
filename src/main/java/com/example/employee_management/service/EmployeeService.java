package com.example.employee_management.service;

import com.example.employee_management.dao.EmployeeDao;
import com.example.employee_management.model.Employee;
import com.example.employee_management.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeService(@Qualifier("fakeDao") EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> getAllEmployees(Optional<String> gender) {
        if (gender.isPresent()) {
            Gender theGender = Gender.valueOf(gender.get().toUpperCase());

            return employeeDao.selectAllEmployees().stream()
                    .filter(employee -> theGender.equals(employee.getGender()))
                    .collect(Collectors.toList());
        }
        return employeeDao.selectAllEmployees();
    }

    public Employee getEmployeeById(UUID employeeId) {
        return employeeDao.selectEmployeeById(employeeId);
    }

    public int persistNewEmployee(UUID employeeId, Employee newEmployee) {
        UUID uid = employeeId == null ? UUID.randomUUID() : employeeId;
        newEmployee.setEmployeeId(uid);
        return employeeDao.persistNewEmployee(uid, newEmployee);
    }


    public int updateEmployeeById(UUID employeeId, Employee update) {
        Optional<Employee> employeeOptional = Optional.ofNullable(getEmployeeById(employeeId));
        if (employeeOptional.isPresent()) {
            update.setEmployeeId(employeeId);
            return employeeDao.updateEmployeeById(employeeId, update);
        }
        return -1;
    }

    public int deleteEmployeeById(UUID employeeId) {
        Optional<Employee> employeeOptional = Optional.ofNullable(getEmployeeById(employeeId));
        if (employeeOptional.isPresent()) {
            return employeeDao.deleteEmployeeById(employeeId);
        }
        return -1;
    }
}
