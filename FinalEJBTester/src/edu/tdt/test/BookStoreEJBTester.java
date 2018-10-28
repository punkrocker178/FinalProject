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
                + "1. Add Book \n"
                + "2. List All Books (Current Session) \n"
                + "3. Find Book By Id \n"
                + "4. List Book's Author(s)\n"
                + "5. Remove Book By Id\n"
                + "6. Assign author to book \n"
                + "7. Edit Book information \n"
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
        List<Publisher> publishersList = storeBean.getPublishers();
        String format = "%-15s %-100s %-25s %s%n";
        if (booksList.isEmpty()) {
            System.out.println("There is no book in the bookstore!\n");
        }
        System.out.println("\n=================================");
        System.out.println("Listing All Books In Bookstore");
        System.out.println("=================================");
        System.out.printf(format, "ID", "Name", "Publisher", "Price");
        for (int i = 0; i < booksList.size(); i++) {
            System.out.printf(format, booksList.get(i).getBookId(),
                    booksList.get(i).getBookName(),
                    booksList.get(i).getPublisher(),
                    booksList.get(i).getPrice());
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
            String bookId, bookName, authorId, authorName, publisherId, publisherName;
            switch (choice) {
//                Insert new user
                case 1:
                    // Add a book
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
                    System.out.println("Enter Publisher Name");
                    publisherName = sc.nextLine();
                    System.out.println("Enter book price");
                    int price = sc.nextInt();
                    Publisher publisher = new Publisher(publisherId, publisherName);
                    Book b = new Book();
                    b.setBookId(bookId);
                    b.setBookName(bookName);
                    b.setPublisher(publisher);
                    b.setPrice(price);
                    storeBean.addPublisher(publisher);
                    storeBean.addBook(b);
                    break;
                case 2:
                    // Print all books (using current session bean)
                    listAllBooks(storeBean);
                    break;
                case 3:
                    //Find Book By Id
                    System.out.println("Enter book Id");
                    bookId = sc.nextLine();
                    book = storeBean.getBookById(bookId);
                    System.out.println(book);
                    break;
                case 4:
                    //List Author(s) of Book
                    System.out.println("Enter book Id");
                    bookId = sc.nextLine();
                    ArrayList<Author> authorsList = storeBean.getBookAuthors(bookId);
                    for (Author author : authorsList) {
                        System.out.println(author);
                    }
                    break;
                case 5:
                    //Remove book
                    System.out.println("Enter bookId");
                    bookId = sc.nextLine();
                    storeBean.removeBooks(bookId);
                    break;
                case 6:
                    //Assign author to book
                    System.out.println("Enter book Id");
                    bookId = sc.nextLine();
                    System.out.println("Enter author Id");
                    authorId = sc.nextLine();
                    storeBean.insertBookAuthor(bookId, authorId);
                    break;
                case 7:
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
        } while (choice != 8);

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
