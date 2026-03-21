package org.spring.managinglibrary.managinglibrary.service;

import lombok.RequiredArgsConstructor;
import org.spring.managinglibrary.managinglibrary.model.Books;
import org.spring.managinglibrary.managinglibrary.model.BorrowRecord;
import org.spring.managinglibrary.managinglibrary.model.Member;
import org.spring.managinglibrary.managinglibrary.repository.BookRepository;
import org.spring.managinglibrary.managinglibrary.repository.BorrowRecordRepository;
import org.spring.managinglibrary.managinglibrary.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor // automatically creates constructor for final fields
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public BorrowRecord borrowBook(Long memberId, Long bookId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Books books = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BorrowRecord record = new BorrowRecord();
        record.setMember(member);
        record.setBooks(books);
        record.setBorrowDate(LocalDate.now());
        record.setReturnDate(null);

        return borrowRecordRepository.save(record);
    }
    public BorrowRecord returnBook(Long recordId) {

        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setReturnDate(LocalDate.now());

        return borrowRecordRepository.save(record);
    }
    public List<BorrowRecord> getAllRecords() {
        return borrowRecordRepository.findAll();
    }
    public BorrowRecord getRecordById(Long id) {
        return borrowRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }
    public void deleteRecord(Long id) {
        if (!borrowRecordRepository.existsById(id)) {
            throw new RuntimeException("Record not found");
        }
        borrowRecordRepository.deleteById(id);
    }
}