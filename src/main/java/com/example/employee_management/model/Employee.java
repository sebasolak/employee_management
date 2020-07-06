package com.example.employee_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

    private UUID employeeId;
    @NotNull(message = "first name require")
    private final String name;
    @NotNull
    private final String surname;
    @NotNull
    @Email
    private final String email;
    @NotNull
    @Max(120)
    @Min(18)
    private final int age;
    @NotNull
    private final Gender gender;
    @NotNull
    @Min(0)
    @Max(50)
    private final int yearsOfWork;
    @NotNull
    @Min(1000)
    @Max(50_000)
    private final double baseSalary;
    @NotNull
    private final boolean employeeBonus;
    @NotNull
    private final String auth;

    public Employee(@JsonProperty("employeeId") UUID employeeId,
                    @JsonProperty("name") String name,
                    @JsonProperty("surname") String surname,
                    @JsonProperty("email") String email,
                    @JsonProperty("age") int age,
                    @JsonProperty("gender") Gender gender,
                    @JsonProperty("yearsOfWork") int yearsOfWork,
                    @JsonProperty("baseSalary") double baseSalary,
                    @JsonProperty("employeeBonus") boolean employeeBonus,
                    @JsonProperty("auth") String auth) {
        this.employeeId = employeeId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.yearsOfWork = yearsOfWork;
        this.baseSalary = baseSalary;
        this.employeeBonus = employeeBonus;
        this.auth = auth;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public int getYearsOfWork() {
        return yearsOfWork;
    }


    public double getBaseSalary() {
        return baseSalary;
    }

    public boolean isEmployeeBonus() {
        return employeeBonus;
    }

    public String getAuth() {
        return auth;
    }

    public int getHireDate() {
        return LocalDate.now().minusYears(yearsOfWork).getYear();
    }

    public String getTotalMonthSalary() {

        double bonusForSeniority = yearsOfWork * 10;

        double totalMonthSalary = employeeBonus ?
                baseSalary + (baseSalary * 0.15) + bonusForSeniority :
                baseSalary + bonusForSeniority;

        return String.format("Salary for %s is %.2f.\n Expected annual salary is %.2f.",
                LocalDate.now().getMonth(), totalMonthSalary, totalMonthSalary * 12);
    }

    public static Employee newEmployee(UUID employeeId, Employee employee) {
        return new Employee(employeeId, employee.getName(), employee.getSurname(), employee.getEmail(),
                employee.getAge(), employee.getGender(), employee.getYearsOfWork(),
                employee.getBaseSalary(), employee.isEmployeeBonus(), employee.getAuth());
    }

}
