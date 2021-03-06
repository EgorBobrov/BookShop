/*
 * In MVC paradigm this class would be the Model representing a book.
 * 
 */

package com.epamjuniors.bookshop.bookshop_model.book;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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

import com.epamjuniors.bookshop.bookshop_model.user.User;

@Entity
@Table(name="BOOK")
public class Book implements Serializable {
	private static final long serialVersionUID = 2L;

	// Persistent Fields:
	@Id 
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;

	@Column(name = "isbn", length = 15) 
	@NotNull
	@Size(max = 15)
	private String isbn;

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

	//users who added the book into their basket
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = User.class) 
	@JoinTable(name="USER_BASKET", joinColumns=@JoinColumn(name="book_id"), inverseJoinColumns=@JoinColumn(name="user_id"))
	private List<User> buyers = new ArrayList<User>();

	//users who BOUGHT the book
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = User.class) 
	@JoinTable(name="USER_INVENTORY", joinColumns=@JoinColumn(name="book_id", unique = false), inverseJoinColumns=@JoinColumn(name="user_id", unique = false))
	private List<User> owners = new ArrayList<User>();


	@Column(name = "price")
	protected Integer price;

	@Column(name = "discount")
	// decimal, meant to be [0.00-1.00]
	protected Double discount;

	@Column(name = "amountInStock")
	protected Integer amountInStock;

	public List<User> getBuyers() {
		return buyers;
	}

	public void setBuyers(List<User> buyers) {
		this.buyers = buyers;
	}

	public List<User> getOwners() {
		return owners;
	}

	public void setOwners(List<User> owners) {
		this.owners = owners;
	}

	public void setAmountInStock(Integer amountInStock) {
		this.amountInStock = amountInStock;
	}

	@Formula("price*(1-discount)")
	protected Integer priceWDiscount;

	@Column(name = "rating")
	// sum of all votes
	private Integer rating = 0;

	@Column(name = "votes")
	// number of votes
	private Integer votes = 0;

	@Column(name = "resultrating")
	// average rating based on votes
	private Double resultRating = 0.0;

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

	public Double getResultRating() {
		return resultRating;
	}

	public void setResultRating(Double resultRating) {
		this.resultRating = resultRating;
	}


	@ElementCollection(fetch=FetchType.EAGER,targetClass = Genre.class)
	@Enumerated(value = EnumType.STRING)
	@CollectionTable(
			name = "GENRE_BOOK", 
			joinColumns = @JoinColumn(name = "book_id")
			)
	@Column(name = "genre")
	protected Set<Genre> genres= new HashSet<Genre>();

	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Comment.class)
	private Set<Comment> comments = new HashSet<Comment>();

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	// Constructors:
	public Book() {
	}

	public Book(String isbn, String title, Integer nbOfPages, String description, Set<Author> authors, Set<Genre> genres, Integer price, Double discount) {
		this.isbn = isbn;
		this.title = title;
		this.nbOfPages = nbOfPages;
		this.description = description;
		this.authors = authors;
		this.genres = genres;
		this.price = price;
		this.discount = discount;
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
	public Integer getId() {
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
	public void setId(Integer id) {
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

	public Boolean removeComment(Comment c){
		return this.comments.remove(c);
	}

	public Long getLastCommentDate(List<Comment> comments) {
		java.util.Collections.sort(comments);
		java.util.Collections.reverse(comments);
		if(comments.size() > 0) {
			Comment last = comments.get(0);

			DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
					.append(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss '+0300'")).toFormatter().withLocale(Locale.ENGLISH);
			LocalDateTime thisdate = LocalDateTime.parse(last.getDate(), formatter);
			ZonedDateTime zdt = thisdate.atZone(ZoneId.systemDefault());
			return zdt.toEpochSecond();
		}
		return 0l;
	}

	// hash, equals, toString

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Book)) {
			return false;
		}
		Book other = (Book) obj;
		if (isbn != null) {
			if (!isbn.equals(other.getIsbn())) {
				return false;
			}
		}
		if (title!= null) {
			if (!title.equals(other.getTitle())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result +((this.isbn == null) ? 0 : this.isbn.hashCode()) +((this.title == null) ? 0 : this.title.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return title;
	}


}