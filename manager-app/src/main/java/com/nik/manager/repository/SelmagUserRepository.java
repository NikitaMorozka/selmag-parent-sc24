package com.nik.manager.repository;

import com.nik.manager.entity.SelmagUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SelmagUserRepository extends JpaRepository<SelmagUser, Integer> {

    Optional<SelmagUser> findAllByUsername(String username);

}
