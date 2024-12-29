package com.literature.literature.service;

import com.literature.literature.model.Author;
import com.literature.literature.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAliveAuthors() {
        return authorRepository.findByDeathDateIsNull();
    }

    public Author saveAuthor(Author author) {
        // Verificar si el autor ya existe por nombre
        Optional<Author> existingAuthor = authorRepository.findByName(author.getName());
        if (existingAuthor.isPresent()) {
            return existingAuthor.get();
        }
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}