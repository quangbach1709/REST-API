package com.quangbach1709.restapi.database;

import com.github.javafaker.Faker;
import com.quangbach1709.restapi.entity.Person;
import com.quangbach1709.restapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Component
public class DataFakerPerson implements CommandLineRunner {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Tạo danh sách các người giả
        List<Person> fakePersons = IntStream.rangeClosed(1, 10) // Tạo 10 bản ghi
                .mapToObj(i -> {
                    Person person = new Person();
                    person.setFullName(faker.name().fullName());
                    person.setGender(faker.options().option("Nam", "Nữ"));
                    person.setBirthDate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    person.setPhoneNumber(faker.phoneNumber().cellPhone());
                    person.setAddress(faker.address().fullAddress());
                    return person;
                })
                .collect(Collectors.toList());

        // Lưu vào database
        personRepository.saveAll(fakePersons);
    }
}
