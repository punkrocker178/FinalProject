package edu.tdt.persistence;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Receipt.class)
public abstract class Receipt_ {

	public static volatile SingularAttribute<Receipt, Date> date;
	public static volatile SingularAttribute<Receipt, Integer> total;
	public static volatile SingularAttribute<Receipt, String> orderId;
	public static volatile CollectionAttribute<Receipt, Book> bookCollection;
	public static volatile SingularAttribute<Receipt, Account> username;

}

