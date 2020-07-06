package com.example.employee_management.clientproxy;

import com.example.employee_management.model.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

public interface EmployeeResourceV1 {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Employee> getAllEmployees(@QueryParam("gender") String gender);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{employeeId}")
    Employee getEmployeeById(@PathParam("employeeId") UUID employeeId);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void persistNewEmployee(Employee newEmployee);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{employeeId}")
    void updateEmployeeById(@PathParam("employeeId") UUID employeeId,
                            Employee update);

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{employeeId}")
    void deleteEmployeeById(@PathParam("employeeId") UUID employeeId);
}
