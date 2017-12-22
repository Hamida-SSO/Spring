package fr.dta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.dta.entities.Book;
import fr.dta.repository.BookDao;

@Service
@Transactional
public class BookService {

	
	@Autowired BookDao bookDao;
	
	public Book getBook(Long id) {
		return bookDao.getById(id);
	}

	public List<Book> getAll() {
		return bookDao.getAll();
	}

	// Teste si l'Id n'est pas existant puis créer
	public void create(Book book) {
		bookDao.createBook(book); 
	}

	// Teste le book par rapport à l'ID
	public void update(Book book) {
		bookDao.updateBook(book);
	}
	
	public void removeBook(Long id) {
		bookDao.delete(id);
	}
	
}
