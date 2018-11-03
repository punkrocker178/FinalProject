/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.persistence;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Steel
 */
@Entity
@Table(name = "book", catalog = "BookStoreDB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b")
    , @NamedQuery(name = "Book.findByBookId", query = "SELECT b FROM Book b WHERE b.bookId = :bookId")
    , @NamedQuery(name = "Book.findByBookName", query = "SELECT b FROM Book b WHERE b.bookName = :bookName")
    , @NamedQuery(name = "Book.findByPrice", query = "SELECT b FROM Book b WHERE b.price = :price")})
public class Book implements Serializable {

    @JoinTable(name = "stock_input_book", joinColumns = {
        @JoinColumn(name = "book_id", referencedColumnName = "book_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "input_receipt_id", referencedColumnName = "input_receipt_id", nullable = false)})
    @ManyToMany
    private Collection<StockInput> stockInputCollection;

    @JoinTable(name = "book_receipt", joinColumns = {
        @JoinColumn(name = "book_id", referencedColumnName = "book_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)})
    @ManyToMany
    private Collection<Receipt> receiptCollection;

    @Column(name = "quantity")
    private Integer quantity;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "book_id", nullable = false, length = 2147483647)
    private String bookId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "book_name", nullable = false, length = 2147483647)
    private String bookName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price", nullable = false)
    private int price;
    @ManyToMany(mappedBy = "bookCollection")
    private Collection<Author> authorCollection;
    @JoinColumn(name = "publisher_id", referencedColumnName = "publisher_id", nullable = false)
    @ManyToOne(optional = false)
    private Publisher publisher;

    public Book() {
    }

    public Book(String bookId) {
        this.bookId = bookId;
    }

    public Book(String bookId, String bookName, int price, int quantity) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.price = price;
        this.quantity = quantity;
    }

    public Book(String bookName, int price, int quantity) {
        this.bookName = bookName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @XmlTransient
    public Collection<Author> getAuthorCollection() {
        return authorCollection;
    }

    public void setAuthorCollection(Collection<Author> authorCollection) {
        this.authorCollection = authorCollection;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookId != null ? bookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.bookId == null && other.bookId != null) || (this.bookId != null && !this.bookId.equals(other.bookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return bookId + "\t" + bookName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @XmlTransient
    public Collection<Receipt> getReceiptCollection() {
        return receiptCollection;
    }

    public void setReceiptCollection(Collection<Receipt> receiptCollection) {
        this.receiptCollection = receiptCollection;
    }

    @XmlTransient
    public Collection<StockInput> getStockInputCollection() {
        return stockInputCollection;
    }

    public void setStockInputCollection(Collection<StockInput> stockInputCollection) {
        this.stockInputCollection = stockInputCollection;
    }

}
