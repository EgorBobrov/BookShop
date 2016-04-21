package book;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
 
@Entity
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
 
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