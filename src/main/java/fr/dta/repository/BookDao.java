package fr.dta.repository;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.dta.entities.Book;

@Repository
@Transactional
public class BookDao {
	
	@PersistenceContext
	protected EntityManager em;
	
	public BookDao() {}; 
	
	//Add Book
	public void createBook(Book book) {
		em.persist(book);
	}
	
	public void updateBook(Book book) {
		em.merge(book);
	}
	
	public List<Book> getAll() {
		TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
		return query.getResultList();
	}
	
	public Book getById(Long id) {
		TypedQuery<Book> query = em.createQuery("select b from Book b where b.id = :id", Book.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	public void delete(Book book) {
		em.remove(getById(book.getId()));
	}

}
