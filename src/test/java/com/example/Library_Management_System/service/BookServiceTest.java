package com.example.Library_Management_System.service;

import com.example.Library_Management_System.dto.BookDTO;
import com.example.Library_Management_System.entity.Book;
import com.example.Library_Management_System.entity.Borrower;
import com.example.Library_Management_System.exception.ResourceNotFoundException;
import com.example.Library_Management_System.repo.BookRepository;
import com.example.Library_Management_System.repo.BorrowerRepository;
import com.example.Library_Management_System.service.IMPL.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BorrowerRepository borrowerRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookDTO createBookDTO(String isbn, String title, String author) {
        return new BookDTO(null, isbn, title, author, null);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRegisterBook() {
        // Given
        BookDTO bookDTO = createBookDTO("1234567890", "Test Book123", "Test Author123");
        Book book = new Book(1L, "1234567890", "Test Book12", "Test Author123", null);

        // Mocking
        when(modelMapper.map(bookDTO, Book.class)).thenReturn(book);
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        BookDTO savedBook = bookService.registerBook(bookDTO);

        // Then
        assertEquals(bookDTO.getIsbn(), savedBook.getIsbn());
        assertEquals(bookDTO.getTitle(), savedBook.getTitle());
        assertEquals(bookDTO.getAuthor(), savedBook.getAuthor());
    }

    @Test
    void testRegisterBookWithExistingISBNAndSameTitleAuthor() {
        // Given
        BookDTO bookDTO = createBookDTO("1234567890", "Test Book123", "Test Author123");
        Book existingBook = new Book(1L, "1234567890", "Test Book123", "Test Author123", null);
        List<Book> existingBooks = List.of(existingBook);

        // Mocking
        when(bookRepository.findByIsbn(bookDTO.getIsbn())).thenReturn(existingBooks);
        when(modelMapper.map(bookDTO, Book.class)).thenReturn(existingBook);


        // When / Then
        assertDoesNotThrow(() -> bookService.registerBook(bookDTO));
    }

    @Test
    void testRegisterBookWithExistingISBNAndDifferentTitle() {
        // Given
        BookDTO bookDTO = createBookDTO("1234567890", "Book Title", "Test Author");
        Book existingBook = new Book(1L, "1234567890", "Test Book123", "Test Author123", null);
        List<Book> existingBooks = List.of(existingBook);

        // Mocking
        when(bookRepository.findByIsbn(bookDTO.getIsbn())).thenReturn(existingBooks);

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> bookService.registerBook(bookDTO));
    }

    @Test
    void testRegisterBookWithExistingISBNAndDifferentAuthor() {
        // Given
        BookDTO bookDTO = createBookDTO("1234567890", "Test Book123", "Author Different");
        Book existingBook = new Book(1L, "1234567890", "Test Book123", "Test Author123", null);
        List<Book> existingBooks = List.of(existingBook);

        // Mocking
        when(bookRepository.findByIsbn(bookDTO.getIsbn())).thenReturn(existingBooks);

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> bookService.registerBook(bookDTO));
    }

    @Test
    void testGetAllBooks() {
        // Given
        Book book1 = new Book(1L, "1234567890", "Book 1", "Author 1", null);
        Book book2 = new Book(2L, "0987654321", "Book 2", "Author 2", null);
        List<Book> mockBooks = Arrays.asList(book1, book2);

        // Mocking
        when(bookRepository.findAll()).thenReturn(mockBooks);
        when(modelMapper.map(book1, BookDTO.class)).thenReturn(new BookDTO(book1.getId(), book1.getIsbn(), book1.getTitle(), book1.getAuthor(), null));
        when(modelMapper.map(book2, BookDTO.class)).thenReturn(new BookDTO(book2.getId(), book2.getIsbn(), book2.getTitle(), book2.getAuthor(), null));

        // When
        List<BookDTO> books = bookService.getAllBooks();

        // Then
        assertEquals(2, books.size());
        assertEquals(book1.getIsbn(), books.get(0).getIsbn());
        assertEquals(book2.getIsbn(), books.get(1).getIsbn());
    }


    @Test
    void testRegisterBookWithValidBorrowerId() {
        // Given
        BookDTO bookDTO = createBookDTO("1234567890", "Test Book123", "Test Author123");
        bookDTO.setBorrowerId(1L);
        Borrower borrower = new Borrower(1L, "qwe@outlook.com", "James");

        // Mocking
        when(modelMapper.map(bookDTO, Book.class)).thenReturn(new Book());
        when(modelMapper.map(new Book(), BookDTO.class)).thenReturn(bookDTO);
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        // When
        BookDTO savedBook = bookService.registerBook(bookDTO);

        // Then
        assertNotNull(savedBook);
        assertEquals(bookDTO.getBorrowerId(), savedBook.getBorrowerId());
    }

    @Test
    void testRegisterBookWithInvalidBorrowerId() {
        // Given
        BookDTO bookDTO = createBookDTO("1234567890", "Test Book123", "Test Author123");
        bookDTO.setBorrowerId(111L);

        // Mocking
        when(modelMapper.map(bookDTO, Book.class)).thenReturn(new Book());
        when(borrowerRepository.findById(111L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ResourceNotFoundException.class, () -> bookService.registerBook(bookDTO));
    }

    @Test
    void testRegisterBookWithNullBorrowerId() {
        // Given
        BookDTO bookDTO = createBookDTO("1234567890", "Test Book123", "Test Author123");
        bookDTO.setBorrowerId(null);

        // Mocking
        when(modelMapper.map(bookDTO, Book.class)).thenReturn(new Book());
        when(modelMapper.map(new Book(), BookDTO.class)).thenReturn(bookDTO);
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        // When
        BookDTO savedBook = bookService.registerBook(bookDTO);

        // Then
        assertNotNull(savedBook);
        assertNull(savedBook.getBorrowerId());
    }
}