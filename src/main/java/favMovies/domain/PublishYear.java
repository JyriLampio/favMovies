package favMovies.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

@Entity
public class PublishYear {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int name;

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "publishYear")
	private List<Movie> movies;

	public PublishYear() {
	};

	public PublishYear(int name) {
		this.name = name;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public List<Movie> getMovies() {
		return movies;
	}
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	@PreRemove
	private void preRemove() {
		movies.forEach(movie -> movie.setPublishYear(null));
	}

}
