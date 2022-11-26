package favMovies;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import favMovies.domain.Country;
import favMovies.domain.CountryRepo;
import favMovies.domain.Genre;
import favMovies.domain.GenreRepo;
import favMovies.domain.Movie;
import favMovies.domain.MovieRepo;
import favMovies.domain.PublishYear;
import favMovies.domain.PublishYearRepo;


@SpringBootApplication
public class FavMoviesApplication {
	private static final Logger log = LoggerFactory.getLogger(FavMoviesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FavMoviesApplication.class, args);
	}

	@Bean
	public CommandLineRunner movieDemoing(MovieRepo movieRepo, GenreRepo genreRepo, CountryRepo countryRepo, PublishYearRepo publishYearRepo) {
		return (args) -> {

			log.info("Saving data for testing purp");
			genreRepo.save(new Genre(" "));
			Genre comedy = genreRepo.save(new Genre("Comedy"));
			Genre horror = genreRepo.save(new Genre("Horror"));
			Country UK = countryRepo.save(new Country("UK"));
			Country USA = countryRepo.save(new Country("USA"));
			Country USsA = countryRepo.save(new Country("USAsdadasd"));
			Country UsadasdSsA = countryRepo.save(new Country("USAsdadsdasdasdf"));
			Country UsadafasfsdSsA = countryRepo.save(new Country("USAsdaddsfssdasdasd"));

	

		for (int yearss = 1950; yearss <= 2019; yearss++) {
			publishYearRepo.save(new PublishYear(yearss));
				}
			PublishYear yearss =  publishYearRepo.save(new PublishYear(2020));		
			PublishYear years =  publishYearRepo.save(new PublishYear(2021));
			PublishYear yearsss =  publishYearRepo.save(new PublishYear(2022));

			log.info("save a couple of products");
			movieRepo.save(new Movie("John Wick", "Olli Ohjaaja", yearss, "https://www.themoviedb.org/movie/245891-john-wick", UK, horror));
			movieRepo.save(new Movie("Taru Herrojen herroista", "Olli Ohjaaja", yearss, "https://www.themoviedb.org/movie/120-the-lord-of-the-rings-the-fellowship-of-the-ring", UK, comedy));
			movieRepo.save(new Movie("Rambo", "Olli Ohjaaja", years, "https://www.themoviedb.org/movie/7555-rambo", USA, comedy));
			movieRepo.save(new Movie("Rambo", "Olli Ohjaaja", yearsss, "https://www.themoviedb.org/movie/7555-rambo", USsA, comedy));
			movieRepo.save(new Movie("Rambo", "Olli Ohjaaja", yearss, "https://www.themoviedb.org/movie/7555-rambo", USA, comedy));
			movieRepo.save(new Movie("Rambo", "Olli Ohjaaja", years, "https://www.themoviedb.org/movie/7555-rambo", UsadasdSsA, comedy));
			movieRepo.save(new Movie("Rambo", "Olli Ohjaaja", years, "https://www.themoviedb.org/movie/7555-rambo", USA, comedy));
			movieRepo.save(new Movie("Rambo", "Olli Ohjaaja", yearsss, "https://www.themoviedb.org/movie/7555-rambo", USA, comedy));
			movieRepo.save(new Movie("Rambo", "Olli Ohjaaja", years, "https://www.themoviedb.org/movie/7555-rambo", USA, comedy));
			movieRepo.save(new Movie("Rambo", "Olli Ohjaaja", yearsss, "https://www.themoviedb.org/movie/7555-rambo", UsadafasfsdSsA, comedy));
			

			for (Movie movie : movieRepo.findAll()) {
				log.info(movie.toString());
			}
		};
	}
}
