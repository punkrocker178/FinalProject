/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.bean;

import edu.tdt.persistence.Account;
import edu.tdt.persistence.Role;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Steel
 */
@Stateful
public class SystemManagement implements SystemManagementRemote {

    @PersistenceContext(unitName = "FinalEJBPU")
    private EntityManager em;

    public SystemManagement() {

    }

    @Override
    public void insertAccount(String userName, String userPassword) {
        Account user = new Account(userName, hashPassword(userPassword));
        em.persist(user);
    }

    @Override
    public void insertRole(String roleName) {
        Role role = new Role(roleName);
        em.persist(role);
    }

    @Override
    public void insertAccountRole(String userName, String roleName) {
        Account user = em.find(Account.class, userName);
        Role role = em.find(Role.class, roleName);

        user.getRoleCollection().add(role);
        role.getAccountCollection().add(user);
    }

    @Override
    public ArrayList<String> searchRole(String rolename) {
        Role role = em.find(Role.class, rolename);

        if (role != null) {
            ArrayList<String> arrOutput = new ArrayList<String>();
            for (Account user : role.getAccountCollection()) {
                arrOutput.add(user.getUsername());
            }
            return arrOutput;
        }
        return null;
    }

    @Override
    public String getAccountPassword(String username) {
        Account user = em.find(Account.class, username);
        if (user != null) {
            return user.getPassword();
        }
        return "";
    }

    @Override
    public String hashPassword(String password) {
//        From Mkyong.com
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashedPassword = new StringBuilder();
            for (byte b : hashInBytes) {
                hashedPassword.append(String.format("%02x", b));
            }
            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean isAdmin(String username) {
        Account user = em.find(Account.class, username);
        for (Role role : user.getRoleCollection()) {
            if (role.getRoleName().equals("admin")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCashier(String username) {
        Account user = em.find(Account.class, username);
        for (Role role : user.getRoleCollection()) {
            if (role.getRoleName().equals("cashier")) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isWarehouse(String username) {
        Account user = em.find(Account.class, username);
        for (Role role : user.getRoleCollection()) {
            if (role.getRoleName().equals("warehouse")) {
                return true;
            }
        }
        return false;
    }

}
