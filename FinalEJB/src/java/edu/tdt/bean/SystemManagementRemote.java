/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.bean;

import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author Steel
 */
@Remote
public interface SystemManagementRemote {

    void insertAccount(String userName, String userPassword);

    void insertRole(String roleName);

    void insertAccountRole(String userName, String roleName);

    ArrayList<String> searchRole(String rolename);

    String getAccountPassword(String username);
    
    boolean isAdmin(String username);
    
    boolean isCashier(String username);
    
    boolean isWarehouse(String username);
    
    String hashPassword(String password);

}
