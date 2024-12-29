package com.literature.literature.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literature.literature.dto.GutendexResponse;
import com.literature.literature.model.Author;
import com.literature.literature.model.BooksQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Service
public class APIConsume {
    private final ObjectMapper objectMapper;
    private final BookService bookService;
    private final AuthorService authorService;

    public APIConsume(BookService bookService, AuthorService authorService) {
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public GutendexResponse obtenerDatos(String url) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                GutendexResponse gutendexResponse = objectMapper.readValue(response.body(), GutendexResponse.class);
                // Guardar los libros en la base de datos
                saveBooks(gutendexResponse);
                return gutendexResponse;
            } else {
                throw new RuntimeException("Error en la respuesta de la API. Código: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consultar la API: " + e.getMessage(), e);
        }
    }

    private void saveBooks(GutendexResponse response) {
        for (GutendexResponse.Book apiBook : response.getResults()) {
            // Guardar el libro
            BooksQuery book = new BooksQuery();
            book.setApiId(String.valueOf(apiBook.getId()));
            book.setTitle(apiBook.getTitle());
            book.setLanguage(String.join(",", apiBook.getLanguages()));

            // Procesar autores
            if (!apiBook.getAuthors().isEmpty()) {
                GutendexResponse.Book.Author apiAuthor = apiBook.getAuthors().get(0);
                book.setAuthor(apiAuthor.getName());

                // Guardar el autor
                saveAuthor(apiAuthor);
            }

            try {
                bookService.saveBook(book);
            } catch (RuntimeException e) {
                // El libro ya existe, lo ignoramos
            }
        }
    }

    private void saveAuthor(GutendexResponse.Book.Author apiAuthor) {
        Author author = new Author();
        author.setName(apiAuthor.getName());

        // Convertir años a LocalDate
        if (apiAuthor.getBirthYear() != null) {
            author.setBirthDate(LocalDate.of(apiAuthor.getBirthYear(), 1, 1));
        }
        if (apiAuthor.getDeathYear() != null) {
            author.setDeathDate(LocalDate.of(apiAuthor.getDeathYear(), 1, 1));
        }

        authorService.saveAuthor(author);
    }
}