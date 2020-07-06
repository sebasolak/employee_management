package com.example.employee_management.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("fakeUsersDao")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }


    private List<ApplicationUser> getApplicationUsers() {
        return Arrays.asList(
                new ApplicationUser("boss",
                        passwordEncoder.encode("pass"),
                        new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority("BOSS"))),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser("manager",
                        passwordEncoder.encode("pass"),
                        new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority("MANAGER"))),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser("wayne",
                        passwordEncoder.encode("pass"),
                        new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority("M1"))),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser("cassandra",
                        passwordEncoder.encode("pass"),
                        new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority("M2"))),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser("louis",
                        passwordEncoder.encode("pass"),
                        new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority("M3"))),
                        true,
                        true,
                        true,
                        true
                )
        );
    }
}
