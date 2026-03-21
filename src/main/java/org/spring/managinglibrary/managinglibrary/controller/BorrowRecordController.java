package org.spring.managinglibrary.managinglibrary.controller;

import lombok.RequiredArgsConstructor;
import org.spring.managinglibrary.managinglibrary.model.BorrowRecord;
import org.spring.managinglibrary.managinglibrary.service.BorrowRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrow")
@RequiredArgsConstructor
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;
    @PostMapping
    public BorrowRecord borrowBook(@RequestParam Long memberId, @RequestParam Long bookId) {
        return borrowRecordService.borrowBook(memberId, bookId);
    }
    @PutMapping("/return/{id}")
    public BorrowRecord returnBook(@PathVariable Long id) {
        return borrowRecordService.returnBook(id);
    }
    @GetMapping
    public List<BorrowRecord> getAllRecords() {
        return borrowRecordService.getAllRecords();
    }
    @GetMapping("/{id}")
    public BorrowRecord getRecordById(@PathVariable Long id) {
        return borrowRecordService.getRecordById(id);
    }
    @DeleteMapping("/{id}")
    public String deleteRecord(@PathVariable Long id) {
        borrowRecordService.deleteRecord(id);
        return "Borrow record deleted successfully";
    }
}