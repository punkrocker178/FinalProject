/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.test;

/**
 * Hiện lựa chọn trong console
 *
 * @author Steel
 */
public class UI {

    public UI() {

    }

    public void header(String choice) {
        switch (choice) {
            case "publisher":
                System.out.println("\n=================================");
                System.out.println("Listing All Publishers In Database");
                System.out.println("=================================");
                break;
            case "authors":
                System.out.println("\n=================================");
                System.out.println("Listing All Authors In Database");
                System.out.println("=================================");
                break;
            case "books":
                System.out.println("\n=================================");
                System.out.println("Listing All Books In Bookstore");
                System.out.println("=================================");
                break;
            case "ckBooks":
                System.out.println("\n=================================");
                System.out.println("Listing All Books In Check Out List");
                System.out.println("=================================");
                break;
            case "receipt":
                System.out.println("\n===================================");
                System.out.println("\tReceipt");
                System.out.println("===================================");
                break;
            case "accounts":
                System.out.println("\n===================================");
                System.out.println("Listing All Accounts By Role");
                System.out.println("===================================");
                break;
            case "roles":
                System.out.println("\n===================================");
                System.out.println("1. Admin");
                System.out.println("2. Cashier");
                System.out.println("3. Warehouse Keeper");
                System.out.println("===================================");
                break;
        }
    }

    public void showWarehouseUI() {
        System.out.println("\n===========================");
        System.out.println("Welcome to the Bookstore");
        System.out.println("===========================");
        System.out.print("Options: \n"
                + "1. Add Book With New Author & Publisher \n"
                + "2. Add Book With Existing Author & Publisher \n"
                + "3. Add Author\n"
                + "4. Add Publisher\n"
                + "6. List Book's Author(s)\n"
                + "7. Find Book By Id \n"
                + "8. Remove Book By Id\n"
                + "9. Assign Author To Book \n"
                + "10. Edit Book's information \n"
                + "11. Add New Books To Database\n"
                + "12. List All Books\n"
                + "Enter Choice: ");
    }

    public void showAdminUI() {
        System.out.println("\n===========================");
        System.out.println("Welcome to the Bookstore");
        System.out.println("===========================");
        System.out.print("Options: \n"
                + "1. Add Account \n"
                + "2. Add Role \n"
                + "3. Search Accounts by Role\n"
                + "4. Insert Account to existing Role\n"
                + "5. View Report \n"
                + "Enter Choice: ");
    }

    public void showBookStoreUI() {
        System.out.println("\n===========================");
        System.out.println("Welcome to the Bookstore");
        System.out.println("===========================");
        System.out.print("Options: \n"
                + "1. List All Books (Current Session) \n"
                + "2. Find Book By Id \n"
                + "3. List Book's Author(s)\n"
                + "4. List All Checked Out Book(s) \n"
                + "5. Checkout \n"
                + "6. View Report \n"
                + "Enter Choice: ");
    }

    public void reportUI() {
        doubleDash();
        System.out.println("View Reports By:");
        System.out.println("1. This Week");
        System.out.println("2. This Month");
        System.out.println("3. Custom Time Range");
        doubleDash();
    }

    public void doubleDash() {
        System.out.println("=================================");
    }
}
