package com.leonardo.bookstoremanager.repository;

import com.leonardo.bookstoremanager.entitys.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
