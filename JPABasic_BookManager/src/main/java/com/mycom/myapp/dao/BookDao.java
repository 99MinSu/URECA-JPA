package com.mycom.myapp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.dto.BookDto;
import com.mycom.myapp.entity.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class BookDao {
	
	EntityManager em;
	
	public BookDao() {
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "update"); // create (항상 새로 만든다 있으면 drop 후 만듬), update( 없으면 만들고 create, 있으면 변경되어야 하면 변경 alter)
        // hbm2ddl <= Hibernate Mapping to(2) DDL
        EntityManagerFactory emf = 
                new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                        new MyPersistenceUnitInfo(), props
                );
        em = emf.createEntityManager();
	}
	
	public List<BookDto> listBook(){
		TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
		// entity 의 list 를 dto 의 list 로 변환
		
		List<BookDto> bookList = query.getResultList().stream().map( book -> new BookDto(book.getBookid(), book.getBookname(), book.getPublisher(), book.getPrice()))
			.collect(Collectors.toList());
		return bookList;
	}
	
	public List<BookDto> listBookSearch(String searchWord){
		String searchPattern = "%" + searchWord + "%";
		TypedQuery<Book> query = em.createQuery("select b from Book b where b.bookname like :searchPattern", Book.class);
		query.setParameter("searchPattern", searchPattern);
		// entity 의 list 를 dto 의 list 로 변환
		
		List<BookDto> bookList = query.getResultList().stream().map( book -> new BookDto(book.getBookid(), book.getBookname(), book.getPublisher(), book.getPrice()))
			.collect(Collectors.toList());
		return bookList;
	}
	
	public BookDto detailBook(int bookid) {
		Book book = em.find(Book.class, bookid);
		return new BookDto(book.getBookid(), book.getBookname(), book.getPublisher(), book.getPrice());
	}
	
	public void insertBook(BookDto bookDto) {
		em.getTransaction().begin();
		Book book = new Book();
		
		book.setBookid(bookDto.getBookId());
		book.setBookname(bookDto.getBookName());
		book.setPublisher(bookDto.getPublisher());
		book.setPrice(bookDto.getPrice());
		
		em.persist(book);
		em.getTransaction().commit();
	}
	
	public void updateBook(BookDto bookDto) {
		em.getTransaction().begin();
		Book book = em.find(Book.class, bookDto.getBookId());
		
		book.setBookname(bookDto.getBookName());
		book.setPublisher(bookDto.getPublisher());
		book.setPrice(bookDto.getPrice());
		
		em.getTransaction().commit();
	}
	
	public void deleteBook(int bookid) {
		em.getTransaction().begin();
		Book book = em.find(Book.class, bookid);
		em.remove(book);
		
		em.getTransaction().commit();
	}
}
