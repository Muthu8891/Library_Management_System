// BookService.java
package com.example.Library_Management_System.service;

import com.example.Library_Management_System.dto.BookDTO;
import java.util.List;

public interface BookService {
    BookDTO registerBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();

    BookDTO getBookById(Long bookId);
}
