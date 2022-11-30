package favMovies;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import favMovies.domain.ApiParser;
import favMovies.domain.Genre;
import favMovies.domain.GenreRepo;
import favMovies.domain.LanguageRepo;
import favMovies.domain.Language;
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
	public CommandLineRunner movieDemoing(MovieRepo movieRepo, GenreRepo genreRepo, LanguageRepo languageRepo,
			PublishYearRepo publishYearRepo) {
		return (args) -> {

			ApiParser.addGenres(genreRepo);
			ApiParser.addMovies(movieRepo, languageRepo, genreRepo, publishYearRepo);
		};
	}
}
