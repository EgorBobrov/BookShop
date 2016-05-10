/*
 * In MVC paradigm this class would be the Model.
 * 
 */

package user;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.sun.istack.internal.NotNull;
import com.sun.org.apache.xpath.internal.operations.Bool;
 
@Entity
public class User implements Serializable {
	//a universal version identifier for a Serializable class 
	//Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object
    private static final long serialVersionUID = 1L;
 
    // Persistent Fields:
    
    @Id
    @NotNull
    @Column(name = "email")
    String email;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "registrationDate")
    private Date registrationDate;
    @Column(name = "admin")
    private Boolean admin;
 
    // Constructors:
    public User() {
    }
 
    public User(String name, String email, String password, Boolean admin) {
        this.name = name;
        this.email = email;
        this.registrationDate = new Date(System.currentTimeMillis());
        this.password = password;
        this.admin = admin;
    }
 
    // String Representation on page:
    @Override
    public String toString() {
        return name + ", whose email is " + email + " (registered on " + registrationDate + " as " + (admin ? " admin" : " regular user") + ")";
    } 
    
    public String getName() {
		return name;
	}
    public String getEmail() {
		return email;
	}
    public Boolean isAdmin() {
		return admin;
	}
    
}