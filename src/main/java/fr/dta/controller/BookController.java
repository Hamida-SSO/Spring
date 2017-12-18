package fr.dta.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.dta.entities.Book;
import fr.dta.exceptions.NotFoundException;
import fr.dta.service.Library;

//Le @Controller -> besoin du @ResponseBody sur les @RequestMapping

@RestController
@RequestMapping("/books")
public class BookController {
	
	private Library library = Library.getInstance();

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "Hi everybody";
	}

	@RequestMapping(value = "/{year}/{month}/{day}", method = RequestMethod.GET)
	public String dateNom(@PathVariable int year, @PathVariable int month, @PathVariable int day) {
		LocalDate date = LocalDate.of(year, month, day);

		return "Liste des livres du " + date.getDayOfMonth() + " " + date.getMonth() + " " + date.getYear();
	}

	@RequestMapping(value = "/book", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Book getbook() {
		Book book = new Book();
		book.setId(1L);
		book.setTitle("Titre");
		book.setAuthor("Auteur");
		book.setNbPages(10);
		book.setPublicationDate(new Date());

		return book;
	}

//	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public String create(@RequestBody Book book) {
//		return library.create(book);
//	}

//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//	public void delete(@PathVariable int id) {
//		library.removeBook(id);
//	}

//	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public String update(@RequestBody Book book) {
//		return library.update(book);
//	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.OK)
	public void deleteBooks() {
		library.removeBooks();
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public List<Book> read() {
		return library.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public Book readBook(@PathVariable Long id) {
		Book result = library.getBook(id);
		if(result == null) {
			throw new NotFoundException();
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value=HttpStatus.CREATED)
	public String create(@RequestBody @Valid Book resource, BindingResult res) throws Exception {
		System.out.println("Error: "+res.getErrorCount());
		if(!res.hasErrors()) {
			return Library.create(resource);
		}else {
			throw new Exception("Validation error");
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable @Valid int id,  BindingResult res) throws Exception {
		if(!res.hasErrors()) {
			library.removeBook(id);
		}else {
			throw new Exception("Validation error");
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value=HttpStatus.CREATED)
	public String update(@RequestBody @Valid Book book, BindingResult res) throws Exception {
		if(!res.hasErrors()) {
			return library.update(book);
		}else {
			throw new Exception("Validation error");
		}
	}
}
