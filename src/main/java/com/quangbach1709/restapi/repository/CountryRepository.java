package com.quangbach1709.restapi.repository;

import com.quangbach1709.restapi.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
