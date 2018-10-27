package edu.tdt.persistence;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Author.class)
public abstract class Author_ {

	public static volatile SingularAttribute<Author, String> authorName;
	public static volatile SingularAttribute<Author, String> authorId;
	public static volatile CollectionAttribute<Author, Book> bookCollection;

}

