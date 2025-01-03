package com.quangbach1709.restapi.database;

import com.github.javafaker.Faker;
import com.quangbach1709.restapi.entity.Company;
import com.quangbach1709.restapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Component
public class DataFakerCompany implements CommandLineRunner {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        List<Company> fakeCompanies = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> {
                    Company company = new Company();
                    company.setName(faker.company().name());
                    company.setCode(faker.company().industry());
                    company.setAddress(faker.address().fullAddress());
                    return company;
                })
                .collect(Collectors.toList());
        companyRepository.saveAll(fakeCompanies);
    }
}
