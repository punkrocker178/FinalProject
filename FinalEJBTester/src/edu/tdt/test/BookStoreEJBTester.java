/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.test;

import edu.tdt.bean.BookStoreSessionRemote;
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
        jndi = new JNDI();
    }

    public void testEntityEJB() {
        try {
            // Scanner definition
            sc = new Scanner(System.in);

            // Lookup the LibrarySessionBeanRemote
            String jndiProperties = jndi.getJNDI();
            BookStoreSessionRemote storeBean = (BookStoreSessionRemote) jndi.getInitialContex().lookup(jndiProperties);
            
            Functions function = new Functions(storeBean);
//            function.warehouseFunctions(storeBean);
            function.salesFunctions(storeBean);

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
//                showGuestUI();
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
