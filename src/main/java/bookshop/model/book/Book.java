/*
 * In MVC paradigm this class would be the Model.
 * 
 */

package bookshop.model.book;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn; 

import org.hibernate.annotations.Formula;

@Entity
@Table(name="BOOK")
public class Book implements Serializable {
	//a universal version identifier for a Serializable class 
	//Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object
    private static final long serialVersionUID = 2L;
    
    // Persistent Fields:
    @Id 
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;
  
    //By default, all fields of the class are mapped to table columns with the same name. 
    //Use the @Column annotation when the field and column names differ
    @Column(name = "isbn", length = 15) 
    @NotNull
    @Size(max = 15)
    private String isbn;
    
    // TODO: rename to nbOfPages
    @Column(name = "nb_of_pages")
    @Min(1)
    private Integer nbOfPages;
    
    @NotNull
    @Column(name = "title")
    private String title;
    
    @Column(name = "cover")
    private String cover;

    @Column(name = "description")
    private String description;
    
    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Author.class) 
    @JoinTable(name="AUTHOR_BOOK", joinColumns=@JoinColumn(name="book_id"), inverseJoinColumns=@JoinColumn(name="author_name"))
    private Set<Author> authors = new HashSet<Author>();

    @Column(name = "price")
    protected Integer price;
    
    @Column(name = "discount")
    // decimal, meant to be [0.00-1.00]
    protected Double discount;
    
    @Column(name = "amountInStock")
    protected Integer amountInStock;

    @Formula("price*(1-discount)")
    protected Integer priceWDiscount;
    
    @ElementCollection(fetch=FetchType.EAGER,targetClass = Genre.class)
    @Enumerated(value = EnumType.STRING)
    @CollectionTable(
            name = "GENRE_BOOK", 
            joinColumns = @JoinColumn(name = "book_id")
    )
    @Column(name = "genre")
    protected Set<Genre> genres= new HashSet<Genre>();
    
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Comment.class) 
    private List<Comment> comments = new ArrayList<Comment>();
    
    public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	// Constructors:
    public Book() {
    }
 
    public Book(String isbn, String title, Integer nbOfPages, String description, Set<Author> authors, Integer price, Double discount) {
    	this.isbn = isbn;
        this.title = title;
        this.nbOfPages = nbOfPages;
        this.description = description;
        this.authors = authors;
        this.price = price;
        this.discount = discount;
    }
 
    // String Representation:
    @Override
    public String toString() {
        return "id: "+ id + "; "+ title;
    }
    
    public String getIsbn() {
		return isbn;
    }
    
    public String getTitle() {
    	return title;
    }
    public Integer getNbOfPages() {
		return nbOfPages;
	}
    public Long getId() {
        return id;
    }
    public Integer getPrice() {
		return price;
	}
    public Double getDiscount() {
		return discount;
	}
    public Integer getAmountInStock() {
		return this.amountInStock;
    }
    public Integer getPriceWDiscount() {
		return this.priceWDiscount;
	}
    public String getCover() {
    	return this.cover;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
		this.title = title;
	}
    public String getDescription() {
		return description;
	}
    public void setDescription(String description) {
		this.description = description;
	}
    public void setNbOfPages(Integer nbOfPages) {
		this.nbOfPages = nbOfPages;
	}
    public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

    public Set<Author> getAuthors() {
        return this.authors;
    }

    public void setAuthors(final Set<Author> authors) {
        this.authors = authors;
    }
    public Set<Genre> getGenres() {
        return this.genres;
    }

    public void setGenres(final Set<Genre> genres) {
        this.genres = genres;
    }
    public void setPrice(Integer price) {
		this.price = price;
	}
    public void setDiscount(Double discount) {
		this.discount = discount;
	}
    public void setPriceWDiscount(Integer priceWDiscount) {
		this.priceWDiscount = priceWDiscount;
	}
    public void setAmount(Integer amount) {
		this.amountInStock = amount;
	}
    public void setCover(String cover) {
		this.cover = cover;
	}
    
    public Boolean removeAuthor (Author a){
    	return this.getAuthors().remove(a);
    }

}