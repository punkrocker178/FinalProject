/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.test;

import edu.tdt.bean.BookStoreSessionRemote;
import edu.tdt.persistence.Account;
import edu.tdt.persistence.Author;
import edu.tdt.persistence.Book;
import edu.tdt.persistence.Publisher;
import edu.tdt.persistence.Receipt;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Chứa tất cả chức năng dành cho client
 *
 * @author Steel
 */
public class Functions {

    private BookStoreSessionRemote storeBean;
    private Scanner sc;

    public String format = "%1$-10s %2$-70.70s %3$-25s %4$s %n";

    //Sách khách đem ra quầy thanh toán
    private ArrayList<Book> checkedOutBooks = new ArrayList<Book>();
    //Số lượng của những quyển sách đó
    private ArrayList<Integer> bookQty = new ArrayList();

    public DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    private UI ui = new UI();

    public Functions() {

    }

    public Functions(BookStoreSessionRemote storeBean) {
        this.storeBean = storeBean;
    }

    void warehouseFunctions(BookStoreSessionRemote storeBean) {
        sc = new Scanner(System.in, "ISO-8859-1");
        int choice = 0;
        try {
            do {
                ui.showWarehouseUI();
                choice = Integer.parseInt(sc.nextLine());
                Book book;
                Publisher publisher;
                Author author;
                String bookId;
                String bookName;
                String authorId;
                String authorName;
                String publisherId;
                String publisherName;
                int price;
                int quantity;
                switch (choice) {
                    //                Insert new user
                    case 1:
                        // Add a book with new Author & Publisher
                        listAllBooks(storeBean);
                        bookId = storeBean.autoID('B');
                        publisherId = storeBean.autoID('P');
                        System.out.print("Enter book name: ");
                        bookName = sc.nextLine();
                        System.out.println("Enter Publisher Name");
                        publisherName = sc.nextLine();
                        System.out.println("Enter book price");
                        price = sc.nextInt();
                        System.out.println("Enter quantity:");
                        quantity = sc.nextInt();
                        sc.nextLine();
                        System.out.println("How many authors are there?");
                        int numOfAuthors = sc.nextInt();
                        sc.nextLine();
                        publisher = new Publisher(publisherId, publisherName);
                        book = new Book(bookName, price, quantity);
                        book.setPublisher(publisher);
                        book.setBookId(bookId);
                        storeBean.addPublisher(publisher);
                        storeBean.addBook(book);
                        for (int i = 0; i < numOfAuthors; i++) {
                            authorId = storeBean.autoID('A');
                            System.out.println("Enter the author's name");
                            authorName = sc.nextLine();
                            storeBean.addAuthor(new Author(authorId, authorName));
                            storeBean.insertBookAuthor(bookId, authorId);
                        }
                        break;
                    case 2:
                        // Add a book with existing Author & Publisher
                        showAllPublishers(storeBean);
                        System.out.println("================================");
                        showAllAuthors(storeBean);
                        System.out.println("================================");
                        bookId = storeBean.autoID('B');
                        authorId = storeBean.autoID('A');
                        System.out.print("Enter book's name: ");
                        bookName = sc.nextLine();
                        System.out.println("Enter Publisher's ID");
                        publisherId = sc.nextLine();
                        System.out.println("Enter book's price");
                        price = sc.nextInt();
                        System.out.println("Enter quantity:");
                        quantity = sc.nextInt();
                        sc.nextLine();
                        publisher = storeBean.getPublisherById(publisherId);
                        book = new Book(bookName, price, quantity);
                        book.setBookId(bookId);
                        book.setPublisher(publisher);
                        storeBean.addBook(book);
                        System.out.println("How many authors are there?");
                        numOfAuthors = sc.nextInt();
                        sc.nextLine();
                        for (int i = 0; i < numOfAuthors; i++) {
                            System.out.println("Enter author's ID ");
                            authorId = sc.nextLine();
                            storeBean.insertBookAuthor(bookId, authorId);
                        }
                        break;
                    case 3:
                        //Add author
                        authorId = storeBean.autoID('A');
                        System.out.println("Enter author's name");
                        authorName = sc.nextLine();
                        author = new Author(authorId, authorName);
                        storeBean.addAuthor(author);
                        break;
                    case 4:
                        //Add publisher
                        publisherId = storeBean.autoID('P');
                        System.out.println("Enter publisher's name");
                        publisherName = sc.nextLine();
                        publisher = new Publisher(publisherId, publisherName);
                        storeBean.addPublisher(publisher);
                        break;
                    case 6:
                        //Find Book By Id
                        String ch = "";
                        System.out.println("Enter book Id");
                        bookId = sc.nextLine();
                        book = storeBean.getBookById(bookId);
                        System.out.println(book);
                        break;
                    case 7:
                        //List Author(s) of Book
                        System.out.println("Enter book Id");
                        bookId = sc.nextLine();
                        ArrayList<Author> authorsList = storeBean.getBookAuthors(bookId);
                        for (Author authorsOfBook : authorsList) {
                            System.out.println(authorsOfBook);
                        }
                        break;
                    case 8:
                        //Remove book
                        System.out.println("Enter bookId");
                        bookId = sc.nextLine();
                        storeBean.removeBooks(bookId);
                        break;
                    case 9:
                        //Assign author to book
                        System.out.println("Enter book Id");
                        bookId = sc.nextLine();
                        System.out.println("Enter author Id");
                        authorId = sc.nextLine();
                        storeBean.insertBookAuthor(bookId, authorId);
                        break;
                    case 10:
                        //Edit Book information
                        String[] input = new String[4];
                        System.out.println("Enter bookId");
                        bookId = sc.nextLine();
                        System.out.println("Edit name (Press enter to skip)");
                        input[0] = sc.nextLine();
                        System.out.println("Edit publisherId (Press enter to skip)");
                        input[1] = sc.nextLine();
                        System.out.println("Edit price (Press enter to skip)");
                        input[2] = sc.nextLine();
                        System.out.println("Edit quantity:");
                        input[3] = sc.nextLine();
                        storeBean.updateBook(bookId, input);
                        break;
                    default:
                        // Exit
                        break;
                }
            } while (choice < 12 && choice > 0);
        } catch (NumberFormatException e) {
            System.err.println("Please input an integer!");
        }
    }

    void salesFunctions(BookStoreSessionRemote storeBean) {
        sc = new Scanner(System.in, "ISO-8859-1");
        ArrayList<Book> checkedOutBooks = new ArrayList<Book>();
        int choice = 0;
        try {
            do {
                ui.showBookStoreUI();
                choice = Integer.parseInt(sc.nextLine());
                Book book;
                Publisher publisher;
                Author author;
                String bookId;
                String bookName;
                String authorId;
                String authorName;
                String publisherId;
                String publisherName;
                int price;
                int quantity;
                switch (choice) {
                    case 1:
                        // Print all books (using current session bean)
                        listAllBooks(storeBean);
                        break;
                    case 2:
                        //Find Book By Id
                        String ch = "";
                        System.out.println("Enter book Id");
                        bookId = sc.nextLine();
                        book = storeBean.getBookById(bookId);
                        System.out.println(book);
                        System.out.println("Add to cart?");
                        ch = sc.nextLine();
                        if (ch.equals("y") && book.getQuantity() > 0) {
                            System.out.println("How many?");
                            int numBooks = sc.nextInt();
                            sc.nextLine();
                            bookQty.add(numBooks);
                            checkedOutBooks.add(book);
                        } else {
                            System.out.println("Could not add book");
                        }
                        break;
                    case 3:
                        //List Author(s) of Book
                        System.out.println("Enter book Id");
                        bookId = sc.nextLine();
                        ArrayList<Author> authorsList = storeBean.getBookAuthors(bookId);
                        for (Author authorsOfBook : authorsList) {
                            System.out.println(authorsOfBook);
                        }
                        break;
                    case 4:
                        //List All checked out books
                        if (checkedOutBooks.isEmpty()) {
                            System.out.println("There is no book in the check out list!\n");
                        }
                        ui.header("ckBooks");
                        System.out.printf(format, "ID", "Name", "Publisher", "Price");
                        for (Book chkedOutBook : checkedOutBooks) {
                            System.out.printf(format, chkedOutBook.getBookId(), chkedOutBook.getBookName(), chkedOutBook.getPublisher(), new DecimalFormat("0,000 VND").format(chkedOutBook.getPrice()));
                        }
                        break;
                    case 5:
                        if (bookQty.isEmpty() && checkedOutBooks.isEmpty()) {
                            System.out.println("No books to proceed payment!");
                            return;
                        }
                        System.out.println("Books to be checked out");
                        int i = 0;
                        for (Book chkedOutBook : checkedOutBooks) {
                            int qty = bookQty.get(i);
                            System.out.printf(format, chkedOutBook.getBookId(), chkedOutBook.getBookName(), qty, new DecimalFormat("0,000 VND").format(chkedOutBook.getPrice()));
                        }
                        total(checkedOutBooks, bookQty, new Account("hieu178", "81dc9bdb52d04dc20036dbd8313ed055"), storeBean);
                        checkedOutBooks.removeAll(checkedOutBooks);
                        bookQty.removeAll(bookQty);
                        break;
                    default:
                        // Exit
                        break;
                }
            } while (choice < 12 && choice > 0);
        } catch (NumberFormatException e) {
            System.err.println("Please input an integer!");
        }
    }

    private void total(ArrayList<Book> checkedOutBooks, ArrayList<Integer> bookQty, Account account, BookStoreSessionRemote storeBean) {
        int total = 0;
        Receipt receipt = new Receipt();
        //        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();
        String receiptId = storeBean.autoID('R');
        receipt.setUsername(account);
        receipt.setDate(date);
        storeBean.addReceipt(receipt);
        total = storeBean.insertReceiptBook(receiptId, checkedOutBooks, bookQty);
        System.out.println("The total is:" + total);
        System.out.println("Customer's cash");
        int cash = sc.nextInt();
        sc.nextLine();
        if (payment(total, cash)) {
            printReceipt(checkedOutBooks, bookQty, date, account.getUsername(), total);
        }
    }

    private void showAllAuthors(BookStoreSessionRemote storeBean) {
        List<Author> authors = storeBean.getAuthors();
        if (authors.isEmpty()) {
            System.out.println("There isn't any author in the Database!\n");
        }
        ui.header("authors");
        for (Author author : authors) {
            System.out.println(author.getAuthorId() + "\t" + author.getAuthorName());
        }
        System.out.println();
    }

    private void listAllBooks(BookStoreSessionRemote storeBean) {
        List<Book> booksList = storeBean.getBooks();
        if (booksList.isEmpty()) {
            System.out.println("There is no book in the bookstore!\n");
        }
        ui.header("books");
        System.out.printf(format, "ID", "Name", "Publisher", "Price");
        for (Book book : booksList) {
            System.out.printf(format, book.getBookId(), book.getBookName(), book.getPublisher(), new DecimalFormat("0,000 VND").format(book.getPrice()));
        }
        System.out.println();
    }

    private void printReceipt(ArrayList<Book> checkedkOutBooks, ArrayList<Integer> bookQty, Date date, String username, int total) {
        int i = 0;
        ui.header("receipt");
        System.out.println(username + "\t" + dateFormat.format(date));
        System.out.println("-------------------------------------");
        System.out.printf(format, "ID", "Book Name", "Qty", "Price");
        for (Book book : checkedkOutBooks) {
            int qty = bookQty.get(i);
            System.out.printf(format, book.getBookId(), book.getBookName(), qty, new DecimalFormat("0,000 VND").format(book.getPrice()));
        }
        System.out.printf("%-105s %s%n", "Total:", new DecimalFormat("0,000 VND").format(total));
    }

    private boolean payment(int total, int cash) {
        int change = 0;
        change = total - cash;
        if (change == 0) {
            System.out.println("Thank you\nPrinting Bill");
        } else if (change < 0) {
            System.out.println("The change is: " + change + "\nPrinting Bill");
        }
        return true;
    }

    private void showAllPublishers(BookStoreSessionRemote storeBean) {
        List<Publisher> publishers = storeBean.getPublishers();
        if (publishers.isEmpty()) {
            System.out.println("There isn't any publisher in the Database!\n");
        }
        ui.header("publisher");
        for (Publisher publisher : publishers) {
            System.out.println(publisher.getPublisherId() + "\t" + publisher.getPublisherName());
        }
        System.out.println();
    }

}