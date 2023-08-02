package com.Auton.gibg.repository;

import com.Auton.gibg.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   User findByEmail(String email);
}
