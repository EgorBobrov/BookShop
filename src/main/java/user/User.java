/*
 * In MVC paradigm this class would be the Model.
 * 
 */

package user;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
 
@Entity
public class User implements Serializable {
	//a universal version identifier for a Serializable class 
	//Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object
    private static final long serialVersionUID = 1L;
 
    // Persistent Fields:
    @Id @GeneratedValue
    Long id;
    private String name;
    private String email;
    private String password;
    private Date registrationDate;
 
    // Constructors:
    public User() {
    }
 
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.registrationDate = new Date(System.currentTimeMillis());
        this.password = password;
    }
 
    // String Representation on page:
    @Override
    public String toString() {
        return name + ", whose email is " + email + " (registered on " + registrationDate + ")";
    } 
}