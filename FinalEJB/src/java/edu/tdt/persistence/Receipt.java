/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Steel
 */
@Entity
@Table(name = "receipt", catalog = "BookStoreDB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receipt.findAll", query = "SELECT r FROM Receipt r")
    , @NamedQuery(name = "Receipt.findByOrderId", query = "SELECT r FROM Receipt r WHERE r.orderId = :orderId")
    , @NamedQuery(name = "Receipt.findByDate", query = "SELECT r FROM Receipt r WHERE r.date = :date")
    , @NamedQuery(name = "Receipt.findByTotal", query = "SELECT r FROM Receipt r WHERE r.total = :total")})
public class Receipt implements Serializable,Comparable<Receipt> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "order_id", nullable = false, length = 2147483647)
    private String orderId;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total", nullable = false)
    private int total;
    @ManyToMany(mappedBy = "receiptCollection")
    private Collection<Book> bookCollection;
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    @ManyToOne(optional = false)
    private Account username;

    public Receipt() {
    }

    public Receipt(String orderId) {
        this.orderId = orderId;
    }

    public Receipt(String orderId, int total) {
        this.orderId = orderId;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @XmlTransient
    public Collection<Book> getBookCollection() {
        return bookCollection;
    }

    public void setBookCollection(Collection<Book> bookCollection) {
        this.bookCollection = bookCollection;
    }

    public Account getUsername() {
        return username;
    }

    public void setUsername(Account username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receipt)) {
            return false;
        }
        Receipt other = (Receipt) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.tdt.persistence.Receipt[ orderId=" + orderId + " ]";
    }
    
    public int compareTo(Receipt r){
        String id1 = this.getOrderId();
        String id2 = r.getOrderId();
        String[] i1 = id1.split("\\D");
        String[] i2 = id2.split("\\D");
        if(Integer.parseInt(i1[1])>Integer.parseInt(i2[1])){
            return 1;
        }else if(Integer.parseInt(i1[1])==Integer.parseInt(i2[1])){
            return 0;
        }
        return -1;
    }
    
}
