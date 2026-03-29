package org.spring.managinglibrary.managinglibrary.controller;

import org.spring.managinglibrary.managinglibrary.model.Books;
import org.spring.managinglibrary.managinglibrary.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService){
        this.bookService=bookService;
    }
    @PostMapping
    public Books addBooks(@RequestBody Books books){
        return bookService.addBook(books);
    }
    @GetMapping
    public List<Books> getBooks(){
        return bookService.getAllBooks();
    }
    @GetMapping("/{Id}")
    public Books getBookById(@PathVariable Long Id){
        return bookService.getBookById(Id);
    }
    @DeleteMapping("/{Id}")
    public String deleteBook(@PathVariable Long Id){
        bookService.deleteBook(Id);
        return "Book deleted successfully";
    }
    @PutMapping("/{Id}")
    public Books updateBook(@PathVariable Long Id,@RequestBody Books books){
        return bookService.updateBook(Id,books);
    }
}
