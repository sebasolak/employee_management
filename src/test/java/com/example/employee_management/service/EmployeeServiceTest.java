package com.example.employee_management.service;

import com.example.employee_management.dao.FakeEmployeeDao;
import com.example.employee_management.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.employee_management.model.Gender.FEMALE;
import static com.example.employee_management.model.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class EmployeeServiceTest {

    @Mock
    private FakeEmployeeDao fakeEmployeeDao;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        employeeService = new EmployeeService(fakeEmployeeDao);
    }

    @Test
    void shouldGetAllEmployees() {
        UUID kristenId = UUID.randomUUID();
        Employee kristenEmployee = new Employee(kristenId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        ImmutableList<Employee> employees = new ImmutableList.Builder<Employee>()
                .add(kristenEmployee)
                .build();

        given(fakeEmployeeDao.selectAllEmployees()).willReturn(employees);

        List<Employee> allEmployees = employeeService.getAllEmployees(Optional.empty());

        assertThat(allEmployees).hasSize(1);

        Employee employee = allEmployees.get(0);

        assertThat(employee).isEqualToComparingFieldByField(kristenEmployee);
    }

    @Test
    void shouldGetEmployeesByGender() {
        UUID kristenId = UUID.randomUUID();
        Employee kristenEmployee = new Employee(kristenId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        UUID antonioId = UUID.randomUUID();
        Employee antonioEmployee = new Employee(antonioId, "Antonio", "Howard",
                "antoniohoward@gmail.com", 30, MALE, 3,
                2500.00, true, "M5");

        ImmutableList<Employee> employees = new ImmutableList.Builder<Employee>()
                .add(kristenEmployee)
                .add(antonioEmployee)
                .build();

        given(fakeEmployeeDao.selectAllEmployees()).willReturn(employees);

        List<Employee> allEmployees
                = employeeService.getAllEmployees(Optional.empty());

        assertThat(allEmployees).hasSize(2);

        List<Employee> allFemaleEmployees
                = employeeService.getAllEmployees(Optional.of("female"));

        assertThat(allFemaleEmployees).hasSize(1);
        assertThat(allFemaleEmployees.get(0)).isEqualToComparingFieldByField(kristenEmployee);

        List<Employee> allMaleEmployees
                = employeeService.getAllEmployees(Optional.of("male"));

        assertThat(allMaleEmployees).hasSize(1);
        assertThat(allMaleEmployees.get(0)).isEqualToComparingFieldByField(antonioEmployee);
    }

    @Test
    void shouldGetEmployeeById() {
        UUID kristenId = UUID.randomUUID();
        Employee kristenEmployee = new Employee(kristenId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        given(fakeEmployeeDao.selectEmployeeById(kristenId)).willReturn(kristenEmployee);

        Employee kristenMaybe = employeeService.getEmployeeById(kristenId);

        assertThat(kristenMaybe).isEqualToComparingFieldByField(kristenEmployee);
    }

    @Test
    void shouldPersistNewEmployee() {
        Employee kristenEmployee = new Employee(null, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        given(fakeEmployeeDao.persistNewEmployee(any(UUID.class), eq(kristenEmployee))).willReturn(1);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);

        int persistResult = employeeService.persistNewEmployee(null, kristenEmployee);

        verify(fakeEmployeeDao).persistNewEmployee(any(UUID.class), captor.capture());

        Employee employee = captor.getValue();

        assertThat(employee.getEmployeeId()).isInstanceOf(UUID.class);
        assertThat(employee).isEqualToIgnoringGivenFields(kristenEmployee, "employeeId");

        assertThat(persistResult).isOne();
    }

    @Test
    void shouldUpdateEmployeeById() {
        UUID kristenId = UUID.randomUUID();
        Employee kristenEmployee = new Employee(kristenId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        given(fakeEmployeeDao.selectEmployeeById(kristenId)).willReturn(kristenEmployee);
        given(fakeEmployeeDao.updateEmployeeById(kristenId, kristenEmployee)).willReturn(1);

        ArgumentCaptor<UUID> uidCaptor = ArgumentCaptor.forClass(UUID.class);
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        int updateResult = employeeService.updateEmployeeById(kristenId, kristenEmployee);

        verify(fakeEmployeeDao).selectEmployeeById(kristenId);
        verify(fakeEmployeeDao).updateEmployeeById(uidCaptor.capture(), employeeCaptor.capture());
        Employee employee = employeeCaptor.getValue();

        assertThat(employee).isEqualToComparingFieldByField(kristenEmployee);

        assertThat(updateResult).isOne();
    }

    @Test
    void shouldDeleteEmployeeById() {
        UUID kristenId = UUID.randomUUID();
        Employee kristenEmployee = new Employee(kristenId, "Kristen", "Kelly",
                "kriskelly@gmail.com", 35, FEMALE, 8,
                4500.00, false, "M4");

        given(fakeEmployeeDao.selectEmployeeById(kristenId)).willReturn(kristenEmployee);
        given(fakeEmployeeDao.deleteEmployeeById(kristenId)).willReturn(1);


        int deleteResult = employeeService.deleteEmployeeById(kristenId);

        verify(fakeEmployeeDao).selectEmployeeById(kristenId);
        verify(fakeEmployeeDao).deleteEmployeeById(kristenId);

        assertThat(deleteResult).isOne();
    }
}