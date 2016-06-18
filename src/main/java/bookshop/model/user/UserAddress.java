package bookshop.model.user;

//import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="ADDRESS")
public class UserAddress {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@NotEmpty
    @Column(name="name", nullable=false)
	private String name;
	
	@NotEmpty
    @Column(name="street", nullable=false)
	private String street;
	
	@NotEmpty
    @Column(name="city", nullable=false)
	private String city;
	
	@NotEmpty
    @Column(name="region")
	private String region;
	
	@NotEmpty
	@Column(name="country")
//[TBD]	private Locale country;
	private String country;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "user_id", insertable = true)
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUserId(User user) {
		this.user = user;
	}
	
	public UserAddress(){
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public UserAddress(String name, String street, String city, String region, String country){
	    this.name=name;
	    this.street = street;
	    this.city = city;
	    this.region = region;
	    this.country = country;
	}
	
	@Override
	public String toString(){
		return name +";\n "+ street + "\n " + city + "; "+ region + "\n " + country; 
	}
	


}
