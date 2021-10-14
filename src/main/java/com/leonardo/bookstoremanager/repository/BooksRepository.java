package com.leonardo.bookstoremanager.repository;

import com.leonardo.bookstoremanager.entitys.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Long> {
}
