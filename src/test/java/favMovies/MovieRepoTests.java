package favMovies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import favMovies.domain.ApplicationUserRepo;
import favMovies.domain.Genre;
import favMovies.domain.GenreRepo;
import favMovies.domain.Language;
import favMovies.domain.LanguageRepo;
import favMovies.domain.Movie;
import favMovies.domain.MovieRepo;
import favMovies.domain.PublishYear;
import favMovies.domain.PublishYearRepo;


@ContextConfiguration(classes = FavMoviesApplication.class)
@SpringBootTest
class MovieRepoTests {
	
	@Autowired
	MovieRepo movieRepo;
	@Autowired
	private GenreRepo genreRepo;
	@Autowired
	private LanguageRepo languageRepo;
	@Autowired
	private PublishYearRepo publishYearRepo;
	@Autowired
	private ApplicationUserRepo applicationUserRepo;
	
	@Test
	public void findMovieName() {
		
		Movie movie = movieRepo.findById((long) 300);
		assertEquals(movie.getTitle(), "The Sender");
	}
	
	@Test
	public void FindMovieGenre() {
		
		Genre genre = movieRepo.findById((long) 300).getGenre();
		assertEquals(genre.getName(), "Horror");
	}
	
	@Test
	public void FindMovieLanguage() {
		
		Language lang = movieRepo.findById((long) 300).getLanguage();
		assertEquals(lang.getName(), "EN");
	}

	@Test
	public void FindMovieYear() {
		
		PublishYear yearPublished = movieRepo.findById((long) 300).getPublishYear();
		assertEquals(yearPublished.getName(), 1982);
	}
}
