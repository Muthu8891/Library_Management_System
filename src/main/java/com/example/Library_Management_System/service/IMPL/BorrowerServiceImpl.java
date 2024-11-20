// BorrowerServiceImpl.java
package com.example.Library_Management_System.service.IMPL;

import com.example.Library_Management_System.dto.BorrowerDTO;
import com.example.Library_Management_System.entity.Book;
import com.example.Library_Management_System.entity.Borrower;
import com.example.Library_Management_System.exception.BookAlreadyBorrowedException;
import com.example.Library_Management_System.exception.BookNotBorrowedException;
import com.example.Library_Management_System.exception.DuplicateBorrowerEmailException;
import com.example.Library_Management_System.exception.ResourceNotFoundException;
import com.example.Library_Management_System.repo.BookRepository;
import com.example.Library_Management_System.repo.BorrowerRepository;
import com.example.Library_Management_System.service.BorrowerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerServiceImpl implements BorrowerService {

    private final BorrowerRepository borrowerRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BorrowerServiceImpl(BorrowerRepository borrowerRepository, BookRepository bookRepository, ModelMapper modelMapper) {
        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BorrowerDTO registerBorrower(BorrowerDTO borrowerDTO) {
        // Check borrower email already exists
        if (borrowerRepository.existsByEmail(borrowerDTO.getEmail())) {
            throw new DuplicateBorrowerEmailException("Borrower email already exists.");
        }

        Borrower borrower = modelMapper.map(borrowerDTO, Borrower.class);
        Borrower savedBorrower = borrowerRepository.save(borrower);
        return modelMapper.map(savedBorrower, BorrowerDTO.class);
    }
    @Override
    public void borrowBook(Long borrowerId, Long bookId) {
        // Retrieve borrower from repository
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found: " + borrowerId));

        // Retrieve book from repository
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));

        // Check book already borrowed
        if (book.getBorrower() != null) {
            throw new BookAlreadyBorrowedException("Book borrowed already");
        }

        // Set the book borrowed
        book.setBorrower(borrower);

        // Save book entity
        bookRepository.save(book);
    }

    @Override
    public void returnBook(Long borrowerId, Long bookId) {
        // Retrieve borrower from repository
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found: " + borrowerId));

        // Retrieve book from repository
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));

        // Logic for book was borrowed by the borrower
        if ( book.getBorrower() ==null || !book.getBorrower().equals(borrower)) {
            throw new BookNotBorrowedException("Book not borrowed by this borrower");
        }

        // set book as returned
        book.setBorrower(null);

        // Save book entity
        bookRepository.save(book);
    }

    @Override
    public List<BorrowerDTO> getAllBorrowers() {
        return borrowerRepository.findAll().stream()
                .map(borrower -> modelMapper.map(borrower, BorrowerDTO.class))
                .toList();
    }

    @Override
    public BorrowerDTO getBorrowerById(Long borrowerId) {
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found: " + borrowerId));
        return modelMapper.map(borrower, BorrowerDTO.class);
    }

}