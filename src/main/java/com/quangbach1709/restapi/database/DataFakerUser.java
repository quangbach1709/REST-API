package com.quangbach1709.restapi.database;

import com.github.javafaker.Faker;
import com.quangbach1709.restapi.entity.User;
import com.quangbach1709.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Component
public class DataFakerUser implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Tạo dữ liệu giả
        Faker faker = new Faker();
        List<User> fakeUsers = IntStream.rangeClosed(1, 10) // Tạo 10 bản ghi
                .mapToObj(i -> {
                    User user = new User();
                    user.setEmail(faker.internet().emailAddress());
                    user.setPassword(faker.internet().password());
                    user.setIsActive(faker.bool().bool());
                    return user;
                })
                .collect(Collectors.toList());
        userRepository.saveAll(fakeUsers);

    }
}
