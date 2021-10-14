package com.leonardo.bookstoremanager.repository;

import com.leonardo.bookstoremanager.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
