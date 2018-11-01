package edu.tdt.persistence;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ {

	public static volatile SingularAttribute<Book, Integer> quantity;
	public static volatile CollectionAttribute<Book, Author> authorCollection;
	public static volatile SingularAttribute<Book, Integer> price;
	public static volatile SingularAttribute<Book, Publisher> publisher;
	public static volatile CollectionAttribute<Book, Receipt> receiptCollection;
	public static volatile SingularAttribute<Book, String> bookName;
	public static volatile SingularAttribute<Book, String> bookId;

}

