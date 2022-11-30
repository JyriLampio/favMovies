package favMovies.domain;

import javax.persistence.Column;
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
	
	@Column(columnDefinition="TEXT", length = 2048)
	private String overview;
	
	@ManyToOne
	@JoinColumn(name = "publishYearid")
	private PublishYear publishYear;
	
	private String tmdb;
	
	@ManyToOne
	@JoinColumn(name = "languageid")
	private Language language;

	@ManyToOne
	@JoinColumn(name = "genreid")
	private Genre genre;

	public Movie() {
	};

	public Movie(String title, String overview, PublishYear publishYear, String tmdb, Language language,
			Genre genre) {
		this.title = title;
		this.overview = overview;
		this.publishYear = publishYear;
		this.tmdb = tmdb;
		this.language = language;
		this.genre = genre;

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
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
