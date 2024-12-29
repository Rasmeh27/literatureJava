package com.literature.literature.service;

import com.literature.literature.model.BooksQuery;
import com.literature.literature.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BooksQuery saveBook(BooksQuery booksQuery) {
        if(bookRepository.findByApiId(booksQuery.getApiId()).isPresent()) {
            throw new RuntimeException("El libro ya esta registrado.");
        }
        return bookRepository.save(booksQuery);
    }

    public List<BooksQuery> getAllBooks() {
        return bookRepository.findAll();
    }
}
