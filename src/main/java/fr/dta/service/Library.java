package fr.dta.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.dta.entities.Book;

public class Library {

	public static Library instance = null;
	private static ArrayList<Book> list = new ArrayList<Book>();

	public static Library getInstance() {
		if (instance != null)
			return instance;
		else
			instance = new Library();
		return instance;
	}

	public Book getBook(Long id) {
		for (Book b : list) {
			if (b.getId() == id) {
				return b;
			}
		}
		return null;
	}

	public List<Book> getAll() {
		return list;
	}

	// Teste si l'Id n'est pas existant puis créer
	public static String create(Book book) {

		for (Book b : list) {
			if (b.getId() == book.getId()) {
				return "Book déjà existant";
			}
		}
		list.add(book);
		return "Book ajouté";
	}

	// Teste le book par rapport à l'ID
	public String update(Book book) {

		for (Book b : list) {
			if (b.getId() == book.getId()) {
				list.remove(b);
				list.add(book);
				return "Book mise à jour";
			}
		}
		return "Book non trouvé";
	}
	
	public void removeBook(int id) {
		for (Book b : list) {
			if (b.getId() == id) {
				list.remove(b);
			}
		}
		System.out.println("je suis là");
	}
	
	public void removeBooks() {
		for (Book b : list) {
			list.remove(b);	
		}
	}

	private Library() {
		Book b = new Book();
		b.setId(1L);
		b.setTitle("Title1");
		b.setAuthor("Author1");
		b.setNbPages(10);
		b.setPublicationDate(new Date());
		b.setIsbn("1024623558");

		list.add(b);

		b = new Book();
		b.setId(2L);
		b.setTitle("Title2");
		b.setAuthor("Author2");
		b.setNbPages(20);
		b.setPublicationDate(new Date());
		b.setIsbn("1024625646");
		
		list.add(b);

		b = new Book();
		b.setId(3L);
		b.setTitle("Title3");
		b.setAuthor("Author3");
		b.setNbPages(30);
		b.setPublicationDate(new Date());
		b.setIsbn("6284623558");
		
		list.add(b);
	}

	// public ArrayList<Book> search(String criteria){
	// ArrayList<Book> result = new ArrayList<Book>();
	// for(Book b: list)if(b.matches(criteria.toLowerCase()))result.add(b);
	// return result;
	// }

	// public Book getById(int id) {
	// for (Book b : list)
	// if (b.getId() == id)
	// return b;
	// return null;
	// }

	// public boolean contains(int id) {
	// return getById(id) != null;
	// }

	// Teste si l'ID book existe et rajoute l'ID dans la liste des book
	// public boolean addBook(Book b) {
	// if (list.contains(b.getId()))
	// return false;
	// list.add(b);
	// return true;
	// }

}
