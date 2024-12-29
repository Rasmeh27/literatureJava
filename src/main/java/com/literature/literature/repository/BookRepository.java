package com.literature.literature.repository;

import com.literature.literature.model.BooksQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository  extends JpaRepository<BooksQuery, Long> {
    Optional<BooksQuery> findByApiId(String apiId);
}
