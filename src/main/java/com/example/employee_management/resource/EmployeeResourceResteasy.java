package com.example.employee_management.resource;

import com.example.employee_management.auth.ApplicationUser;
import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Validated
@Component
@Path("api/v1/employees")
public class EmployeeResourceResteasy {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeResourceResteasy(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees(@QueryParam("gender") String gender) {

        //user from userdetails
//        ApplicationUser user = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(user.getAuthorities());
//
//        String authority = grantedAuthorities.get(0).getAuthority().replace("ROLE_", "");
//
//        return "BOSS".equals(authority) ||
//                "MANAGER".equals(authority) ?
//                employeeService.getAllEmployees(Optional.ofNullable(gender)) :
//                employeeService.getAllEmployees(Optional.empty()).stream()
//                        .filter(employee -> authority.equals(employee.getAuth()))
//                        .collect(Collectors.toList());

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
    public void persistNewEmployee(@Valid Employee newEmployee) {
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
