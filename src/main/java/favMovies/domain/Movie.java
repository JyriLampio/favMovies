package favMovies.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	// @Column annotation can be used to specify mapped column. Example:
	// @Column(name=”title_name”)
	@NotEmpty(message = "Title cannot be empty.")
	@Column(columnDefinition="TEXT", length = 2048)
	private String title;
	
	@NotEmpty(message = "User's email cannot be empty.")
	@Column(columnDefinition="TEXT", length = 2048)
	private String overview;
	
	@ManyToOne
	@JoinColumn(name = "publishYearid")
	private PublishYear publishYear;
	
	private int tmdbId;
	
	@ManyToOne
	@JoinColumn(name = "languageid")
	private Language language;

	@ManyToOne
	@JoinColumn(name = "genreid")
	private Genre genre;
	
    @ManyToMany(mappedBy = "likedMovies")
    Set<ApplicationUser> likes = new HashSet<ApplicationUser>();

	public Movie() {
		super();
	};

	public Movie(String title, String overview, PublishYear publishYear, int tmdbId, Language language,
			Genre genre) {
		this.title = title;
		this.overview = overview;
		this.publishYear = publishYear;
		this.tmdbId = tmdbId;
		this.language = language;
		this.genre = genre;

	}
	
	public Movie(String title, String overview, PublishYear publishYear, int tmdbId, Language language,
			Genre genre, Set<ApplicationUser> likes) {
		this.title = title;
		this.overview = overview;
		this.publishYear = publishYear;
		this.tmdbId = tmdbId;
		this.language = language;
		this.genre = genre;
		this.likes = likes;
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

	public int getTmdbId() {
		return tmdbId;
	}

	public void setTmdbId(int tmdbId) {
		this.tmdbId = tmdbId;
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
	

    public Set<ApplicationUser> getApplicationUsers() {
        return likes;
    }

    public void setApplicationUsers (Set<ApplicationUser> set) {
        this.likes = likes;
    }
}
