package com.literature.literature.controller;

import com.literature.literature.dto.GutendexResponse;
import com.literature.literature.model.Author;
import com.literature.literature.model.BooksQuery;
import com.literature.literature.service.APIConsume;
import com.literature.literature.service.AuthorService;
import com.literature.literature.service.BookService;
import org.springframework.stereotype.Controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Controller
public class ConsoleController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final APIConsume apiConsume;
    private final String BASE_URL = "https://gutendex.com/books?search=";

    public ConsoleController(BookService bookService, AuthorService authorService, APIConsume apiConsume) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.apiConsume =  apiConsume;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n--- Menú de opciones ---");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar libros registrados");
            System.out.println("3. Listar autores registrados");
            System.out.println("4. Listar autores vivos");
            System.out.println("5. Listar libros por idioma");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> buscarLibroPorTitulo(scanner);
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivos();
                case 5 -> listarLibrosPorIdioma(scanner);
                case 0 -> System.out.println("Saliendo del programa...");
            }
        }while (option != 0);
    }

    private void buscarLibroPorTitulo(Scanner scanner) {
        try {
            System.out.println("Ingrese el titulo del libro: ");
            String title = scanner.nextLine();
            String encodedTitle = URLEncoder.encode(title.replace(" ", "%20"), StandardCharsets.UTF_8);
            GutendexResponse apiResponse = apiConsume.obtenerDatos(BASE_URL + encodedTitle);

            System.out.println("\nResultados encontrados: " + apiResponse.getCount());
            for (GutendexResponse.Book book : apiResponse.getResults()) {
                System.out.println("\nTítulo: " + book.getTitle());
                System.out.println("Autor(es): " + book.getAuthors().stream()
                        .map(GutendexResponse.Book.Author::getName)
                        .collect(Collectors.joining(", ")));
                System.out.println("Idiomas: " + String.join(", ", book.getLanguages()));
                System.out.println("Descargas: " + book.getDownloadCount());
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el libro: " + e.getMessage());
        }
    }

    private void listarLibrosRegistrados() {
        List<BooksQuery> books = bookService.getAllBooks();
        if(books.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else  {
            System.out.println("Libros registrados!");
            books.forEach(booksQuery -> System.out.println
                    ("Título: " + booksQuery.getTitle() + ", Autor: " + booksQuery.getAuthor() +
                            ", Idioma: " + booksQuery.getLanguage()));
        }
    }

    private void listarAutoresRegistrados() {
        List<Author> authors = authorService.getAliveAuthors();
        if (authors.isEmpty()) {
            System.out.println("No hay autores registrados.");
        }else {
            System.out.println("Autores registrados correctamente!");
            authors.forEach(author -> System.out.println("Nombre: "+ author.getName() +
                    ", Fecha de nacimiento: "+author.getBirthDate() +
                    ", Fecha de fallecimiento: "+author.getDeathDate()));
        }
    }

    private void listarAutoresVivos() {
        List<Author> aliveAuthors = authorService.getAliveAuthors();
        if (aliveAuthors.isEmpty()) {
            System.out.println("No hay autores vivos registrados");
        }else {
            System.out.println("Autores vivos registados");
            aliveAuthors.forEach(author -> System.out.println("Nombre: " + author.getName() +
                    ", Fecha de nacimiento: " + author.getBirthDate()));
        }
    }

    private void listarLibrosPorIdioma(Scanner scanner) {
        System.out.println("Ingrese el idioma: ");
        String language = scanner.nextLine();
        List<BooksQuery> books = bookService.getAllBooks();
        List<BooksQuery> filteredBooks = books.stream().filter(book -> book.getLanguage()
                .equalsIgnoreCase(language)).toList();

        if(filteredBooks.isEmpty()) {
            System.out.println("No hay libros registrados en este idioma.");
        } else {
            System.out.println("Libro en idioma: " + language);
            filteredBooks.forEach(booksQuery -> System.out.println("Titulo: "+booksQuery.getTitle() + ", Autor: "+booksQuery.getAuthor()));
        }
    }
}
