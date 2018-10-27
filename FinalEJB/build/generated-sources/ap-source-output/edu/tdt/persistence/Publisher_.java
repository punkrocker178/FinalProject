package edu.tdt.persistence;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Publisher.class)
public abstract class Publisher_ {

	public static volatile SingularAttribute<Publisher, String> publisherId;
	public static volatile SingularAttribute<Publisher, String> publisherName;
	public static volatile CollectionAttribute<Publisher, Book> bookCollection;

}

