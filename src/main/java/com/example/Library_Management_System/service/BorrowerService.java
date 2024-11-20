
// BorrowerService.java
package com.example.Library_Management_System.service;

import com.example.Library_Management_System.dto.BorrowerDTO;
import java.util.List;

public interface BorrowerService {
    BorrowerDTO registerBorrower(BorrowerDTO borrowerDTO);
    void borrowBook(Long borrowerId, Long bookId);
    void returnBook(Long borrowerId, Long bookId);
    List<BorrowerDTO> getAllBorrowers();

    BorrowerDTO getBorrowerById(Long borrowerId);
}