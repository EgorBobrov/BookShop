package bookshop.model.book;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.List;

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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bookshop.converter.RoleToUserProfileConverter;
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
    
    @Transient
	private static Logger logger = LoggerFactory.getLogger(RoleToUserProfileConverter.class);

    public String toString() {
    	return this.text   +" posted by "  + this.user +  " on " +  this.date;
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
	
	//converting date from string to actual comparable format. will return min date if formatting errors.
	private static LocalDateTime formatDate(String date){
		try {
			DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
					.append(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss '+0300'")).toFormatter().withLocale(Locale.ENGLISH);
			return LocalDateTime.parse(date, formatter);
		} catch (DateTimeParseException e){
			logger.error("formatting error when casting "+date.toString()+": "+e.getStackTrace());
		}
		return LocalDateTime.MIN;
	}
	
	//comparing comments by the time thay've been written on
	@Override
	public int compareTo(Comment other){
		LocalDateTime thisdate = formatDate(this.date);
		LocalDateTime otherdate = formatDate(other.getDate());
			
		return thisdate.isBefore(otherdate) ? -1 : (thisdate.isAfter(otherdate)? 1 :0);
	}
	
	public static Long getLastCommentDate(List<Comment> comments) {
		java.util.Collections.sort(comments);
  		java.util.Collections.reverse(comments);
  		if(comments.size() > 0) {
  			Comment last = comments.get(0);
  			ZonedDateTime zdt = formatDate(last.getDate()).atZone(ZoneId.systemDefault());
  			return zdt.toEpochSecond();
  		}
  		return 0l;
  	}

}
