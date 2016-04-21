/*
 * In MVC paradigm this class would be the Model.
 * 
 */

package book;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
 
@Entity
public class Book implements Serializable {
	
	//a universal version identifier for a Serializable class 
	//Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object
    private static final long serialVersionUID = 2L;
 
    // Persistent Fields:
    @Id @GeneratedValue
    Long id;
    private String title;
    private String author;
 
    // Constructors:
    public Book() {
    }
 
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
 
    // String Representation:
    @Override
    public String toString() {
        return title + " by " + author;
    } 
}