/*
 * In MVC paradigm this class would be the Model.
 * 
 */

package bookshop.model.book;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn; 

@Entity
@Table(name="BOOK")
public class Book implements Serializable {
	//a universal version identifier for a Serializable class 
	//Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object
    private static final long serialVersionUID = 2L;
    
    // Persistent Fields:
    @Id 
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;
  
    //By default, all fields of the class are mapped to table columns with the same name. 
    //Use the @Column annotation when the field and column names differ
    @Column(name = "isbn", length = 15) 
    @NotNull
    @Size(max = 15)
    private String isbn;
    
    // TODO: rename to nbOfPages
    @Column(name = "nb_of_pages")
    @Min(1)
    private Integer nbOfPages;
    
    @NotNull
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Author.class) 
    @JoinTable(name="AUTHOR_BOOK", joinColumns=@JoinColumn(name="book_id"), inverseJoinColumns=@JoinColumn(name="author_id"))
    private Set<Author> authors = new HashSet<Author>();
     
    // Constructors:
    public Book() {
    }
 
    public Book(String isbn, String title, Integer nbOfPages, String description, Set<Author> authors) {
    	this.isbn = isbn;
        this.title = title;
        this.nbOfPages = nbOfPages;
        this.description = description;
        this.authors = authors;
    }
 
    // String Representation:
    @Override
    public String toString() {
        return "id: "+ id + "; "+ title + " authors: "+ authors;
    }
    
    public String getIsbn() {
		return isbn;
    }
    
    public String getTitle() {
    	return title;
    }
    public Integer getNbOfPages() {
		return nbOfPages;
	}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
		this.title = title;
	}
    public String getDescription() {
		return description;
	}
    public void setDescription(String description) {
		this.description = description;
	}
    public void setNbOfPages(Integer nbOfPages) {
		this.nbOfPages = nbOfPages;
	}
    public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

    public Set<Author> getAuthors() {
        return this.authors;
    }

    public void setAuthors(final Set<Author> authors) {
        this.authors = authors;
    }
}