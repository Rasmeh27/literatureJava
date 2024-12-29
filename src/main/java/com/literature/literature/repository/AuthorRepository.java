package com.literature.literature.repository;

import com.literature.literature.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByDeathDateIsNull();

    Optional<Author> findByName(String name);
}
