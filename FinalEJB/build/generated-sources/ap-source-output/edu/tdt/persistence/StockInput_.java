package edu.tdt.persistence;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StockInput.class)
public abstract class StockInput_ {

	public static volatile SingularAttribute<StockInput, Date> date;
	public static volatile SingularAttribute<StockInput, String> inputReceiptId;
	public static volatile SingularAttribute<StockInput, Integer> price;
	public static volatile CollectionAttribute<StockInput, Book> bookCollection;
	public static volatile SingularAttribute<StockInput, Account> username;

}

