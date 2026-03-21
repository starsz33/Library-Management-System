package org.spring.managinglibrary.managinglibrary.service;

import org.spring.managinglibrary.managinglibrary.model.Books;
import org.spring.managinglibrary.managinglibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }
    public Books addBook(Books books){
        bookRepository.save(books);
        return books;
    }
    public List<Books> getAllBooks(){

        return bookRepository.findAll();
    }
    public Books getBookById(Long Id){
        return bookRepository.findById(Id).orElseThrow(() ->new RuntimeException("Book not Found"));
    }
    public void deleteBook(Long Id){
        bookRepository.deleteById(Id);
    }
    public Books updateBook(Long Id,Books updatedBook){
        Books books=getBookById(Id);
        books.setTitle(updatedBook.getTitle());
        books.setAuthor(updatedBook.getAuthor());
        books.setIsbn(updatedBook.getIsbn());
        return bookRepository.save(books);
    }

}
