package com.leonardo.bookstoremanager.repository;

import com.leonardo.bookstoremanager.entitys.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);
}
