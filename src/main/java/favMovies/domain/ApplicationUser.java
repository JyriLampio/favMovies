package favMovies.domain;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.PreRemove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import favMovies.domain.ApplicationUserRepo;
import favMovies.web.SecurityController;

@Service
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

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "movie_likes", joinColumns = @JoinColumn(name = "applicationUser_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	Set<Movie> likedMovies;
	//private List<Movie> likedMovies = new ArrayList<Movie>();
	//Set<Movie> likedMovies = new HashSet < Movie > ();

	

	public ApplicationUser(String firstName, String lastName, String role, String username, String passwordHash) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.username = username;
		this.passwordHash = passwordHash;
	}
/*
	public ApplicationUser(String firstName, String lastName, String role, String username, String passwordHash List<Movie> likedMoviesSet<Movie> likedMovies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.username = username;
		this.passwordHash = passwordHash;
		//this.likedMovies = (List<Movie>) likedMovies;
	}*/
	
    public void removeMovie(Movie movie, ApplicationUser user, ApplicationUserRepo repo) {
    	System.out.println("ENNEN LEFFAN POISTOA" + movie);
    	user.getLikedMovies().remove(movie);
    	System.out.println("Poiston jälkeeneee" + movie);
    	movie.getApplicationUsers().remove(this);
    	System.out.println("Poiston jälkeen" + movie);
    	repo.save(user);
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
	/*
	public void setLikedMovies(List<Movie> likedMovies) {
		this.likedMovies = likedMovies;
	}

	public List<Movie> getLikedMovies() {
		return likedMovies;
	}*/
	
	/*@PreRemove
	private void preRemove() {
		likedMovies.forEach(like -> like.setLikes(null));
	}
*/
	@Override
	public String toString() {
		return "Name " + firstName + " " + lastName + ", username=" + username + " Role: " + role + " Password: " + passwordHash + " Movies: " + likedMovies;
	}

}