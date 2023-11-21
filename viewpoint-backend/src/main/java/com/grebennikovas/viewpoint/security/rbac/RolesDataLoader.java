package com.grebennikovas.viewpoint.security.rbac;

import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RolesDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        Set<Privilege> dashboardPrivileges = initPrivileges(List.of("READ DASHBOARD LIST", "READ DASHBOARD", "EDIT DASHBOARD", "DELETE DASHBOARD"));
        Set<Privilege> chartPrivileges = initPrivileges(List.of("READ CHART LIST", "READ CHART", "EDIT CHART", "DELETE CHART"));
        Set<Privilege> datasetPrivileges = initPrivileges(List.of("READ DATASET LIST", "READ DATASET", "EDIT DATASET", "DELETE DATASET"));
        Set<Privilege> sourcePrivileges = initPrivileges(List.of("READ SOURCE LIST", "READ SOURCE", "EDIT SOURCE", "DELETE SOURCE"));
        Set<Privilege> userPrivileges = initPrivileges(List.of("READ USER LIST", "READ USER", "EDIT USER", "DELETE USER"));
        Set<Privilege> securityPrivileges = initPrivileges(List.of("READ ROLE LIST", "READ ROLE", "EDIT ROLE", "DELETE ROLE"));

        Set<Privilege> adminPrivileges = new HashSet<>();
        adminPrivileges.addAll(dashboardPrivileges);
        adminPrivileges.addAll(chartPrivileges);
        adminPrivileges.addAll(datasetPrivileges);
        adminPrivileges.addAll(sourcePrivileges);
        adminPrivileges.addAll(userPrivileges);
        adminPrivileges.addAll(securityPrivileges);

        Set<Privilege> analystPrivileges = new HashSet<>();
        adminPrivileges.addAll(dashboardPrivileges);
        adminPrivileges.addAll(chartPrivileges);
        adminPrivileges.addAll(datasetPrivileges);
        analystPrivileges.addAll(initPrivileges(List.of("READ SOURCE LIST", "READ USER LIST", "READ USER", "READ ROLE LIST", "READ ROLE")));

        createRoleIfNotFound("ADMIN", adminPrivileges);
        createRoleIfNotFound("ANALYST", analystPrivileges);

        Role adminRole = roleRepository.findByName("ADMIN");
        Role analystRole = roleRepository.findByName("ANALYST");

        User admin = new User();
        admin.setUsername("admin");
        admin.setFirstname("admin");
        admin.setLastname("admin");
        admin.setEmail("admin@example.com");
        admin.setActive(true);
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(Set.of(adminRole));
        userRepository.save(admin);

        User analyst = new User();
        analyst.setUsername("analyst");
        analyst.setFirstname("analyst");
        analyst.setLastname("analyst");
        analyst.setEmail("analyst@example.com");
        analyst.setActive(true);
        analyst.setPassword(passwordEncoder.encode("analyst"));
        analyst.setRoles(Set.of(analystRole));
        userRepository.save(analyst);

        alreadySetup = true;
    }

    @Transactional
    Set<Privilege> initPrivileges(List<String> names) {
        return names.stream()
                .map(this::createPrivilegeIfNotFound).collect(Collectors.toSet());
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Set<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}