package com.example.employee_management.it;

import com.example.employee_management.clientproxy.EmployeeResourceV1;
import com.example.employee_management.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static com.example.employee_management.model.Gender.FEMALE;
import static com.example.employee_management.model.Gender.MALE;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
class EmployeeIntegrationTests {

    @Autowired
    private EmployeeResourceV1 employeeResourceV1;

    @Test
    void shouldPersistEmployee() {
        //given
        UUID employeeId = UUID.randomUUID();
        Employee employee = new Employee(employeeId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        //when
        employeeResourceV1.persistNewEmployee(employee);

        //then
        Employee kristenEmployee = employeeResourceV1.getEmployeeById(employeeId);

        assertThat(kristenEmployee).isEqualToComparingFieldByField(employee);
    }

    @Test
    void shouldDeleteUser() {
        //given
        UUID employeeId = UUID.randomUUID();
        Employee employee = new Employee(employeeId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        //when
        employeeResourceV1.persistNewEmployee(employee);

        //then
        Employee kristenEmployee = employeeResourceV1.getEmployeeById(employeeId);
        assertThat(kristenEmployee).isEqualToComparingFieldByField(employee);

        //when
        employeeResourceV1.deleteEmployeeById(employeeId);

        //then
        kristenEmployee = employeeResourceV1.getEmployeeById(employeeId);
        assertThat(kristenEmployee).isNull();
    }

    @Test
    void shouldUpdateEmployee() {
        //given
        UUID employeeId = UUID.randomUUID();
        Employee employee = new Employee(employeeId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        //when
        employeeResourceV1.persistNewEmployee(employee);

        //then
        Employee kristenEmployee = employeeResourceV1.getEmployeeById(employeeId);
        assertThat(kristenEmployee).isEqualToComparingFieldByField(employee);

        //given
        Employee updatedEmployee = new Employee(null, "Antonio", "Howard",
                "antoniohoward@gmail.com", 30, MALE, 3,
                2500.00, true, "M5");

        //when
        employeeResourceV1.updateEmployeeById(employeeId, updatedEmployee);
        Employee updatedKristen = employeeResourceV1.getEmployeeById(employeeId);

        assertThat(updatedKristen.getEmployeeId()).isEqualTo(kristenEmployee.getEmployeeId());
        assertThat(updatedKristen).isEqualToIgnoringGivenFields(updatedEmployee, "employeeId");

    }


    @Test
    void shouldGetEmployeeByGender() {
        //given
        UUID employeeId = UUID.randomUUID();
        Employee employee = new Employee(employeeId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        //when
        employeeResourceV1.persistNewEmployee(employee);

        List<Employee> males = employeeResourceV1.getAllEmployees(MALE.name());

        assertThat(males).extracting("employeeId").doesNotContain(employee.getEmployeeId());
        assertThat(males).extracting("name").doesNotContain(employee.getName());
        assertThat(males).extracting("surname").doesNotContain(employee.getSurname());
        assertThat(males).extracting("email").doesNotContain(employee.getEmail());
        assertThat(males).extracting("age").doesNotContain(employee.getAge());
        assertThat(males).extracting("gender").doesNotContain(employee.getGender());
        assertThat(males).extracting("yearsOfWork").doesNotContain(employee.getYearsOfWork());
        assertThat(males).extracting("baseSalary").doesNotContain(employee.getBaseSalary());
        assertThat(males).extracting("auth").doesNotContain(employee.getAuth());

        List<Employee> females = employeeResourceV1.getAllEmployees(FEMALE.name());

        assertThat(females).extracting("employeeId").contains(employee.getEmployeeId());
        assertThat(females).extracting("name").contains(employee.getName());
        assertThat(females).extracting("surname").contains(employee.getSurname());
        assertThat(females).extracting("email").contains(employee.getEmail());
        assertThat(females).extracting("age").contains(employee.getAge());
        assertThat(females).extracting("gender").contains(employee.getGender());
        assertThat(females).extracting("yearsOfWork").contains(employee.getYearsOfWork());
        assertThat(females).extracting("baseSalary").contains(employee.getBaseSalary());
        assertThat(females).extracting("auth").contains(employee.getAuth());
    }
}
