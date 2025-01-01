package com.quangbach1709.restapi.repository;

import com.quangbach1709.restapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
