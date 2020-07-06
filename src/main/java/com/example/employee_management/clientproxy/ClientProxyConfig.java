package com.example.employee_management.clientproxy;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientProxyConfig {

    @Value("${employees.api.url.v1}")
    private String employeesEndpointUrl;

    @Bean
    public EmployeeResourceV1 getEmployeeResourceV1() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(employeesEndpointUrl);
        EmployeeResourceV1 proxy = target.proxy(EmployeeResourceV1.class);
        return proxy;
    }
}
