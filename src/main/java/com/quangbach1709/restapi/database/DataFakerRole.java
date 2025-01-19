package com.quangbach1709.restapi.database;

import com.github.javafaker.Faker;
import com.quangbach1709.restapi.entity.Role;
import com.quangbach1709.restapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//@Component
public class DataFakerRole implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        List<Role> fakeRoles = Arrays.asList("ADMIN", "USER", "MANAGER", "EDITOR").stream()
                .map(roleName -> {
                    Role role = new Role();
                    role.setRole(roleName);
                    role.setDescription(faker.lorem().sentence());
                    return role;
                })
                .collect(Collectors.toList());
        roleRepository.saveAll(fakeRoles);
    }
}
