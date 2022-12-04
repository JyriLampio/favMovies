package favMovies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

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
	

	@Autowired
	ApiParser apiParser;
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
			apiParser.addInitialGenres();
			apiParser.addInitialMovies();
			
			Genre genre = new Genre(28, "Nnalle");
			
			Movie movie1 = new Movie("Jaakon elokuva", "Olli Ohjaaja", publishYearRepo.findById(12), 7555, languageRepo.findById(90), genreRepo.findById(73));
			Movie movie2 = new Movie("Jonnan elokuva", "Olli Ohjaaja", publishYearRepo.findById(17), 15, languageRepo.findById(167), genreRepo.findById(79));
			List<Movie> movielist = new ArrayList<>();
			movielist.add(movie2);
			ApplicationUser user1 = applicationUserRepo.save(new ApplicationUser("Jaakko", "Pavunvarsi", "USER", "Jaakko91", "$2a$10$lYT2Sth210v1rmHp2L/cQ.iQUmjJHWlZddVTanmFyrZ83iqqYoO4K"));
			ApplicationUser user2 = applicationUserRepo.save(new ApplicationUser("Jonna", "Pajunvarsi", "ADMIN", "Jonna92", "$2a$10$lYT2Sth210v1rmHp2L/cQ.iQUmjJHWlZddVTanmFyrZ83iqqYoO4K"));
			System.out.println("TÄMÄ ON STARTISTA " + movie1);
			applicationUserRepo.save(user1);
			applicationUserRepo.save(user2);
			//user1.setLikedMovies(movielist);
			//user1.getLikedMovies().add(movie1);
			//user2.getLikedMovies().add(movie2);
			

			System.out.println("TÄMÄ ON STARTISTA " + user1);
			System.out.println("TÄMÄ ON STARTISTA " + user2);

			
			//movie1.getApplicationUsers().add(user);
		};
	}
}
