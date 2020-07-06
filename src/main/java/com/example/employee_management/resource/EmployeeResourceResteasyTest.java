package com.example.employee_management.resource;

import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Path("api/v1/test")
public class EmployeeResourceResteasyTest {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeResourceResteasyTest(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees(@QueryParam("gender") String gender) {
        return employeeService.getAllEmployees(Optional.ofNullable(gender));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{employeeId}")
    public Employee getEmployeeById(@PathParam("employeeId") UUID employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void persistNewEmployee(Employee newEmployee) {
        employeeService.persistNewEmployee(newEmployee.getEmployeeId(), newEmployee);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{employeeId}")
    public void updateEmployeeById(@PathParam("employeeId") UUID employeeId,
                                   Employee update) {
        employeeService.updateEmployeeById(employeeId, update);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{employeeId}")
    public void deleteEmployeeById(@PathParam("employeeId") UUID employeeId) {
        employeeService.deleteEmployeeById(employeeId);
    }
}
