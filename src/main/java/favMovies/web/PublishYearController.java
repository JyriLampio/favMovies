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
import favMovies.domain.PublishYear;
import favMovies.domain.PublishYearRepo;

@CrossOrigin
@Controller
public class PublishYearController {
	@Autowired
	private PublishYearRepo publishYearRepo;
	
	// Show a page with all genres
	@GetMapping("/publishyears")
	public String yearList(Model model) {
		model.addAttribute("publishyears", publishYearRepo.findAll());
		return "yearList";
	}
	
	/*
	// Add a genre
	@GetMapping("/addgenre")
	public String addYear(Model model) {
		model.addAttribute("year", new PublishYear());
		return "addYear";
	}
	
	// Save new genre
	@PostMapping("/savegenre")
	public String saveGenre(Genre genre) {
		try {
			publishYearRepo.save(genre);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "redirect:/genres";
	}*/
	
	// Delete genre
	@GetMapping("/deleteyear/{id}")
	public String deleteGenre(@PathVariable("id") Long id, Model model) {
		publishYearRepo.deleteById(id);
		return "redirect:../publishyears";
	}
	/*
	
	//REST
	
	
	@PostMapping("api/genres")
	public ResponseEntity<Genre> postGenre(@RequestBody Genre genre){
		try {
			return ResponseEntity.status(203).body(publishYearRepo.save(genre));
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
	
	
	@GetMapping("api/genres")
	public ResponseEntity<List<Genre>> getAllGenres(){
		try {
			List<Genre> genres = (java.util.List<Genre>) publishYearRepo.findAll();
			return ResponseEntity.ok().body(genres);			
		}catch(Exception e) {
			return ResponseEntity.status(500).build();			
		}		
	}
	
	@GetMapping("api/genres/{id}")
	public ResponseEntity<Optional<Genre>> getGenre(@PathVariable Long id){
		try {
			Optional<Genre> genre = publishYearRepo.findById(id);
			return ResponseEntity.ok().body(genre);				
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}		
	}
	
	@DeleteMapping("api/genres/{id}")
	public ResponseEntity<Optional<Genre>> deleteGenre(@PathVariable Long id){
		try {
			Optional<Genre> genre =  publishYearRepo.findById(id);
			if(genre.isEmpty()) {
				return ResponseEntity.ok().build();
			}
			publishYearRepo.deleteById(id);		
			return ResponseEntity.status(204).body(genre); 		
		}catch(Exception e) {
			return ResponseEntity.status(500).build();			
		}	
	}
	
	@PutMapping("api/genres/{id}")
	public ResponseEntity<Genre> updateGenre(@RequestBody Genre genreUpdated, @PathVariable Long id){
		Optional<Genre> genre = publishYearRepo.findById(id);
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
*/
}
