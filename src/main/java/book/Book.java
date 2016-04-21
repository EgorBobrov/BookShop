package book;

import java.io.Serializable;
import java.sql.Date;
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
    private Date additonDate;
 
    // Constructors:
    public Book() {
    }
 
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.additonDate = new Date(System.currentTimeMillis());
    }
 
    // String Representation:
    @Override
    public String toString() {
        return title + " by " + author + "( added on " + additonDate + ")";
    } 
}