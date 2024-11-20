// BookController.java
package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.BookDTO;
import com.example.Library_Management_System.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookDTO> registerBook(@RequestBody BookDTO bookDTO) {
        logger.info("Create new book: {}", bookDTO);
        try {
            BookDTO savedBook = bookService.registerBook(bookDTO);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        logger.info("Get Available books:");
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookDetails(@PathVariable Long bookId) {
        logger.info("Book Id: {}", bookId.toString());
        BookDTO bookDTO = bookService.getBookById(bookId);
        return ResponseEntity.ok(bookDTO);
    }
}
