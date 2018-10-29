/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tdt.persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Steel
 */
@Stateful
public class BookStoreSession implements BookStoreSessionRemote {

    @PersistenceContext(unitName = "FinalEJBPU")
    private EntityManager entityManager;

    public BookStoreSession() {

    }

    @Override
    public void addBook(Book book) {
        entityManager.persist(book);
    }

    public void addPublisher(Publisher publisher) {
        entityManager.persist(publisher);
    }

    @Override
    public void addAuthor(Author author) {
        entityManager.persist(author);
    }

    public void insertBookAuthor(String bookId, String authorId) {
        Book book = entityManager.find(Book.class, bookId);
        Author author = entityManager.find(Author.class, authorId);
        book.getAuthorCollection().add(author);
        author.getBookCollection().add(book);
    }

    @Override
    public ArrayList<Author> getBookAuthors(String bookId) {
        Book book = entityManager.find(Book.class, bookId);
        ArrayList<Author> authorsList = new ArrayList();
        if (book != null) {
            for (Author author : book.getAuthorCollection()) {
                authorsList.add(author);
            }
        }
        return authorsList;
    }

    @Override
    public Book getBookById(String Id) {
        return (Book) entityManager.createNamedQuery("Book.findByBookId")
                .setParameter("bookId", Id).getSingleResult();
    }

    @Override
    public List<Book> getBooks() {
        return entityManager.createNamedQuery("Book.findAll").getResultList();
    }

    public List<Author> getAuthors() {
        return entityManager.createNamedQuery("Author.findAll").getResultList();
    }
    
    public Publisher getPublisherById(String Id){
        return (Publisher) entityManager.createNamedQuery("Publisher.findByPublisherId")
                .setParameter("publisherId",Id).getSingleResult();
    }

    public List<Publisher> getPublishers() {
        return entityManager.createNamedQuery("Publisher.findAll").getResultList();
    }

    public void removeBooks(String bookId) {
        Book book = entityManager.find(Book.class, bookId);
        //Xóa danh sách Book_Author rồi mới xóa xách khỏi DB
        List<Author> authorsList = (List<Author>) book.getAuthorCollection();
        for (Author author : authorsList) {
            List<Book> booksList = (List<Book>) author.getBookCollection();
            for (Book bookToBeDeleted : booksList) {
                if (bookToBeDeleted.equals(book)) {
                    booksList.remove(bookToBeDeleted);
                    break;
                }
            }
        }
        entityManager.remove(book);
    }

    public void updateBook(String bookId, String[] input) {
        Book book = entityManager.find(Book.class, bookId);
        if (!input[0].equals("")) {
            book.setBookName(input[0]);
        }
        if (!input[1].equals("")) {
            book.getPublisher().setPublisherId(input[1]);
        }
        if (!input[2].equals("")) {
            book.setPrice(Integer.parseInt(input[2]));
        }
    }

//    public String getAllBooksNames() {
//        String s = "";
//        List<Book> booksList = getBooks();
//        for (int i = 0; i < booksList.size(); i++) {
//            s = s+(i + 1) + "\t" + booksList.get(i)+"\n";
//        }
//        return s;
//    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
