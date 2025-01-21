package com.quangbach1709.restapi.database;

import com.github.javafaker.Faker;
import com.quangbach1709.restapi.entity.Company;
import com.quangbach1709.restapi.entity.Department;
import com.quangbach1709.restapi.repository.CompanyRepository;
import com.quangbach1709.restapi.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.Locale;
import java.util.Random;

//@Component
public class DataFakerDepartment implements CommandLineRunner {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only seed if departments table is empty
        if (departmentRepository.count() == 0) {
            seedDepartments();
        }
    }

    private void seedDepartments() {
        Faker faker = new Faker(new Locale("en"));
        Random random = new Random();

        // Get a random company
        Company company = companyRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No companies found"));

        // First create 5 parent departments
        Department[] parentDepts = new Department[5];
        for (int i = 0; i < 5; i++) {
            Department dept = new Department();
            dept.setName(faker.commerce().department());
            dept.setCode("DEPT-" + faker.number().digits(4));
            dept.setCompany(company);
            parentDepts[i] = departmentRepository.save(dept);
        }

        // Create 5 child departments
        for (int i = 0; i < 5; i++) {
            Department dept = new Department();
            dept.setName(faker.commerce().department());
            dept.setCode("DEPT-" + faker.number().digits(4));
            dept.setCompany(company);
            dept.setParent(parentDepts[random.nextInt(5)]); // Randomly assign parent
            departmentRepository.save(dept);
        }
    }
}