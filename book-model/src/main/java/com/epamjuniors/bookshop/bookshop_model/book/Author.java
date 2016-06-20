package com.epamjuniors.bookshop.bookshop_model.book;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="AUTHOR")
//@Proxy(lazy=false) 
public class Author {

    // attributes

	@Id
    @Column(length = 50, name = "name", nullable = false)
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @Column(length = 5000)
    @Size(max = 5000)
    private String bio;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<Book>();
    
    @Column(name = "picture")
    private String picture;
    
    public Set<Book> getBooks() {
        return this.books;
    }
    
    public void setBooks(Set<Book> b) {
        this.books = b;
    }
    
    public Boolean removeBook(Book b){
    	return this.books.remove(b);
    }
    
    protected Author(){}
    
    public Author(String name){
    	this.name = name;
    }

    public String getname() {
        return name;
    }

    @Column(name = "name", nullable = false)
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "bio")
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picAdress) {
        this.picture = picAdress;
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
        if (name != null) {
            if (!name.equals(other.name)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result +((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }


    @Override
    public String toString() {
        return this.name;
    }
    
    //handling jsp string input which is meent to be a set of authors
    public static Set<Author> toAuthor (String text){
    	String[] authors = text.replaceAll("\\[|\\]", "").split(",");
    	Set<Author> setAuthors = new HashSet<Author>();
    	for (String a: authors){
    		setAuthors.add(new Author(a.trim()));
    	}
    	return setAuthors;
    }
}
