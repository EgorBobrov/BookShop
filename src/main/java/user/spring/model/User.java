/*
 * In MVC paradigm this class would be the Model.
 * 
 */

package user.spring.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
 
@Entity
@Table(name="users")
public class User implements Serializable {
	//a universal version identifier for a Serializable class 
	//Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object
    private static final long serialVersionUID = 1L;
 
    // Persistent Fields:
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "email")
    private String email;
    
    @Column(name = "login")
    private String login;
    
    @Column(name = "password")
    private String password;
    
/*	@Column(name = "registrationDate")
    private Date registrationDate;
*/ 
    // connecting users to roles through intermediate table user_roles
	@OneToOne(cascade=CascadeType.ALL)
	@JoinTable(name="user_roles",
		joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
		inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
	)
	private Role role;
    
    // String Representation on page:
    @Override
    public String toString() {
    	return login + ", " + email + ", id: " + id;
    } 
    public Long getId() {
		return this.id;
	}
    public String getLogin() {
		return login;
	}
    public String getEmail() {
		return email;
	}
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

/*	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
*/
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLogin(String name) {
		this.login = name;
	}

}