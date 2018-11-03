/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.test;

import edu.tdt.bean.BookStoreSession;
import edu.tdt.bean.BookStoreSessionRemote;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Steel
 */
public class JNDI {
     private Properties props;
    private InitialContext ctx;

    public JNDI() {
        props = new Properties();

        try {
            props.load(new FileInputStream("jndi.properties"));
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        try {
            ctx = new InitialContext(props);
            //ctx.close();
        } catch (NamingException ex) {
            ex.getMessage();
        }

    }

    public String getJNDI() {
        String appName = "";
        String moduleName = "FinalEJB";
        String distinctName = "";
        String sessionBeanName = BookStoreSession.class.getSimpleName();
        String viewClassName = BookStoreSessionRemote.class.getName()+"?stateful";
        return "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" 
                + sessionBeanName + "!" + viewClassName;
    }
    
//    public String getJNDI2(){
//        String appName = "";
//        String moduleName = "SampleEJB3";
//        String distinctName = "";
//        String sessionBeanName = SystemManagement.class.getSimpleName();
//        String viewClassName = SystemManagementRemote.class.getName()+"?stateful";
//        return "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" 
//                + sessionBeanName + "!" + viewClassName;
//    }
    
    public InitialContext getInitialContex(){
        return ctx;
    }
}
