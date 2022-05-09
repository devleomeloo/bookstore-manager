package com.leonardo.bookstoremanager.repository;

import com.leonardo.bookstoremanager.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailOrUserName(String email, String userName);

    Optional<User> findByUserName(String userName);
}
