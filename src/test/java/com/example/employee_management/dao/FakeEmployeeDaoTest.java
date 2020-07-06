package com.example.employee_management.dao;

import com.example.employee_management.model.Employee;
import com.example.employee_management.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.example.employee_management.model.Gender.FEMALE;
import static com.example.employee_management.model.Gender.MALE;
import static org.assertj.core.api.Assertions.*;

class FakeEmployeeDaoTest {

    private FakeEmployeeDao fakeEmployeeDao;

    @BeforeEach
    void setUp() {
        fakeEmployeeDao = new FakeEmployeeDao();
    }

    @Test
    void shouldSelectAllEmployees() {
        List<Employee> allEmployees = fakeEmployeeDao.selectAllEmployees();
        assertThat(allEmployees).hasSize(3);
        Employee employee = allEmployees.get(0);
        assertThat(employee.getEmployeeId()).isInstanceOf(UUID.class);
        assertThat(employee.getName()).isIn("Wayne", "Cassandra", "Luis");
        assertThat(employee.getSurname()).isIn("Clark", "Ward", "Hoffman");
        assertThat(employee.getEmail()).isIn("wayne.clark@example.com",
                "casward@gmail.com", "lhoffman@hotmail.com");
        assertThat(employee.getAge()).isBetween(25, 42);
        assertThat(employee.getGender()).isInstanceOf(Gender.class);
        assertThat(employee.getYearsOfWork()).isBetween(2, 7);
        assertThat(employee.getBaseSalary()).isBetween(1900.00, 3850.00);
        assertThat(employee.isEmployeeBonus()).isIn(true, false);
        assertThat(employee.getAuth()).isIn("M1", "M2", "M3");
        assertThat(employee.getHireDate()).isInstanceOf(Integer.class);
        assertThat(employee.getTotalMonthSalary()).isInstanceOf(String.class);

    }

    @Test
    @DisplayName("shouldSelectEmployeeById")
    void shouldSelectEmployeeById() {

        UUID kristenId = UUID.randomUUID();
        Employee kristenEmployee = new Employee(kristenId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        fakeEmployeeDao.persistNewEmployee(kristenId, kristenEmployee);
        assertThat(fakeEmployeeDao.selectAllEmployees()).hasSize(4);

        Employee kristenMaybe = fakeEmployeeDao.selectEmployeeById(kristenId);

        assertThat(kristenMaybe).isEqualToComparingFieldByField(kristenEmployee);
    }

    @Test
    void shouldNotSelectEmployeeByRandomId() {
        Employee employee = fakeEmployeeDao.selectEmployeeById(UUID.randomUUID());

        assertThat(employee).isNull();
    }

    @Test
    void shouldPersistNewEmployee() {
        assertThat(fakeEmployeeDao.selectAllEmployees()).hasSize(3);
        UUID kristenId = UUID.randomUUID();
        Employee kristenEmployee = new Employee(kristenId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        int persistResult
                = fakeEmployeeDao.persistNewEmployee(kristenId, kristenEmployee);

        assertThat(persistResult).isOne();
        assertThat(fakeEmployeeDao.selectAllEmployees()).hasSize(4);
    }

    @Test
    void updateEmployeeById() {
        UUID kristenId = UUID.randomUUID();
        Employee kristenEmployee = new Employee(kristenId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        int persistResult
                = fakeEmployeeDao.persistNewEmployee(kristenId, kristenEmployee);

        assertThat(persistResult).isOne();

        Employee employeeActual
                = fakeEmployeeDao.selectEmployeeById(kristenId);


        assertThat(employeeActual).isEqualToComparingFieldByField(kristenEmployee);

        Employee antonioEmployee = new Employee(null, "Antonio", "Howard",
                "antoniohoward@gmail.com", 30, MALE, 3,
                2500.00, true, "M5");

        int updateResult
                = fakeEmployeeDao.updateEmployeeById(kristenId, antonioEmployee);

        assertThat(updateResult).isOne();

        employeeActual
                = fakeEmployeeDao.selectEmployeeById(kristenId);

        assertThat(employeeActual.getEmployeeId()).isEqualTo(kristenId);
        assertThat(employeeActual)
                .isEqualToIgnoringGivenFields(antonioEmployee, "employeeId");

    }

    @Test
    void deleteEmployeeById() {

        UUID kristenId = UUID.randomUUID();
        Employee kristenEmployee = new Employee(kristenId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        int persistResult
                = fakeEmployeeDao.persistNewEmployee(kristenId, kristenEmployee);

        assertThat(persistResult).isOne();

        List<Employee> allEmployees = fakeEmployeeDao.selectAllEmployees();
        Employee employee = fakeEmployeeDao.selectEmployeeById(kristenId);

        assertThat(allEmployees).hasSize(4);
        assertThat(employee).isEqualToComparingFieldByField(kristenEmployee);

        int deleteResult = fakeEmployeeDao.deleteEmployeeById(kristenId);
        allEmployees = fakeEmployeeDao.selectAllEmployees();
        employee = fakeEmployeeDao.selectEmployeeById(kristenId);

        assertThat(deleteResult).isOne();
        assertThat(allEmployees).hasSize(3);
        assertThat(employee).isNull();

    }
}