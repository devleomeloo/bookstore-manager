package com.leonardo.bookstoremanager.repository;

import com.leonardo.bookstoremanager.entitys.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
