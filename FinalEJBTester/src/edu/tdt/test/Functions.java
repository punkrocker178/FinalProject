/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.test;

import edu.tdt.bean.BookStoreSessionRemote;
import edu.tdt.bean.SystemManagementRemote;
import edu.tdt.persistence.Account;
import edu.tdt.persistence.Author;
import edu.tdt.persistence.Book;
import edu.tdt.persistence.Publisher;
import edu.tdt.persistence.Receipt;
import edu.tdt.persistence.Role;
import edu.tdt.persistence.StockInput;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

    private SystemManagementRemote session;

    private Scanner sc;

    private Date date;

    public String format = "%1$-10s %2$-70.70s %3$-25s %4$s %n";

    public String formatBook = "%1$-10s %2$-70.70s %3$-25s %4$-20s %5$s %n";

    public String formatTotal = "%-105s %s%n";

    //Sách khách đem ra quầy thanh toán
    private ArrayList<Book> checkedOutBooks = new ArrayList<Book>();

    private ArrayList<Book> newBooks = new ArrayList<Book>();

    private ArrayList<String> authorIdList;

    private ArrayList<ArrayList> authorsOfNewBooks = new ArrayList();

    public DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private UI ui = new UI();

    public Functions() {

    }

    public Functions(BookStoreSessionRemote storeBean) {
        this.storeBean = storeBean;
    }

    void adminFunctions(SystemManagementRemote session, BookStoreSessionRemote storeBean) {
        sc = new Scanner(System.in);
        int choice = 0;
        try {
            do {
                ui.showAdminUI();
                choice = Integer.parseInt(sc.nextLine());
                Account user;
                Role role;
                String username, password, rolename;

                switch (choice) {
                    case 1:
                        //Add Account
                        System.out.println("Enter username");
                        username = sc.nextLine();
                        System.out.println("Enter password");
                        password = sc.nextLine();
                        System.out.print("Enter role name : ");
                        rolename = sc.nextLine();
                        session.insertAccount(username, password);
                        session.insertAccountRole(username, rolename);
                        System.out.println("Done!");
                        break;
                    case 2:
                        System.out.print("Enter role: ");
                        rolename = sc.nextLine();
                        session.insertRole(rolename);
                        System.out.println("Done!");
                        //Add Role
                        break;
                    case 3:
                        //Search account by role
                        ui.header("roles");
                        System.out.print("Enter choice: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        rolename = roleChoice(choice);
                        ui.header("accounts");
                        for (String s : session.searchRole(rolename)) {
                            System.out.println(s);
                        }
                        System.out.println("Done!");
                        break;
                    case 4:
                        //Insert role to existing account
                        System.out.println("Enter username");
                        username = sc.nextLine();
                        System.out.println("Enter role name");
                        rolename = sc.nextLine();
                        session.insertAccountRole(username, rolename);
                        break;
                    case 5:
                        //View Report
                        ui.reportUI();
                        System.out.println("Enter Choice:");
                        choice = sc.nextInt();
                        sc.nextLine();
                        getReport(choice, storeBean);
                        break;
                }
            } while (choice < 5 && choice > 0);
        } catch (NumberFormatException e) {
            System.err.println("Please input an integer!");
        }
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
//                        storeBean.addBook(book);
                        for (int i = 0; i < numOfAuthors; i++) {
                            authorIdList = new ArrayList();
                            authorId = storeBean.autoID('A');
                            System.out.println("Enter the author's name");
                            authorName = sc.nextLine();
                            author = new Author(authorId, authorName);
                            storeBean.addAuthor(author);
                            authorIdList.add(authorId);
//                            storeBean.insertBookAuthor(bookId, authorId);
                        }
                        newBooks.add(book);
                        authorsOfNewBooks.add(authorIdList);
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
//                        storeBean.addBook(book);
                        System.out.println("How many authors are there?");
                        numOfAuthors = sc.nextInt();
                        sc.nextLine();
                        for (int i = 0; i < numOfAuthors; i++) {
                            authorIdList = new ArrayList();
                            System.out.println("Enter author's ID ");
                            authorId = sc.nextLine();
                            authorIdList.add(authorId);
//                            storeBean.insertBookAuthor(bookId, authorId);
                        }
                        newBooks.add(book);
                        authorsOfNewBooks.add(authorIdList);
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
                    case 11:
                        //Stock input
                        System.out.println("Newly added books");
                        int i = 0;
                        for (Book nBook : newBooks) {
//                            int qty = bookQty.get(i);
                            System.out.printf(format, nBook.getBookId(),
                                    nBook.getBookName(),
                                    nBook.getQuantity(),
                                    new DecimalFormat("0,000 VND").format(nBook.getPrice()));
                        }
                        addBooksToDB(newBooks,
                                new Account("hieu178", "81dc9bdb52d04dc20036dbd8313ed055"), storeBean);
                        newBooks.removeAll(newBooks);
                        break;
                    case 12:
                        listAllBooks(storeBean);
                        break;
                    default:
                        // Exit
                        break;
                }
            } while (choice < 13 && choice > 0);
        } catch (NumberFormatException e) {
            System.err.println("Please input an integer!");
        }
    }

    void salesFunctions(BookStoreSessionRemote storeBean) {
        sc = new Scanner(System.in, "ISO-8859-1");
        int choice = 0;
        try {
            do {
                ui.showBookStoreUI();
                choice = Integer.parseInt(sc.nextLine());
                Book book;
                String bookId;
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
                            book.setQuantity(numBooks);
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
                        System.out.printf(format, "ID", "Name", "Qty", "Price");
                        for (Book chkedOutBook : checkedOutBooks) {
                            System.out.printf(format, chkedOutBook.getBookId(),
                                    chkedOutBook.getBookName(),
                                    chkedOutBook.getQuantity(),
                                    new DecimalFormat("0,000 VND").format(chkedOutBook.getPrice()));
                        }
                        break;
                    case 5:
                        //Proceed to checkout
                        if (checkedOutBooks.isEmpty()) {
                            System.out.println("No books to proceed payment!");
                            return;
                        }
                        System.out.println("\nBooks to be checked out:");
                        ui.doubleDash();
                        System.out.printf(format, "ID", "Book Name", "Qty", "Price");
                        int i = 0;
                        for (Book chkedOutBook : checkedOutBooks) {
                            System.out.printf(format, chkedOutBook.getBookId(),
                                    chkedOutBook.getBookName(),
                                    chkedOutBook.getQuantity(),
                                    new DecimalFormat("0,000 VND").format(chkedOutBook.getPrice()));
                        }
                        ui.doubleDash();
                        total(checkedOutBooks,
                                new Account("hieu178", "81dc9bdb52d04dc20036dbd8313ed055"), storeBean);
                        checkedOutBooks.removeAll(checkedOutBooks);
                        break;
                    case 6:
                        //View Report
                        ui.reportUI();
                        System.out.println("Enter Choice:");
                        choice = sc.nextInt();
                        sc.nextLine();
                        getReport(choice, storeBean);

                    default:
                        // Exit
                        break;
                }
            } while (choice < 12 && choice > 0);
        } catch (NumberFormatException e) {
            System.err.println("Please input an integer!");
        }
    }

    private void getReport(int choice, BookStoreSessionRemote storeBean) {
        sc = new Scanner(System.in);
        int total = 0;
        String strDateFrom, strDateTo;
        List<Receipt> receiptsList;
        List<StockInput> stockReceiptsList;
        switch (choice) {
            case 1:
                //7 days
                receiptsList = storeBean.viewReceipt(7);
                stockReceiptsList = storeBean.viewStockInput(7);
                System.out.println("Viewing Report This Week");
                viewReport(receiptsList, stockReceiptsList);
                break;
            case 2:
                //30 days
                receiptsList = storeBean.viewReceipt(30);
                stockReceiptsList = storeBean.viewStockInput(7);
                System.out.println("Viewing Report This Month");
                viewReport(receiptsList, stockReceiptsList);
                break;
            case 3:
                //Custom
                System.out.println("From date:");
                strDateFrom = sc.nextLine();
                System.out.println("To date:");
                strDateTo = sc.nextLine();
                receiptsList = storeBean.viewReceipt(strDateFrom, strDateTo);
                stockReceiptsList = storeBean.viewStockInput(strDateFrom, strDateTo);
                System.out.println("Viewing Report From : " + strDateFrom + " To : "
                        + strDateTo);
                viewReport(receiptsList, stockReceiptsList);
                break;
        }

    }

    private void viewReport(List<Receipt> receiptsList, List<StockInput> stockReceiptsList) {
        ui.doubleDash();

        List<List<Book>> receiptBook = new ArrayList();
        List<List<Book>> stockReceiptBook = new ArrayList();
        List<Book> booksFromReceipt = new ArrayList();
        List<Book> booksFromStock = new ArrayList();
        List<Book> sortedBooks = new ArrayList();
        List<Book> sortedBooks2 = new ArrayList();

        //Receipt
        System.out.printf(format, "ID", "Date", "Username", "Total");
        Collections.sort(receiptsList);
        for (Receipt receipt : receiptsList) {
            System.out.printf(format, receipt.getOrderId(), dateFormat.format(receipt.getDate()),
                    receipt.getUsername(),
                    new DecimalFormat("0,000 VND").format(receipt.getTotal()));

            booksFromReceipt = storeBean.getBooksFromReceipt(receipt);
            receiptBook.add(booksFromReceipt);
        }
//        System.out.printf(formatTotal, "Total Income:", new DecimalFormat("0,000 VND").format(totalIn));

        //Stock Receipt
        ui.doubleDash();
        Collections.sort(stockReceiptsList);
        for (StockInput receipt : stockReceiptsList) {
            System.out.printf(format, receipt.getInputReceiptId(),
                    dateFormat.format(receipt.getDate()),
                    receipt.getUsername(),
                    new DecimalFormat("0,000 VND").format(receipt.getPrice()));

            booksFromStock = storeBean.getBooksFromStockInput(receipt);
            stockReceiptBook.add(booksFromStock);
        }
//        System.out.printf(formatTotal, "Total Outcome:", new DecimalFormat("0,000 VND").format(totalOut));
        ui.doubleDash();

        for (List<Book> list : receiptBook) {
            booksFromReceipt = list;
            for (Book book : booksFromReceipt) {
                sortedBooks.add(book);
            }
        }

        for (List<Book> list : stockReceiptBook) {
            booksFromStock = list;
            for (Book book : booksFromStock) {
                sortedBooks2.add(book);
            }
        }

        printSortedBooks(sortedBooks, sortedBooks2);
    }

    private void printSortedBooks(List<Book> sortedBooks, List<Book> sortedBooks2) {
        int netIncome = 0, totalIn = 0, totalOut = 0;
        ui.doubleDash();
        System.out.println("Books bought:");
        System.out.printf(format, "ID", "Book Name", "Publisher", "Price");
        Collections.sort(sortedBooks2);
        for (Book book : sortedBooks2) {
            System.out.printf(format, book.getBookId(),
                    book.getBookName(),
                    book.getPublisher(),
                    new DecimalFormat("0,000 VND").format(book.getPrice()));
            totalOut += book.getPrice();
        }
        ui.doubleDash();

        ui.doubleDash();
        System.out.println("Books sold:");
        System.out.printf(format, "ID", "Book Name", "Publisher", "Price");
        Collections.sort(sortedBooks);
        for (Book book : sortedBooks) {
            System.out.printf(format, book.getBookId(),
                    book.getBookName(),
                    book.getPublisher(),
                    new DecimalFormat("0,000 VND").format(book.getPrice()));
            totalIn += book.getPrice();
        }
        netIncome = totalIn - totalOut;
        System.out.println("Income: " + new DecimalFormat("0,000 VND").format(totalIn) + "\n"
                + "Outcome:" + new DecimalFormat("0,000 VND").format(totalOut) + "\n"
                + "Net Income:" + new DecimalFormat("0,000 VND").format(netIncome));
        ui.doubleDash();
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

    private void listAllBooks(BookStoreSessionRemote storeBean) {
        List<Book> booksList = storeBean.getBooks();
        if (booksList.isEmpty()) {
            System.out.println("There is no book in the bookstore!\n");
        }
        Collections.sort(booksList);
        ui.header("books");
        System.out.printf(formatBook, "ID", "Name", "Publisher", "Quantity", "Price");
        for (Book book : booksList) {
            System.out.printf(formatBook,
                    book.getBookId(),
                    book.getBookName(),
                    book.getPublisher(),
                    book.getQuantity(),
                    new DecimalFormat("0,000 VND").format(book.getPrice()));
        }
        System.out.println();
    }

    private void printReceipt(ArrayList<Book> checkedkOutBooks, Date date, String username, int total) {
        int i = 0;
        ui.header("receipt");
        System.out.println(username + "\t" + dateFormat.format(date));
        System.out.println("-------------------------------------");
        System.out.printf(format, "ID", "Book Name", "Qty", "Price");
        Collections.sort(checkedkOutBooks);
        for (Book book : checkedkOutBooks) {
            System.out.printf(format, book.getBookId(),
                    book.getBookName(),
                    book.getQuantity(),
                    new DecimalFormat("0,000 VND").format(book.getPrice()));
        }
        System.out.printf(formatTotal, "Total:", new DecimalFormat("0,000 VND").format(total));
    }

    private void printStockInputReceipt(ArrayList<Book> newBooks, Date date, String name, int total) {
        ui.header("receipt");
        System.out.println(name + "\t" + dateFormat.format(date));
        System.out.println("-------------------------------------");
        System.out.printf(format, "ID", "Book Name", "Qty", "Price");
        for (Book book : newBooks) {
            System.out.printf(format, book.getBookId(),
                    book.getBookName(),
                    book.getQuantity(),
                    new DecimalFormat("0,000 VND").format(book.getPrice()));
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

    private void total(ArrayList<Book> checkedOutBooks, Account account, BookStoreSessionRemote storeBean) {
        int total = 0;
        Receipt receipt = new Receipt();
        //        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        date = new Date();
        String receiptId = storeBean.autoID('R');
        receipt.setUsername(account);
        receipt.setDate(date);
        receipt.setOrderId(receiptId);
        storeBean.addReceipt(receipt);
        total = storeBean.insertReceiptBook(receiptId, checkedOutBooks);
        System.out.println("The total is:" + total);
        System.out.println("Customer's cash");
        int cash = sc.nextInt();
        sc.nextLine();
        if (payment(total, cash)) {
            printReceipt(checkedOutBooks, date, account.getUsername(), total);
        }
    }

    private void addBooksToDB(ArrayList<Book> newBooks,
            Account account, BookStoreSessionRemote storeBean) {
        int[] bookBuyPrice = new int[newBooks.size()];
        for (int i = 0; i < newBooks.size(); i++) {
            bookBuyPrice[i] = newBooks.get(i).getPrice();
        }
        addNewBooks(newBooks);
        int total = 0;
        StockInput stockReceipt = new StockInput();
        date = new Date();
        String stockInId = storeBean.autoID('S');
        stockReceipt.setUsername(account);
        stockReceipt.setDate(date);
        stockReceipt.setInputReceiptId(stockInId);
        storeBean.addStockInputReceipt(stockReceipt);
        total = storeBean.insertStockReceiptBook(stockInId, newBooks, bookBuyPrice);
        printStockInputReceipt(newBooks, date, account.getUsername(), total);
    }

    private void addNewBooks(ArrayList<Book> newBooks) {
        int i = 0;
        for (Book book : newBooks) {
            storeBean.addBook(book);
            authorIdList = authorsOfNewBooks.get(i);
            for (int j = 0; j < authorIdList.size(); j++) {
                storeBean.insertBookAuthor(book.getBookId(), authorIdList.get(j));
            }
            i++;
        }
    }

    public String roleChoice(int choice) {
        switch (choice) {
            case 1:
                return "admin";
            case 2:
                return "cashier";
            case 3:
                return "warehouse";
        }
        return "";
    }
}
