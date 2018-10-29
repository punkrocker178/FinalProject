/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.test;

import edu.tdt.persistence.Author;
import edu.tdt.persistence.Book;
import edu.tdt.persistence.BookStoreSessionRemote;
import edu.tdt.persistence.Publisher;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author Steel
 */
public class BookStoreEJBTester {

    private JNDI jndi;
    private Scanner sc;

    public BookStoreEJBTester() {
        jndi = new JNDI();
    }

    private void showBookStoreGUI() {
        System.out.println("\n===========================");
        System.out.println("Welcome to the Bookstore");
        System.out.println("===========================");
        System.out.print("Options: \n"
                + "1. Add Book With New Author & Publisher \n"
                + "2. Add Book With Existing Author & Publisher \n"
                + "3. List All Books (Current Session) \n"
                + "4. Find Book By Id \n"
                + "5. List Book's Author(s)\n"
                + "6. Remove Book By Id\n"
                + "7. Assign author to book \n"
                + "8. Edit Book information \n"
                + "Enter Choice: ");
    }

    private void showAllPublishers(BookStoreSessionRemote storeBean) {
        List<Publisher> publishers = storeBean.getPublishers();
        if (publishers.isEmpty()) {
            System.out.println("There isn't any publisher in the Database!\n");
        }
        System.out.println("\n=================================");
        System.out.println("Listing All Publishers In Database");
        System.out.println("=================================");
        for (Publisher publisher : publishers) {
            System.out.println(publisher.getPublisherId() + "\t" + publisher.getPublisherName());

        }
        System.out.println();
    }

    private void showAllAuthors(BookStoreSessionRemote storeBean) {
        List<Author> authors = storeBean.getAuthors();
        if (authors.isEmpty()) {
            System.out.println("There isn't any author in the Database!\n");
        }
        System.out.println("\n=================================");
        System.out.println("Listing All Authors In Database");
        System.out.println("=================================");
        for (Author author : authors) {
            System.out.println(author.getAuthorId() + "\t" + author.getAuthorName());

        }
        System.out.println();
    }

    private void listAllBooks(BookStoreSessionRemote storeBean) {
        List<Book> booksList = storeBean.getBooks();
        DecimalFormat df = new DecimalFormat("0,000,000VND");
        String format = "%1$4s %2$50s %3$40s %4$15s %n";
        if (booksList.isEmpty()) {
            System.out.println("There is no book in the bookstore!\n");
        }
        System.out.println("\n=================================");
        System.out.println("Listing All Books In Bookstore");
        System.out.println("=================================");
        System.out.printf(format, "ID", "Name", "Publisher", "Price");
        for (Book book : booksList) {
                System.out.print(String.format("%1$-10s %2$-70.70s %3$-25s"
                        ,book.getBookId(),book.getBookName()
                ,book.getPublisher()));
                System.out.print(new DecimalFormat("0,000 VND").format(book.getPrice()));
                System.out.println("");
        }
        System.out.println();
    }

    private void salesFunctions(BookStoreSessionRemote storeBean) {
        sc = new Scanner(System.in);
        int choice = 0;
        do {
            showBookStoreGUI();
            choice = Integer.parseInt(sc.nextLine());
            Book book;
            Publisher publisher;
            Author author;
            String bookId, bookName, authorId, authorName, publisherId, publisherName;
            int price;
            switch (choice) {
//                Insert new user
                case 1:
                    // Add a book with new Author & Publisher
                    System.out.println("Enter bookId");
                    bookId = sc.nextLine();
                    System.out.print("Enter book name: ");
                    bookName = sc.nextLine();
                    System.out.println("Enter Publisher Id");
                    publisherId = sc.nextLine();
                    System.out.println("Enter Publisher Name");
                    publisherName = sc.nextLine();
                    System.out.println("Enter book price");
                    price = sc.nextInt();
                    sc.nextLine();
                    System.out.println("How many authors are there?");
                    int numOfAuthors = sc.nextInt();

                    for(int i=0;i<numOfAuthors;i++){
                        System.out.println("Enter the author's ID");
                        authorId = sc.nextLine();
                        System.out.println("Enter the author's name");
                        authorName = sc.nextLine();
                        storeBean.addAuthor(new Author(authorId,authorName));
                    }
                    
                    publisher = new Publisher(publisherId, publisherName);
                    book = new Book();
                    book.setBookId(bookId);
                    book.setBookName(bookName);
                    book.setPublisher(publisher);
                    book.setPrice(price);
                    storeBean.addPublisher(publisher);
                    storeBean.addBook(book);
                    break;
                case 2:
                    showAllPublishers(storeBean);
                    System.out.println("================================");
                    showAllAuthors(storeBean);
                    System.out.println("================================");
                    System.out.println("Enter bookId");
                    bookId = sc.nextLine();
                    System.out.print("Enter book name: ");
                    bookName = sc.nextLine();
                    System.out.println("Enter Publisher Id");
                    publisherId = sc.nextLine();
                    System.out.println("Enter book price");
                    price = sc.nextInt();
                    publisher = storeBean.getPublisherById(publisherId);
                    book = new Book();
                    book.setBookId(bookId);
                    book.setBookName(bookName);
                    book.setPublisher(publisher);
                    book.setPrice(price);
                    storeBean.addBook(book);
                    break;
                case 3:
                    // Print all books (using current session bean)
                    listAllBooks(storeBean);
                    break;
                case 4:
                    //Find Book By Id
                    System.out.println("Enter book Id");
                    bookId = sc.nextLine();
                    book = storeBean.getBookById(bookId);
                    System.out.println(book);
                    break;
                case 5:
                    //List Author(s) of Book
                    System.out.println("Enter book Id");
                    bookId = sc.nextLine();
                    ArrayList<Author> authorsList = storeBean.getBookAuthors(bookId);
                    for (Author authorsOfBook : authorsList) {
                        System.out.println(authorsOfBook);
                    }
                    break;
                case 6:
                    //Remove book
                    System.out.println("Enter bookId");
                    bookId = sc.nextLine();
                    storeBean.removeBooks(bookId);
                    break;
                case 7:
                    //Assign author to book
                    System.out.println("Enter book Id");
                    bookId = sc.nextLine();
                    System.out.println("Enter author Id");
                    authorId = sc.nextLine();
                    storeBean.insertBookAuthor(bookId, authorId);
                    break;
                case 8:
                    //Edit Book information
                    String[] input = new String[3];
                    System.out.println("Enter bookId");
                    bookId = sc.nextLine();
                    System.out.println("Edit name (Press enter to skip)");
                    input[0] = sc.nextLine();
                    System.out.println("Edit publisherId (Press enter to skip)");
                    input[1] = sc.nextLine();
                    System.out.println("Edit price (Press enter to skip)");
                    input[2] = sc.nextLine();
                    storeBean.updateBook(bookId, input);
                    break;
                default:
                    // Exit
                    break;
            }
        } while (choice != 9);

    }

    public void testEntityEJB() {
        try {
            // Scanner definition
            sc = new Scanner(System.in);

            // Lookup the LibrarySessionBeanRemote
            String jndiProperties = jndi.getJNDI();
            BookStoreSessionRemote storeBean = (BookStoreSessionRemote) jndi.getInitialContex().lookup(jndiProperties);

            salesFunctions(storeBean);

//            String jndiProperties2 = jndi.getJNDI2();
//            SystemManagementRemote session = (SystemManagementRemote) jndi.getInitialContex().lookup(jndiProperties2);
            // Log-in
//            String _username, _password;
//            System.out.println("Log-in to TDT System Management");
//            System.out.print("Username: ");
//            _username = sc.nextLine();
//            System.out.print("Password: ");
//            _password = sc.nextLine();
//
//            if (!session.hashPassword(_password).equals(session.getUserPassword(_username))) {
//                System.err.println("Wrong username/password!");
//                return;
//            }
//            Check User role
//            if (session.isAdmin(_username)) {
//                adminFunctions(session);
//            } else if (session.isSales(_username)) {
//                salesFunctions(libBean, jndiProperties);
//            } else {
//                showGuestGUI();
//            }
            sc.close();

        } catch (NamingException ex) {
            Logger.getLogger(BookStoreEJBTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        BookStoreEJBTester ejb = new BookStoreEJBTester();
        ejb.testEntityEJB();
    }

}
