/*
 * In MVC paradigm this class would be the Model.
 * 
 */

package book;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
 
@Entity
public class Book implements Serializable {
	//a universal version identifier for a Serializable class 
	//Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object
    private static final long serialVersionUID = 2L;
    // Persistent Fields:
/*    @Id @GeneratedValue
    Long id;
*/    
    //By default, all fields of the class are mapped to table columns with the same name. 
    //Use the @Column annotation when the field and column names differ
    @Column(name = "isbn", length = 15) 
    @NotNull
    @Size(max = 15)
    @Id 
    private String isbn;
    
    // now we are able to directly access the Genre for a given Book
    @ManyToOne
    private Set<Genre> category;
    
    /*
     * @ElementCollection means that the collection is not a collection of entities, but a collection of simple types 
     * (Strings, etc.) or a collection of embeddable elements (class annotated with @Embeddable).
     * It also means that the elements are completely owned by the containing entities: 
     * they're modified when the entity is modified, deleted when the entity is deleted, etc. 
     * They can't have their own lifecycle.
     */
    // @OneToMany - has the same meaning, but is used for entities
    @ElementCollection 
    private Set<String> authors;
    
    @Column(name = "nb_of_pages")
    @Min(1)
    private Integer nbOfPage;
    
    @NotNull
    @Column(name = "title")
    private String title;
 
    // Constructors:
    public Book() {
    }
 
    public Book(String isbn, String title, Set<String> authorsSet) {
    	this.isbn = isbn;
        this.title = title;
        authors = new HashSet<String>();
        this.authors.addAll(authorsSet);
    }
 
    // String Representation:
    @Override
    public String toString() {

        return "ISBN:"+ isbn+ "; "+ title + " by " + authors;
    } 
}