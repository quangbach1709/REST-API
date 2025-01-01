package com.quangbach1709.restapi.repository;

import com.quangbach1709.restapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
