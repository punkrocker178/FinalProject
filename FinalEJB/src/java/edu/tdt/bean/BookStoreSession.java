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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Steel
 */
@Stateful
public class BookStoreSession implements BookStoreSessionRemote {

    @PersistenceContext(unitName = "FinalEJBPU")
    private EntityManager entityManager;

    public BookStoreSession() {

    }

    @Override
    public void addBook(Book book) {
        entityManager.persist(book);
    }

    public void addPublisher(Publisher publisher) {
        entityManager.persist(publisher);
    }

    @Override
    public void addAuthor(Author author) {
        entityManager.persist(author);
    }

    public void addReceipt(Receipt receipt) {
        entityManager.persist(receipt);
    }

    public void addStockInputReceipt(StockInput stockIn) {
        entityManager.persist(stockIn);
    }

    public int insertStockReceiptBook(String inputReceiptId, ArrayList<Book> newBooks, int[] bookBuyPrice) {
        StockInput stockReceipt = entityManager.find(StockInput.class, inputReceiptId);
        int total = 0, i = 0;
        String bookId;
        for (Book book : newBooks) {
            bookId = book.getBookId();
            Book bookInDB = entityManager.find(Book.class, bookId);
            bookInDB.getStockInputCollection().add(stockReceipt);
            stockReceipt.getBookCollection().add(bookInDB);
            total += bookBuyPrice[i] * book.getQuantity();
            i++;
        }
        stockReceipt.setPrice(total);
        return total;
    }

    public int insertReceiptBook(String orderId, ArrayList<Book> checkedOutBooks) {
        Receipt receipt = entityManager.find(Receipt.class, orderId);
        String bookId;
        int total = 0;

        for (Book book : checkedOutBooks) {
            bookId = book.getBookId();
            Book bookInDB = entityManager.find(Book.class, bookId);
            bookInDB.getReceiptCollection().add(receipt);
            receipt.getBookCollection().add(bookInDB);

            //Trừ số sách được lấy ra khỏi db
            bookInDB.setQuantity(bookInDB.getQuantity() - book.getQuantity());
            total += book.getPrice() * book.getQuantity();
        }
        receipt.setTotal(total);
        return total;
    }

    public void insertBookAuthor(String bookId, String authorId) {
        Book book = entityManager.find(Book.class, bookId);
        Author author = entityManager.find(Author.class, authorId);
        book.getAuthorCollection().add(author);
        author.getBookCollection().add(book);
    }

    @Override
    public ArrayList<Author> getBookAuthors(String bookId) {
        Book book = entityManager.find(Book.class, bookId);
        ArrayList<Author> authorsList = new ArrayList();
        if (book != null) {
            for (Author author : book.getAuthorCollection()) {
                authorsList.add(author);
            }
        }
        return authorsList;
    }

    @Override
    public Book getBookById(String Id) {
        return (Book) entityManager.createNamedQuery("Book.findByBookId")
                .setParameter("bookId", Id).getSingleResult();
    }

    @Override
    public List<Book> getBooks() {
        return entityManager.createNamedQuery("Book.findAll").getResultList();
    }

    public List<Author> getAuthors() {
        return entityManager.createNamedQuery("Author.findAll").getResultList();
    }

    public Publisher getPublisherById(String Id) {
        return (Publisher) entityManager.createNamedQuery("Publisher.findByPublisherId")
                .setParameter("publisherId", Id).getSingleResult();
    }

    public List<Publisher> getPublishers() {
        return entityManager.createNamedQuery("Publisher.findAll").getResultList();
    }

    public void removeBooks(String bookId) {
        Book book = entityManager.find(Book.class, bookId);
        //Xóa danh sách Book_Author rồi mới xóa xách khỏi DB
        List<Author> authorsList = (List<Author>) book.getAuthorCollection();
        for (Author author : authorsList) {
            List<Book> booksList = (List<Book>) author.getBookCollection();
            for (Book bookToBeDeleted : booksList) {
                if (bookToBeDeleted.equals(book)) {
                    booksList.remove(bookToBeDeleted);
                    break;
                }
            }
        }
        entityManager.remove(book);
    }

    public void updateBook(String bookId, String[] input) {
        Book book = entityManager.find(Book.class, bookId);
        if (!input[0].equals("")) {
            book.setBookName(input[0]);
        }
        if (!input[1].equals("")) {
            book.getPublisher().setPublisherId(input[1]);
        }
        if (!input[2].equals("")) {
            book.setPrice(Integer.parseInt(input[2]));
        }
        if (!input[3].equals("")) {
            book.setQuantity(Integer.parseInt(input[3]));
        }
    }

    @Override
    public String autoID(char argument) {
        String id = "";
        try {
            switch (argument) {
                case 'A':
                    id = entityManager.createNativeQuery("SELECT author_id FROM public.author\n"
                            + "ORDER BY author_id DESC LIMIT 1").getSingleResult().toString();
                    break;

                case 'B':
                    id = entityManager.createNativeQuery("SELECT book_id FROM public.book\n"
                            + "ORDER BY book_id DESC LIMIT 1").getSingleResult().toString();
                    break;

                case 'E':
                    break;

                case 'P':
                    id = entityManager.createNativeQuery("SELECT publisher_id FROM public.publisher\n"
                            + "ORDER BY publisher_id DESC LIMIT 1").getSingleResult().toString();
                    break;

                case 'R':
                    id = entityManager.createNativeQuery("SELECT order_id FROM public.receipt\n"
                            + "ORDER BY order_id DESC LIMIT 1").getSingleResult().toString();
                    break;

                case 'S':
                    id = entityManager.createNativeQuery("SELECT input_receipt_id FROM public.stock_input\n"
                            + "ORDER BY input_receipt_id DESC LIMIT 1").getSingleResult().toString();
                    break;
            }
            String[] num = id.split("\\D");
            //increment digit part of an id
            int digit = Integer.parseInt(num[1]) + 1;

            if (digit < 10) {
                id = argument + "00" + digit;
            } else if (digit < 100) {
                id = argument + "0" + digit;
            } else if (digit > 100) {
                id = argument + digit + "";
            }
        } catch (NoResultException e) {
            return argument + "001";
        }
        return id;
    }

    public List<Book> getBooksFromReceipt(Receipt receipt) {
        receipt = entityManager.merge(receipt);
        List<Book> booksFromReceipt = new ArrayList<Book>();
        for (Book book : receipt.getBookCollection()) {
            booksFromReceipt.add(book);
        }
        return booksFromReceipt;
    }
    
    public List<Book> getBooksFromStockInput(StockInput stockInput){
        stockInput = entityManager.merge(stockInput);
        List<Book> booksFromReceipt = new ArrayList<Book>();
        for (Book book : stockInput.getBookCollection()) {
            booksFromReceipt.add(book);
        }
        return booksFromReceipt;
    }

    public List<Receipt> viewReceipt(int days) {
        return entityManager.createNativeQuery("SELECT order_id, date, username, total\n"
                + "FROM public.receipt WHERE date >= current_date - integer '" + days + "'", Receipt.class).getResultList();
    }

    public List<Receipt> viewReceipt(String dateFrom, String dateTo) {
        List<Receipt> receiptList = entityManager.createNativeQuery(
                "SELECT order_id, date, username, total\n"
                + "FROM public.receipt\n"
                + "WHERE date>=to_date(:dateFrom,'dd/MM/yyyy')\n"
                + "AND date <= to_date(:dateTo,'dd/MM/yyyy')", Receipt.class)
                .setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo).getResultList();
        return receiptList;
    }
    
    public List<StockInput> viewStockInput(int days) {
        return entityManager.createNativeQuery("SELECT input_receipt_id, date, username, price\n"
                + "FROM public.stock_input WHERE date >= current_date - integer '" + days + "'", StockInput.class).getResultList();
    }

    public List<StockInput> viewStockInput(String dateFrom, String dateTo) {
        List<StockInput> stockReceiptList = entityManager.createNativeQuery(
                "SELECT input_receipt_id, date, username, price\n"
                + "FROM public.stock_input\n"
                + "WHERE date>=to_date(:dateFrom,'dd/MM/yyyy')\n"
                + "AND date <= to_date(:dateTo,'dd/MM/yyyy')", StockInput.class)
                .setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo).getResultList();
        return stockReceiptList;
    }

}
