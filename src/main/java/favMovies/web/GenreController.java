package favMovies.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import favMovies.domain.Genre;
import favMovies.domain.GenreRepo;

@CrossOrigin
@Controller
public class GenreController {
	@Autowired
	private GenreRepo genreRepo;
	
	// Show a page with all genres
	@GetMapping("/genres")
	public String genreList(Model model) {
		model.addAttribute("genres", genreRepo.findAll());
		return "genreList";
	}
	
	// Add a genre
	@GetMapping("/addgenre")
	public String addGenre(Model model) {
		model.addAttribute("genre", new Genre());
		return "addGenre";
	}
	
	// Save new genre
	@PostMapping("/savegenre")
	public String saveGenre(Genre genre) {
		try {
			genreRepo.save(genre);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "redirect:/genres";
	}
	
	// Delete genre
	@GetMapping("/deletegenre/{id}")
	public String deleteGenre(@PathVariable("id") Long id, Model model) {
		genreRepo.deleteById(id);
		return "redirect:../genres";
	}
	
	
	//REST
	
	
	@PostMapping("api/genres")
	public ResponseEntity<Genre> postGenre(@RequestBody Genre genre){
		try {
			return ResponseEntity.status(203).body(genreRepo.save(genre));
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
	
	
	@GetMapping("api/genres")
	public ResponseEntity<List<Genre>> getAllGenres(){
		try {
			List<Genre> genres = (java.util.List<Genre>) genreRepo.findAll();
			return ResponseEntity.ok().body(genres);			
		}catch(Exception e) {
			return ResponseEntity.status(500).build();			
		}		
	}
	
	@GetMapping("api/genres/{id}")
	public ResponseEntity<Optional<Genre>> getGenre(@PathVariable Long id){
		try {
			Optional<Genre> genre = genreRepo.findById(id);
			return ResponseEntity.ok().body(genre);				
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}		
	}
	
	@DeleteMapping("api/genres/{id}")
	public ResponseEntity<Optional<Genre>> deleteGenre(@PathVariable Long id){
		try {
			Optional<Genre> genre =  genreRepo.findById(id);
			if(genre.isEmpty()) {
				return ResponseEntity.ok().build();
			}
			genreRepo.deleteById(id);		
			return ResponseEntity.status(204).body(genre); 		
		}catch(Exception e) {
			return ResponseEntity.status(500).build();			
		}	
	}
	
	@PutMapping("api/genres/{id}")
	public ResponseEntity<Genre> updateGenre(@RequestBody Genre genreUpdated, @PathVariable Long id){
		Optional<Genre> genre = genreRepo.findById(id);
		try {
			if(genre.isEmpty()) {
				return ResponseEntity.status(400).build();
			}			
			genreUpdated.setId(id);
			return ResponseEntity.ok().body(genreUpdated);	
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

}
