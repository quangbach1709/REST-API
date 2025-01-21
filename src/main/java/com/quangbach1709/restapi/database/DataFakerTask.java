package com.quangbach1709.restapi.database;

import com.github.javafaker.Faker;
import com.quangbach1709.restapi.entity.Company;
import com.quangbach1709.restapi.entity.Person;
import com.quangbach1709.restapi.entity.Project;
import com.quangbach1709.restapi.repository.CompanyRepository;
import com.quangbach1709.restapi.repository.PersonRepository;
import com.quangbach1709.restapi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component // Đảm bảo annotation @Component không bị comment
public class DataFakerTask implements CommandLineRunner {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {
        if (projectRepository.count() == 0) {
            seedProjects();
        }
    }

    private void seedProjects() {
        Faker faker = new Faker(new Locale("en"));
        Random random = new Random();

        // Get all companies
        List<Company> companies = companyRepository.findAll();

        // Generate 10 projects
        for (int i = 0; i < 10; i++) {
            Project project = new Project();
            project.setName(faker.company().catchPhrase());
            project.setCode("PRJ-" + faker.number().digits(4));
            project.setDescription(faker.lorem().paragraph());

            // Assign random company
            Company randomCompany = companies.get(random.nextInt(companies.size()));
            project.setCompany(randomCompany);

            // Get persons from the same company
            List<Person> companyPersons = personRepository.findByCompanyId(randomCompany.getId(), null).getContent();

            // Randomly select 2-5 persons for the project
            if (!companyPersons.isEmpty()) {
                int numberOfPersons = random.nextInt(4) + 2; // 2 to 5 persons
                Set<Person> selectedPersons = new HashSet<>();

                for (int j = 0; j < numberOfPersons && j < companyPersons.size(); j++) {
                    selectedPersons.add(companyPersons.get(random.nextInt(companyPersons.size())));
                }

                project.setPersons(selectedPersons);
            }

            projectRepository.save(project);
        }
    }
}