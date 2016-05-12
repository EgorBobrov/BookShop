package book.spring.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
@Table(name="AUTHOR")
public class Author {

    // attributes

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    protected Long id;

    @Column(length = 50, name = "first_name")
    @Size(min = 2, max = 50)
    protected String firstName;

    @Column(length = 50, name = "last_name", nullable = false)
    @NotNull
    @Size(min = 2, max = 50)
    protected String lastName;

    @Column(length = 5000)
    @Size(max = 5000)
    protected String bio;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @Past
    protected Date dateOfBirth;

    @Transient
    protected Integer age;
    
    @Transient
    private Set<Book> books = new HashSet<Book>();
    
    @ManyToMany(mappedBy = "BOOK")
    public Set<Book> getBooks() {
        return this.books;
    }
    
    public Author(String lastname){
    	this.lastName = lastname;
    }

    // lifecycle methods  
    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateAge() {
        if (dateOfBirth == null) {
            age = null;
            return;
        }

        Calendar birth = new GregorianCalendar();
        birth.setTime(dateOfBirth);
        Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        int adjust = 0;
        if (now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR) < 0) {
            adjust = -1;
        }
        age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + adjust;
    }

    // getters and setters 
    
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    // hash, equals, toString

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Author)) {
            return false;
        }
        Author other = (Author) obj;
        if (id != null) {
            if (!id.equals(other.id)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    @Override
    public String toString() {
        String result = " "+ getClass().getSimpleName() + " ";
        if (firstName != null && !firstName.trim().isEmpty())
            result += "firstName: " + firstName;
        if (lastName != null && !lastName.trim().isEmpty())
            result += ", lastName: " + lastName;
        if (bio != null && !bio.trim().isEmpty())
            result += ", bio: " + bio;
        if (dateOfBirth != null)
            result += ", dateOfBirth: " + dateOfBirth;
        if (age != null)
            result += ", age: " + age;
        return "id: "+ this.id + result;
    }
}
