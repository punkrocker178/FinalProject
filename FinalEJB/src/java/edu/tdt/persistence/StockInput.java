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
@Table(name = "stock_input", catalog = "BookStoreDB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockInput.findAll", query = "SELECT s FROM StockInput s")
    , @NamedQuery(name = "StockInput.findByInputReceiptId", query = "SELECT s FROM StockInput s WHERE s.inputReceiptId = :inputReceiptId")
    , @NamedQuery(name = "StockInput.findByDate", query = "SELECT s FROM StockInput s WHERE s.date = :date")
    , @NamedQuery(name = "StockInput.findByPrice", query = "SELECT s FROM StockInput s WHERE s.price = :price")})
public class StockInput implements Serializable,Comparable<StockInput> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "input_receipt_id", nullable = false, length = 2147483647)
    private String inputReceiptId;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price", nullable = false)
    private int price;
    @ManyToMany(mappedBy = "stockInputCollection")
    private Collection<Book> bookCollection;
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    @ManyToOne(optional = false)
    private Account username;

    public StockInput() {
    }

    public StockInput(String inputReceiptId) {
        this.inputReceiptId = inputReceiptId;
    }

    public StockInput(String inputReceiptId, int price) {
        this.inputReceiptId = inputReceiptId;
        this.price = price;
    }

    public String getInputReceiptId() {
        return inputReceiptId;
    }

    public void setInputReceiptId(String inputReceiptId) {
        this.inputReceiptId = inputReceiptId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
        hash += (inputReceiptId != null ? inputReceiptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockInput)) {
            return false;
        }
        StockInput other = (StockInput) object;
        if ((this.inputReceiptId == null && other.inputReceiptId != null) || (this.inputReceiptId != null && !this.inputReceiptId.equals(other.inputReceiptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.tdt.persistence.StockInput[ inputReceiptId=" + inputReceiptId + " ]";
    }
    
    public int compareTo(StockInput s){
        String id1 = this.getInputReceiptId();
        String id2 = s.getInputReceiptId();
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
