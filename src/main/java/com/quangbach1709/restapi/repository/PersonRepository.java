package com.quangbach1709.restapi.repository;

import com.quangbach1709.restapi.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @EntityGraph(attributePaths = {"company"})
        // Tải company cùng với person
    Optional<Person> findById(Long id);

    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.company WHERE p.id = :id")
    Optional<Person> findByIdWithCompany(@Param("id") Long id);

    Page<Person> findByCompanyId(Long companyId, Pageable pageable);
}
