/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;


/**
 *
 * @author Steel
 */
@Remote
public interface BookStoreSessionRemote {
//    Them thong tin vao database
    void addBook(Book book);
    void addAuthor(Author author);
    void addPublisher(Publisher publisher);
    Book getBookById(String Id); 
    void insertBookAuthor(String bookId,String authorId);
    void removeBooks(String bookId);
    void updateBook(String bookId,String[] input);
    List<Publisher> getPublishers();
    ArrayList<Author> getBookAuthors(String bookId);
//    String getAllBooksNames();
    List<Book> getBooks();
}
