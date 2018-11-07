/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.bean;

import edu.tdt.persistence.Author;
import edu.tdt.persistence.Book;
import edu.tdt.persistence.Publisher;
import edu.tdt.persistence.Receipt;
import edu.tdt.persistence.StockInput;
import java.util.ArrayList;
import java.util.Date;
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

    void addReceipt(Receipt receipt);

    void addStockInputReceipt(StockInput stockIn);

    Book getBookById(String Id);

    Publisher getPublisherById(String Id);

    List<Book> getBooks();

    List<Author> getAuthors();

    List<Publisher> getPublishers();

    ArrayList<Author> getBookAuthors(String bookId);

    int insertReceiptBook(String receiptId, ArrayList<Book> checkedOutBooks);

    int insertStockReceiptBook(String stockInId, ArrayList<Book> newBooks, int[] bookBuyPrice);

    void insertBookAuthor(String bookId, String authorId);

    void removeBooks(String bookId);

    void updateBook(String bookId, String[] input);

    List<Book> getBooksFromReceipt(Receipt receipt);

    List<Book> getBooksFromStockInput(StockInput stockInput);

    List<Receipt> viewReceipt(int days);

    List<Receipt> viewReceipt(String dateFrom, String dateTo);

    List<StockInput> viewStockInput(int days);

    List<StockInput> viewStockInput(String dateFrom, String dateTo);

    String autoID(char argument);

}
