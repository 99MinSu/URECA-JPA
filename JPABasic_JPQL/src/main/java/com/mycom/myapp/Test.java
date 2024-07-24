package com.mycom.myapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

// JPQL
// select no commit
// insert, update, delete commit 
// JPQL은 insert (X), update (O), delete (O) // ( 여기서 다루지 않는다. )
public class Test {

    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "update"); // create (항상 새로 만든다 있으면 drop 후 만듬), update( 없으면 만들고 create, 있으면 변경되어야 하면 변경 alter)
        EntityManagerFactory emf = 
                new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                        new MyPersistenceUnitInfo(), props
                );
        EntityManager em = emf.createEntityManager();

        try {
        	// #1 normal Query (단순 Query)
//        	{
//        		String jpql = "select b from Book b"; // table 이 아닌 entity 를 대상
//            	Query query = em.createQuery(jpql);
//            	List<Book> bookList= query.getResultList();
//            	
//            	for (Book book : bookList) {
//    				System.out.println(book);
//    			}
//        	}
        	
        	// #2 TypedQuery (단순 Query) (권장)
//        	{
//            	String jpql = "select b from Book b"; // table 이 아닌 entity 를 대상
//            	TypedQuery<Book> query = em.createQuery(jpql, Book.class);
//            	List<Book> bookList= query.getResultList();
//            	
//            	for (Book book : bookList) {
//    				System.out.println(book);
//    			}
//        	}
        	
        	// #3 Parameter : positional parameter
//        	{
//            	String jpql = "select b from Book b where price > ?1"; // ? + 순서번호
//            	TypedQuery<Book> query = em.createQuery(jpql, Book.class);
//            	query.setParameter(1, 15000);
//            	List<Book> bookList= query.getResultList();
//            	
//            	for (Book book : bookList) {
//    				System.out.println(book);
//    			}
//        	}
        	
        	// #4 Parameter : named parameter
//        	{
//            	String jpql = "select b from Book b where price > :price"; // ? + 순서번호
//            	TypedQuery<Book> query = em.createQuery(jpql, Book.class);
//            	query.setParameter("price", 15000);
//            	List<Book> bookList= query.getResultList();
//            	
//            	for (Book book : bookList) {
//    				System.out.println(book);
//    			}
//        	}
        	
        	// #5 Parameter : positional parameter, named parameter ( 별로... )
//        	{
//            	String jpql = "select b from Book b where b.price > :price and b.price < ?1"; // ? + 순서번호
//            	TypedQuery<Book> query = em.createQuery(jpql, Book.class);
//            	query.setParameter("price", 15000);
//            	query.setParameter(1, 30000);
//            	List<Book> bookList= query.getResultList();
//            	
//            	for (Book book : bookList) {
//    				System.out.println(book);
//    			}
//        	}
        	
        	// #6 like
        	// bookname
        	{
        		String searchWord = "축구";
        		String searchPattern = "%" + searchWord + "%";
            	String jpql = "select b from Book b where b.bookname like : searchPattern ";
            	TypedQuery<Book> query = em.createQuery(jpql, Book.class);
            	query.setParameter("searchPattern", searchPattern);
            	List<Book> bookList= query.getResultList();
            	
            	for (Book book : bookList) {
    				System.out.println(book);
    			}
        	}

        } finally {
            em.close();
        }
    }
}














