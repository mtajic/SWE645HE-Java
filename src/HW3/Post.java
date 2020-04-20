package HW3;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the posts database table.
 * 
 */
@XmlRootElement
@Entity
@Table(name="posts")
@NamedQuery(name="Post.findAll", query="SELECT p FROM Post p")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="_id", nullable=false)
	private int _id;

	private boolean atmosphere;

	private boolean campus;

	@Column(nullable=false, length=50)
	private String city;

	@Column(length=500)
	private String content;

	@Column(nullable=false, length=45)
	private String dateOfBirth;

	@Column(name="dormroom")
	private boolean doreroom;

	@Column(nullable=false, length=70)
	private String email;

	@Column(nullable=false, length=45)
	private String firstName;

	@Column(name="interested",length=50)
	private String intrested;

	@Column(nullable=false, length=45)
	private String lastName;

	private boolean location;

	@Column(length=50)
	private String raffle;

	private boolean sports;

	@Column(nullable=false, length=50)
	private String state;

	@Column(nullable=false, length=70)
	private String street;

	private boolean student;

	@Column(length=50)
	private String suggest;

	@Column(nullable=false, length=45)
	private String tel;

	@Column(nullable=false, length=45)
	private String zip;

	public Post() {
	}

	public int getId() {
		return this._id;
	}

	public void setId(int id) {
		this._id = id;
	}

	public boolean getAtmosphere() {
		return this.atmosphere;
	}

	public void setAtmosphere(boolean atmosphere) {
		this.atmosphere = atmosphere;
	}

	public boolean getCampus() {
		return this.campus;
	}

	public void setCampus(boolean campus) {
		this.campus = campus;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean getdoreroom() {
		return this.doreroom;
	}

	public void setdoreroom(boolean doreroom) {
		this.doreroom = doreroom;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getfirstName() {
		return this.firstName;
	}

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getintrested() {
		return this.intrested;
	}

	public void setintrested(String intrested) {
		this.intrested = intrested;
	}

	public String getlastName() {
		return this.lastName;
	}

	public void setlastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean getLocation() {
		return this.location;
	}

	public void setLocation(boolean location) {
		this.location = location;
	}

	public String getRaffle() {
		return this.raffle;
	}

	public void setRaffle(String raffle) {
		this.raffle = raffle;
	}

	public boolean getSports() {
		return this.sports;
	}

	public void setSports(boolean sports) {
		this.sports = sports;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public boolean getStudent() {
		return this.student;
	}

	public void setStudent(boolean student) {
		this.student = student;
	}

	public String getSuggest() {
		return this.suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Override
	public String toString() {
		return String.format(
				"Post [\"_id\":%s, atmosphere\":%s, campus\":%s, city\":%s, content\":%s, dateOfBirth\":%s, doreroom\":%s, email\":%s, firstName\":%s, intrested\":%s, lastName\":%s, location\":%s, raffle\":%s, sports\":%s, state\":%s, street\":%s, student\":%s, suggest\":%s, tel\":%s, zip\":%s]",
				_id, atmosphere, campus, city, content, dateOfBirth, doreroom, email, firstName, intrested, lastName,
				location, raffle, sports, state, street, student, suggest, tel, zip);
	}

	
}