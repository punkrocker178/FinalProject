package edu.tdt.persistence;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Account.class)
public abstract class Account_ {

	public static volatile SingularAttribute<Account, String> password;
	public static volatile CollectionAttribute<Account, Role> roleCollection;
	public static volatile CollectionAttribute<Account, StockInput> stockInputCollection;
	public static volatile CollectionAttribute<Account, Receipt> receiptCollection;
	public static volatile SingularAttribute<Account, String> username;

}

