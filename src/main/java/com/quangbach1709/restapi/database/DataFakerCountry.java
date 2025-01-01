package com.quangbach1709.restapi.database;


import com.github.javafaker.Faker;
import com.quangbach1709.restapi.entity.Person;
import com.quangbach1709.restapi.repository.CountryRepository;
import com.quangbach1709.restapi.repository.PersonRepository;
import com.quangbach1709.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.quangbach1709.restapi.entity.Country;

//@Component
public class DataFakerCountry implements CommandLineRunner {
    @Autowired
    private CountryRepository countryRepository;


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Tạo danh sách các quốc gia giả
        List<Country> fakeCountries = IntStream.rangeClosed(1, 10) // Tạo 10 bản ghi
                .mapToObj(i -> {
                    Country country = new Country();
                    country.setName(faker.country().name());
                    country.setCode(faker.country().countryCode2());
                    country.setDescription(faker.lorem().sentence());
                    return country;
                })
                .collect(Collectors.toList());

        // Lưu vào database
        countryRepository.saveAll(fakeCountries);


    }
}
