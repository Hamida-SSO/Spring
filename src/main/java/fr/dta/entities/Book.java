package fr.dta.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import fr.dta.validator.Isbn;

@Entity
@SequenceGenerator(name = "seq_book", sequenceName = "seq_book", initialValue = 1, allocationSize = 1)
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_book")
	private Long id;

	@Length(max = 100)
	@Column(name ="title")
	private String title;

	@NotEmpty
	@Column(name ="author")
	private String author;
	
	@Column(name ="nb_pages")
	private int nbPages;
	
	@Column(name ="date_de_publication")
	@Temporal(TemporalType.DATE)
	private Date publicationDate;

	@Isbn
	@Column(name ="isbn")
	private String isbn;

	public Book() {
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public int getNbPages() {
		return nbPages;
	}

	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String toString() {
		return "ID : " + this.id + " | Titre : " + this.title + " | Auteur : " + this.author + " | nombre de pages : "
				+ this.nbPages + " | Date de publication : " + this.publicationDate + "ISBN : " + this.isbn + "\n";
	}

}
