package favMovies.domain;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
public class ApplicationUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstName, lastName;
	private String role;
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	@Column(name = "password", nullable = false)
	private String passwordHash;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "movie_likes", joinColumns = @JoinColumn(name = "applicationUser_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	Set<Movie> likedMovies = new HashSet < Movie > ();

	public ApplicationUser(String firstName, String lastName, String role, String username, String passwordHash) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.username = username;
		this.passwordHash = passwordHash;
	}

	public ApplicationUser(String firstName, String lastName, String role, String username, String passwordHash, Set<Movie> likedMovies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.username = username;
		this.passwordHash = passwordHash;
		this.likedMovies = likedMovies;
	}

	public ApplicationUser() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
    public Set < Movie > getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(Set < Movie > likedMovies) {
        this.likedMovies = likedMovies;
    }

	@Override
	public String toString() {
		return "Name " + firstName + " " + lastName + ", username=" + username + " Role: " + role + " Password: " + passwordHash + " Movies: " + likedMovies;
	}

}