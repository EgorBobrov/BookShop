package user;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
 
@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
 
    // Persistent Fields:
    @Id @GeneratedValue
    Long id;
    private String name;
    private String email;
    private Date registrationDate;
 
    // Constructors:
    public User() {
    }
 
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.registrationDate = new Date(System.currentTimeMillis());
    }
 
    // String Representation on page:
    @Override
    public String toString() {
        return name + ", whose email is " + email + " (registered on " + registrationDate + ")";
    } 
}