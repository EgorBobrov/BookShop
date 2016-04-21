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
    private Date additonDate;
 
    // Constructors:
    public Book() {
    }
 
    public Book(String title) {
        this.title = title;
        this.additonDate = new Date(System.currentTimeMillis());
    }
 
    // String Representation:
    @Override
    public String toString() {
        return title + " (added on " + additonDate + ")";
    }
}