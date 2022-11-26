package favMovies.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	// @Column annotation can be used to specify mapped column. Example:
	// @Column(name=”title_name”)
	private String title;
	private String director;
	
	@ManyToOne
	@JoinColumn(name = "publishYearid")
	private PublishYear publishYear;
	
	private String tmdb;
	
	@ManyToOne
	@JoinColumn(name = "countryid")
	private Country country;

	@ManyToOne
	@JoinColumn(name = "genreid")
	private Genre genre;

	public Movie() {
	};

	public Movie(String title, String director, PublishYear publishYear, String tmdb, Country country,
			Genre genre) {
		this.title = title;
		this.director = director;
		this.publishYear = publishYear;
		this.tmdb = tmdb;
		this.country = country;
		this.genre = genre;

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public PublishYear getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(PublishYear publishYear) {
		this.publishYear = publishYear;
	}

	public String getTmdb() {
		return tmdb;
	}

	public void setTmdb(String tmdb) {
		this.tmdb = tmdb;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
			this.genre = genre;
	}
}
