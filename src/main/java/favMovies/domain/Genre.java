package favMovies.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

@Entity
public class Genre {

	@Id
	@GeneratedValue
	private long id;
	private int tmdbid;
	private String name;

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "genre")
	private List<Movie> movies;

	public Genre() {
	};

	public Genre(int tmdbid, String name) {
		this.name = name;
		this.tmdbid = tmdbid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
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
		movies.forEach(movie -> movie.setGenre(null));
	}

	public long getTmdbid() {
		return tmdbid;
	}

	public void setTmdbid(int tmdbid) {
		this.tmdbid = tmdbid;
	}

}
