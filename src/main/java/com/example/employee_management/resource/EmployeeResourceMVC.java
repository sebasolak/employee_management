package com.example.employee_management.resource;

import com.example.employee_management.auth.ApplicationUser;
import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;

@Validated
//@RestController
//@RequestMapping("api/v1/employees")
public class EmployeeResourceMVC {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeResourceMVC(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Employee> getAllEmployees(@QueryParam("gender") String gender) {

        //user from ApplicationUser
//        ApplicationUser user = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(user.getAuthorities());
//
//        String authority = grantedAuthorities.get(0).getAuthority().replace("ROLE_","");
//
//        return "BOSS".equals(authority) ||
//                "MANAGER".equals(authority) ?
//                employeeService.getAllEmployees(Optional.ofNullable(gender)) :
//                employeeService.getAllEmployees(Optional.empty()).stream()
//                        .filter(employee -> authority.equals(employee.getAuth()))
//                        .collect(Collectors.toList());

        return employeeService.getAllEmployees(Optional.ofNullable(gender));
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{employeeId}"
    )
    public Employee getEmployeeById(@PathVariable("employeeId") UUID employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void persistNewEmployee(@RequestBody @Valid Employee newEmployee) {
        employeeService.persistNewEmployee(newEmployee.getEmployeeId(), newEmployee);
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "{employeeId}"
    )
    public void updateEmployeeById(@PathVariable("employeeId") UUID employeeId,
                                   @RequestBody Employee update) {
        employeeService.updateEmployeeById(employeeId, update);
    }

    @DeleteMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{employeeId}"
    )
    public void deleteEmployeeById(@PathVariable("employeeId") UUID employeeId) {
        employeeService.deleteEmployeeById(employeeId);
    }
}
