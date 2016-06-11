package bookshop.model.book;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import bookshop.model.user.User;


@Entity
@Table(name="COMMENT")
public class Comment implements Comparable<Comment> {
	
    @Id 
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "date")
    private String date;
    
    @Column(name = "text")
    @Size(max = 5000)
    private String text;
    
    @Column(name = "likes")
    private int likesCount;
    
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = User.class) 
    @JoinTable(name="COMMENT_LIKES", joinColumns=@JoinColumn(name="comment_id"), inverseJoinColumns=@JoinColumn(name="user_id"))
    private Set<User> likers = new HashSet<User>();
    
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = User.class) 
    @JoinTable(name="COMMENT_DISLIKES", joinColumns=@JoinColumn(name="comment_id"), inverseJoinColumns=@JoinColumn(name="user_id"))
    private Set<User> dislikers = new HashSet<User>();
    
	@Column(name = "user")
	private String user;
    
    @Column(name = "dislikes")
    private int dislikesCount;

    public String toString() {
    	return this.text + " posted by " + this.user + " on " + this.date;
    }
    
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public int getLikes() {
		return likesCount;
	}
	public void setLikes(int likes) {
		this.likesCount = likes;
	}
	
	public Set<User> getLikers() {
		return likers;
	}
	public void setLikers(Set<User> likers) {
		this.likers = likers;
	}
	
	public int getDislikes() {
		return dislikesCount;
	}
	public void setDislikes(int dislikes) {
		this.dislikesCount = dislikes;
	}
	
	public Set<User> getDislikers() {
		return dislikers;
	}
	public void setDislikers(Set<User> dislikers) {
		this.dislikers = dislikers;
	}

    public Comment() {   	
    }
    
    public Comment(String date, String text, String user) {
    	this.date = date;
    	this.text = text;
    	this.user = user;
    }
	
	public void like() {
		likesCount ++;
	}
	
	public void unlike() {
		likesCount --;
	}
	
	public void dislike() {
		dislikesCount ++;
	}
	
	public void undislike() {
		dislikesCount --;
	}
	
	public boolean addLiker(User liker) {
		return this.likers.add(liker);
	}
	
	public boolean removeLiker(User liker) {
		return this.likers.remove(liker);
	}
	
	public boolean isItLikedByMe(String username) {
		for (User u: likers)
			if (u.getSsoId().equals(username))
				return true;
		return false;
	}
	
	public boolean addDisliker(User disliker) {
		return this.dislikers.add(disliker);
	}
	
	public boolean removeDisliker(User disliker) {
		return this.dislikers.remove(disliker);
	}
	
	
	public boolean isItDislikedByMe(String username) {
		for (User u: dislikers)
			if (u.getSsoId().equals(username))
				return true;
		return false;
	}
	
	//comparing comments by the time thay've been written on
	@Override
	public int compareTo(Comment other){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss '+0300'");
		LocalDateTime thisdate = LocalDateTime.parse(this.date, formatter);
		LocalDateTime otherdate = LocalDateTime.parse(other.getDate(), formatter);
	    return thisdate.isBefore(otherdate) ? -1 : (thisdate.isAfter(otherdate)? 1 :0);
	}

}
