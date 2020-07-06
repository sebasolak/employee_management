package com.example.employee_management.dao;

import com.example.employee_management.model.Employee;
import com.example.employee_management.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.example.employee_management.model.Gender.*;

@Repository("fakeDao")
public class FakeEmployeeDao implements EmployeeDao {

    private final Map<UUID, Employee> database;

    public FakeEmployeeDao() {
        database = new HashMap<>();

        UUID wayneId = UUID.randomUUID();
        Employee wayneEmployee = new Employee(wayneId, "Wayne", "Clark",
                "wayne.clark@example.com", 42, MALE, 7,
                3850.00, true, "M1");
        database.put(wayneId, wayneEmployee);

        UUID cassandraId = UUID.randomUUID();
        Employee cassandraEmployee = new Employee(cassandraId, "Cassandra", "Ward",
                "casward@gmail.com", 28, FEMALE, 3,
                2370.00, true, "M2");
        database.put(cassandraId, cassandraEmployee);

        UUID luisId = UUID.randomUUID();
        Employee luisEmployee = new Employee(luisId, "Luis", "Hoffman",
                "lhoffman@hotmail.com", 25, MALE, 2,
                1900.00, false, "M3");
        database.put(luisId, luisEmployee);
    }

    @Override
    public List<Employee> selectAllEmployees() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Employee selectEmployeeById(UUID employeeId) {
        return database.get(employeeId);
    }

    @Override
    public int persistNewEmployee(UUID employeeId, Employee newEmployee) {
        database.put(employeeId, newEmployee);
        return 1;
    }

    @Override
    public int updateEmployeeById(UUID employeeId, Employee update) {
        update.setEmployeeId(employeeId);
        database.put(employeeId, update);
        return 1;
    }

    @Override
    public int deleteEmployeeById(UUID employeeId) {
        database.remove(employeeId);
        return 1;
    }
}
