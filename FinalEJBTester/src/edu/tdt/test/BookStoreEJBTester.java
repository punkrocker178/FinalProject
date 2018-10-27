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
        System.out.println("\n=========================");
        System.out.println("Welcome to TDTU Bookstore");
        System.out.println("=========================");
        System.out.print("Options: \n"
                + "1. Add Book \n"
                + "2. List All Books (Current Session) \n"
                + "3. Find Book By Id \n"
                + "4. List Book's Author(s)\n"
                + "5. Remove Book By Name\n"
                + "6. \n"
                + "Enter Choice: ");
    }

    private void salesFunctions(BookStoreSessionRemote storeBean) {
        sc = new Scanner(System.in);
        String format = "%-15s %-100s %-25s %s%n";
        int choice = 0;
        do {
            showBookStoreGUI();
            choice = Integer.parseInt(sc.nextLine());
            Book book;
            String bookId;
            String authorId;
            switch (choice) {
//                Insert new user
                case 1:
                    // Add a book
                    System.out.print("Enter book name: ");
                    String bookName = sc.nextLine();
                    System.out.println("Enter book price");
                    int price = sc.nextInt();
                    System.out.println("Enter Publisher Id");
                    System.out.println("Enter Publisher Name");
//                        Publisher publisher = new Publisher("P001","NXB Test");
                    Book b = new Book();
//                        b.setBookId("B020");
//                        b.setBookName(bookName);
//                        b.setPublisherId(publisher);
//                        b.setPrice(price);
//                        storeBean.addPublisher(publisher);
                    storeBean.addBook(b);
                    break;
                case 2:
                    // Print all books (using current session bean)
                    List<Book> booksList = storeBean.getBooks();
                    List<Publisher> publishersList = storeBean.getPublishers();
                    if (booksList.isEmpty()) {
                        System.out.println("There is no book in the bookstore!\n");
                    }
                    
                    System.out.println("\n=========================");
                    System.out.println("Listing Books in TDTU Bookstore");
                    System.out.println("=========================");
                    System.out.printf(format,"ID","Name","Publisher","Price");
                    for (int i = 0; i < booksList.size(); i++) {
                        System.out.printf(format,booksList.get(i).getBookId()
                                ,booksList.get(i).getBookName()
                                ,booksList.get(i).getPublisherId()
                                ,booksList.get(i).getPrice());
                    }
                    System.out.println();

                    break;
                // Print all books (using new session bean)
                case 3:
                    System.out.println("Enter book Id");
                    bookId = sc.nextLine();
                    book = storeBean.getBookById(bookId);
                    System.out.println(book);
                    break;
                case 4:
                    System.out.println("Enter book Id");
                    bookId = sc.nextLine();
                    ArrayList<Author> authorsList = storeBean.getBookAuthors(bookId);
                    
//                    book = storeBean.getBookById(bookId);
//                    Collection<Author> authorsList = book.getAuthorCollection();
                    for(Author author : authorsList){
                        System.out.println(author);
                    }
                    break;
                case 5:
                    System.out.println("Enter book name");
                    bookId = sc.nextLine();
                    storeBean.removeBooks(bookId);
                    break;
                case 6:
                     System.out.println("Enter book Id");
                    bookId = sc.nextLine();
                     System.out.println("Enter author Id");
                    authorId = sc.nextLine();
                    storeBean.insertBookAuthor(bookId, authorId);
                    break;
                case 7:
                    String[] input = new String [3];
                    System.out.println("Enter bookId");
                    bookId = sc.nextLine();
                    System.out.println("Edit name");
                    input[0] = sc.nextLine();
                    System.out.println("Edit publisher");
                    input[1] = sc.nextLine();
                    System.out.println("Edit price");
                    input[2] = sc.nextLine();
                    storeBean.updateBook(bookId,input);
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
