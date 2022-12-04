package favMovies.web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import favMovies.domain.ApiParser;
import favMovies.domain.ApplicationUser;
import favMovies.domain.ApplicationUserRepo;
import favMovies.domain.Genre;
import favMovies.domain.GenreRepo;
import favMovies.domain.Language;
import favMovies.domain.LanguageRepo;
import favMovies.domain.Movie;
import favMovies.domain.MovieRepo;
import favMovies.domain.PublishYear;
import favMovies.domain.PublishYearRepo;
import favMovies.web.SecurityController;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@CrossOrigin
@Controller
public class MovieController {
	
	 @Value("${name}")
	    private String names;
	 
	 public String getSecretKey() {
		    return this.names;
		}

	@Autowired
	private MovieRepo movieRepo;
	@Autowired
	private GenreRepo genreRepo;
	@Autowired
	private LanguageRepo languageRepo;
	@Autowired
	private PublishYearRepo publishYearRepo;
	@Autowired
	private ApplicationUserRepo applicationUserRepo;
	@Autowired
	private SecurityController securityController;
	@Autowired
	private ApiParser apiParser;

	// Index page
	@GetMapping("/")
	public String indexPage(Model model) {
		return "movieList";
	}

	// Show a page with all movies
	@GetMapping("/movies")
	public String movieList(Model model) {
		String subject = "All movies";

		ApplicationUser user = applicationUserRepo.findByUsername(securityController.getUserName());
		System.out.println(user);
		model.addAttribute("movies", user.getLikedMovies());
		model.addAttribute("subject", subject);
		model.addAttribute("movies", movieRepo.findAll());
		return "movieList";
	}

	// Show a page with all movies sorted by specific genre
	@GetMapping("/movies/genre/{id}")
	public String returnMoviesByGenre(@PathVariable("id") long id, Model model) {
		String genreName = genreRepo.findById(id).getName();
		String subject = genreName + " movies";
		model.addAttribute("subject", subject);
		model.addAttribute("movies", genreRepo.findById(id).getMovies());
		return "movieList";
	}
	
	// Show a page with all movies sorted by specific country
	@GetMapping("/movies/country/{id}")
	public String returnMoviesByCountry(@PathVariable("id") long id, Model model) {
		String countryName = languageRepo.findById(id).getName();
		String subject = "Movies from " + countryName;
		model.addAttribute("subject", subject);
		//model.addAttribute("country", name);
		model.addAttribute("movies", languageRepo.findById(id).getMovies());
		return "movieList";
	}
	
	// Show a page with all movies sorted by specific genre
	@GetMapping("/movies/publishyear/{id}")
	public String returnMoviesByYear(@PathVariable("id") long id, Model model) {
		int year = publishYearRepo.findById(id).getName();
		String subject = "Movies from " + year;
		model.addAttribute("subject", subject);
		model.addAttribute("movies", publishYearRepo.findById(id).getMovies());
		return "movieList";
	}
	
	/*// Old code
	@GetMapping("/add")
	public String addMovie(Model model) {
		String subject = "Add a movie";
		List<Genre> genres = (List<Genre>) genreRepo.findAll();
		List<Language> countries = (List<Language>) languageRepo.findAll();
		List<PublishYear> years = (List<PublishYear>) publishYearRepo.findAll();
		model.addAttribute("subject", subject);
		model.addAttribute("movie", new Movie());
		model.addAttribute("countries", countries);
		model.addAttribute("genres", genres);
		model.addAttribute("years", years);
		return "addMovie";
	}
	

*/
	// Save a new movie.
	@PostMapping("/saveedit")
	public String saveMovie(@Valid Movie movie, BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				System.out.println(result);
				return "editMovie";
			} else {
				System.out.println(movie.toString());
				movieRepo.save(movie);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println('2');
			return "addMovie";
		}
		return "redirect:movies";
	}
	
	// Add new movie.
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/add")
	public String addMovie(Model model) {

		return "addMovieLink";
	}
	
	// Save a new movie.
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/save")
	public String saveMovie(Model model, @RequestParam int movieId, RedirectAttributes redirectAttributes) throws IOException {
		String user = securityController.getUserName();
		System.out.println(user);
		String title = apiParser.addMovie(movieId, user);
		if (title == "0") {
		     redirectAttributes.addFlashAttribute("error", "Check the movie ID");
		     return "redirect:add";
		}
		if (title == "1") {
			 String foundDuplicate = "Movie is already on the list";
		     redirectAttributes.addFlashAttribute("duplicate", foundDuplicate);
		     return "redirect:movies";
		}
		else {
			 String confirmation = "Added " + title;
		     redirectAttributes.addFlashAttribute("confirmation", confirmation);
		     return "redirect:movies";
		}
	}


	// Edit a movie by id.
	@GetMapping("/edit/{id}")
	public String editMovie(@PathVariable("id") Long id, Model model) {
		String movieName = movieRepo.findById(id).get().getTitle();
		Movie movie = movieRepo.findById(id).get();
		List<Genre> genres = (List<Genre>) genreRepo.findAll();
		List<Language> languages = (List<Language>) languageRepo.findAll();
		List<PublishYear> years = (List<PublishYear>) publishYearRepo.findAll();
		String subject = "Edit " + movieName;
		model.addAttribute("movie", movie);
		model.addAttribute("genres", genres);
		model.addAttribute("languages", languages);
		model.addAttribute("years", years);
		model.addAttribute("subject", subject);
		return "editMovie";
	}

	// Delete a movie by id. Return to previous page. Täytä vielä country muutos tähän että countryn poistessa palaa countries sivulle
	@GetMapping(path = { "/delete/{id}", "/movies/genre/{genre}/delete/{id}", "/{country}/delete/{id}"  })
	public String deleteMovie(@PathVariable Long id, @PathVariable(required = false) String genre, Model model) {
		movieRepo.deleteById(id);
		
		if (genre != null) {
			return "redirect:/movies/genre/{genre}";
		}
		return "redirect:../movies";
	}


	// error
	@Controller
	public class Error implements ErrorController {
		@RequestMapping("/error")
		public String handleError() {
			return "error";
		}
	}

	/*
	// REST below

	@RequestMapping("api/movies")
	public ResponseEntity<List<Movie>> getAllMovies() {
		try {
			List<Movie> movieList = (List<Movie>) movieRepo.findAll();
			return ResponseEntity.ok().body(movieList);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@RequestMapping("api/movies/{id}")
	public ResponseEntity<Optional<Movie>> getMovie(@PathVariable Long id) {
		try {
			Optional<Movie> movie = movieRepo.findById(id);
			return ResponseEntity.ok().body(movie);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@DeleteMapping("api/movies/{id}")
	public ResponseEntity<Optional<Movie>> deleteMovie(@PathVariable Long id) {
		try {
			Optional<Movie> movie = movieRepo.findById(id);
			if (movie.isEmpty()) {
				return ResponseEntity.status(400).build();
			}
			movieRepo.deleteById(id);
			return ResponseEntity.ok().body(movie);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping("api/movies/{id}")
	public ResponseEntity<Movie> updateMovie(@RequestBody Movie movieUpdated, @PathVariable Long id) {

		try {
			Optional<Movie> movieFromDb = movieRepo.findById(id);
			if (movieFromDb.isEmpty()) {
				System.out.println(movieUpdated);
				return ResponseEntity.status(400).build();
			}

			Optional<Genre> genre = genreRepo.findById((movieUpdated.getGenre().getId()));

			if (genre.isEmpty()) {
				return ResponseEntity.status(400).build();
			}

			movieUpdated.setId(id);

			return ResponseEntity.ok().body(movieRepo.save(movieUpdated));

		} catch (Exception e) {
			return ResponseEntity.status(500).build();

		}
	}

	@PostMapping("api/movies/")
	public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {

		try {
			Optional<Genre> genre = genreRepo.findById((movie.getGenre().getId()));

			if (genre.isEmpty()) {
				return ResponseEntity.status(400).build();
			}
			return ResponseEntity.ok().body(movieRepo.save(movie));
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
	
	*/

}
