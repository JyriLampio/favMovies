package favMovies;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


import favMovies.web.GenreController;
import favMovies.web.LanguageController;
import favMovies.web.MovieController;
import favMovies.web.PublishYearController;
import favMovies.web.SecurityController;


@ContextConfiguration(classes = FavMoviesApplication.class)
@SpringBootTest
class FavMoviesApplicationTests {
	
	@Autowired
	private MovieController movieController;
	@Autowired
	private GenreController genreController;
	@Autowired
	private LanguageController languageController;
	@Autowired
	private PublishYearController publishYearController;
	@Autowired
	private SecurityController securityController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(movieController).isNotNull();
		assertThat(genreController).isNotNull();
		assertThat(languageController).isNotNull();
		assertThat(publishYearController).isNotNull();
		assertThat(securityController).isNotNull();
	}

}
