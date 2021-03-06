/*
 * In MVC paradigm this class would be the Model.
 * User represents any user of our website - regular user, admin or DBA.  
 */

package com.epamjuniors.bookshop.bookshop_model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

import com.epamjuniors.bookshop.bookshop_model.book.Book;
import com.epamjuniors.bookshop.bookshop_model.book.Comment;
 
@Entity
@Table(name="APP_USER")
public class User implements Serializable{
	
	public User(String ssoId, String password){
		this.ssoId = ssoId;
		this.password = password;
	}
	
	public User(){
	}
 
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
 
    @NotEmpty
    @Column(name="SSO_ID", unique=true, nullable=false)
    private String ssoId;
     
    @NotEmpty
    @Column(name="PASSWORD", nullable=false)
    private String password;
         
    @NotEmpty
    @Column(name="FIRST_NAME", nullable=false)
    private String firstName;
 
    @NotEmpty
    @Column(name="LAST_NAME", nullable=false)
    private String lastName;
 
    @NotEmpty
    @Column(name="EMAIL", nullable=false)
    private String email;
 
    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "APP_USER_USER_PROFILE", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
    private Set<UserProfile> userProfiles = new HashSet<UserProfile>();
 
    @ManyToMany(mappedBy = "likers", fetch = FetchType.EAGER)
    private Set<Comment> likedComments = new HashSet<Comment>();
    
    @ManyToMany(mappedBy = "dislikers", fetch = FetchType.EAGER)
    private Set<Comment> dislikedComments = new HashSet<Comment>();
    
    @ManyToMany(mappedBy = "buyers")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Book> basket = new ArrayList<Book>();
    
    @ManyToMany(mappedBy = "owners")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Book> inventory = new ArrayList<Book>();
    
    @OneToMany(targetEntity=UserAddress.class, mappedBy="user", fetch = FetchType.EAGER)
    private Set<UserAddress> addresses;

    public Set<UserAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<UserAddress> addresses) {
		this.addresses = addresses;
	}
	
	public boolean addAddress(UserAddress ua){
		return this.addresses.add(ua);
	}
	
	public boolean removeAddress(UserAddress ua){
		return this.addresses.remove(ua);
	}

	public Set<Comment> getDislikedComments() {
		return dislikedComments;
	}

	public void setDislikedComments(Set<Comment> dislikedComments) {
		this.dislikedComments = dislikedComments;
	}

	public List<Book> getBasket() {
		return basket;
	}

	public void setBasket(List<Book> basket) {
		this.basket = basket;
	}

	public List<Book> getInventory() {
		return inventory;
	}

	public void setInventory(List<Book> inventory) {
		this.inventory = inventory;
	}

	public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getSsoId() {
        return ssoId;
    }
 
    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }
 
    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }
    
    public Set<Comment> getLikedComments() {
        return this.likedComments;
    }
 
    public void setLikedComments(Set<Comment> comments) {
        this.likedComments = comments;
    }
    
    public boolean addToBasket(Book b) {
		return basket.add(b);
	}
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((ssoId == null) ? 0 : ssoId.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (ssoId == null) {
            if (other.ssoId != null)
                return false;
        } else if (!ssoId.equals(other.ssoId))
            return false;
        return true;
    }
 
    // password is included just for convenience (in case we forget it)
    @Override
    public String toString() {
        return "User [id=" + id + ", ssoId=" + ssoId + ", password=" + password
                + ", firstName=" + firstName + ", lastName=" + lastName
                + ", email=" + email + "]";
    }
     
}