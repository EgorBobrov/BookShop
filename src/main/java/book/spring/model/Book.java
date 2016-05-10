/*
 * In MVC paradigm this class would be the Model.
 * 
 */

package book.spring.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import book.Genre;

@Entity
@Table(name="BOOK")
/*@NamedQueries({
    @NamedQuery(name = Book.SEARCH, query = "SELECT b FROM Book b LEFT JOIN FETCH b.authors WHERE UPPER(b.title) LIKE :keyword OR UPPER(b.description) LIKE :keyword ORDER BY b.title")

})
*/
public class Book implements Serializable {
	//a universal version identifier for a Serializable class 
	//Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object
    private static final long serialVersionUID = 2L;
    //public static final String SEARCH = "Book.search";
    
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
    
    // now we are able to directly access the Genre for a given Book
  //TODO: fill the functionality after making the db work
/*    @ManyToOne(targetEntity = Book.class)
    private Set<Genre> category;
*/    
    /*
     * @ElementCollection means that the collection is not a collection of entities, but a collection of simple types 
     * (Strings, etc.) or a collection of embeddable elements (class annotated with @Embeddable).
     * It also means that the elements are completely owned by the containing entities: 
     * they're modified when the entity is modified, deleted when the entity is deleted, etc. 
     * They can't have their own lifecycle.
     */
    // @OneToMany - has the same meaning, but is used for entities
    //TODO: fill the functionality after making the db work
/*    @ElementCollection 
    private Set<String> authors;
*/    
    // TODO: rename to nbOfPages
    @Column(name = "nb_of_pages")
    @Min(1)
    private Integer nbOfPages;
    
    @NotNull
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
 
    // Constructors:
    public Book() {
    }
 
    public Book(String isbn, String title, Integer nbOfPages) {
    	this.isbn = isbn;
        this.title = title;
        this.nbOfPages = nbOfPages;
        //authors = new HashSet<String>();
        //this.authors.addAll(authorsSet);
    }
 
    // String Representation:
    @Override
    public String toString() {
        return "id: "+ id + "; "+ title;
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
    // TODO: something is wrong with this method, returns null when used in jsp
/*    public Set<String> getAuthors() {
		return authors;
	}
*/}