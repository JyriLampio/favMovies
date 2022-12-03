package favMovies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import favMovies.domain.ApiParser;
import favMovies.domain.ApplicationUser;
import favMovies.domain.ApplicationUserRepo;
import favMovies.domain.Genre;
import favMovies.domain.GenreRepo;
import favMovies.domain.LanguageRepo;
import favMovies.domain.Language;
import favMovies.domain.Movie;
import favMovies.domain.MovieRepo;
import favMovies.domain.PublishYear;
import favMovies.domain.PublishYearRepo;

@SpringBootApplication
@EnableConfigurationProperties
public class FavMoviesApplication {
	private static final Logger log = LoggerFactory.getLogger(FavMoviesApplication.class);
	
	ApiParser geerr = new ApiParser();
	
	@Autowired
	ApplicationUserRepo applicationUserRepo;

	public static void main(String[] args) {
		SpringApplication.run(FavMoviesApplication.class, args);
	}

	@Bean
	public CommandLineRunner movieDemoing(MovieRepo movieRepo, GenreRepo genreRepo, LanguageRepo languageRepo,
			PublishYearRepo publishYearRepo) {
		return (args) -> {
			
			for (int years = 1950; years <= 2019; years++) {
				publishYearRepo.save(new PublishYear(years));
			}

			String useless = geerr.addInitialGenres(genreRepo);
			String useless2 = geerr.addInitialMovies(movieRepo, languageRepo, genreRepo, publishYearRepo);
			applicationUserRepo.save(new ApplicationUser("Jaakko", "Pavunvarsi", "USER", "Jaakko91", "$2a$10$lYT2Sth210v1rmHp2L/cQ.iQUmjJHWlZddVTanmFyrZ83iqqYoO4K"));
			applicationUserRepo.save(new ApplicationUser("Jonna", "Pajunvarsi", "ADMIN", "Jonna92", "$2a$10$lYT2Sth210v1rmHp2L/cQ.iQUmjJHWlZddVTanmFyrZ83iqqYoO4K"));
		};
	}
}
