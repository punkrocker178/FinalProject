/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.test;

import edu.tdt.bean.BookStoreSessionRemote;
import edu.tdt.bean.SystemManagementRemote;
import java.io.Console;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 * Main class của client
 *
 * @author Steel
 */
public class BookStoreEJBTester {

    Scanner sc;
    private JNDI jndi;

    public BookStoreEJBTester() {

    }

    public void testEntityEJB() {
        try {
            // Scanner definition
            String jndiPath;

            sc = new Scanner(System.in);

            System.out.println("What type of terminal you are using?:\n"
                    + "1. NetBeans IDE \n"
                    + "2. Windows CMD");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                //Running from NetBeans IDE
                jndiPath = "jndi.properties";
                jndi = new JNDI(jndiPath);
            } else {
                //Running from CMD
                jndiPath = "../jndi.properties";
                jndi = new JNDI(jndiPath);
            }

            // Lookup the LibrarySessionBeanRemote
            String jndiProperties = jndi.getJNDI();
            BookStoreSessionRemote storeBean = (BookStoreSessionRemote) jndi.getInitialContex().lookup(jndiProperties);

            Functions function = new Functions(storeBean);

            String jndiProperties2 = jndi.getJNDI2();
            SystemManagementRemote session = (SystemManagementRemote) jndi.getInitialContex().lookup(jndiProperties2);

//             Log-in 
            String username, password = "";
            System.out.println("Log-in to Book Store System Management");
            System.out.print("Username: ");
            username = sc.nextLine();

            if (choice == 1) {
                /*--THIS LOGIN IS FOR NETBEANS IDE--*/
                System.out.println("Password:");
                password = sc.nextLine();
            } else {
                /*--THIS LOGIN IS FOR CMD--*/
                Console console = System.console();
                if (console == null) {
                    System.out.println("Couldn't get Console instance");
                    System.exit(0);
                }
                char passwordArray[] = console.readPassword("Enter your password: ");
                password = new String(passwordArray);
            }

            if (!session.hashPassword(password).equals(session.getAccountPassword(username))) {
                System.err.println("Wrong username/password!");
                return;
            }
//            Check User role
            if (session.isAdmin(username)) {
                function.adminFunctions(session, storeBean);
            } else if (session.isCashier(username)) {
                function.salesFunctions(storeBean);
            } else if (session.isWarehouse(username)) {
                function.warehouseFunctions(storeBean);
            }
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
