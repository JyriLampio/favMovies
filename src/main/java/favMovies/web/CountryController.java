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

import favMovies.domain.Country;
import favMovies.domain.CountryRepo;

@CrossOrigin
@Controller
public class CountryController {
	
	@Autowired
	private CountryRepo countryRepo;

	// Show a page with all countries
	@GetMapping("countries")
	public String countryList(Model model) {
		model.addAttribute("countries", countryRepo.findAll());
		return "countryList";
	}
	
	// Add a country
	@GetMapping("/addcountry")
	public String addCountry(Model model) {
		model.addAttribute("country", new Country());
		return "addCountry";
	}
	
	// Save new manufacturer
	@PostMapping("/savecountry")
	public String saveCountry(Country country) {
		try {
			countryRepo.save(country);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "redirect:/countries";
	}
	
	// Delete manufacturer in web page
	@GetMapping("/deletecountry/{id}")
	public String deleteCountry(@PathVariable("id") Long id, Model model) {
		countryRepo.deleteById(id);
		return "redirect:../countries";
	}
	
	//REST
	
	@PostMapping("api/countries")
	public ResponseEntity<Country> postCountry(@RequestBody Country country){
		try {
			return ResponseEntity.status(203).body(countryRepo.save(country));
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
	
	
	@GetMapping("api/countries")
	public ResponseEntity<List<Country>> getAllcountries(){
		try {
			List<Country> countries = (java.util.List<Country>) countryRepo.findAll();
			return ResponseEntity.ok().body(countries);			
		}catch(Exception e) {
			return ResponseEntity.status(500).build();			
		}		
	}
	
	@GetMapping("api/countries/{id}")
	public ResponseEntity<Optional<Country>> getCountry(@PathVariable Long id){
		try {
			Optional<Country> country = countryRepo.findById(id);
			return ResponseEntity.ok().body(country);				
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}		
	}
	
	@DeleteMapping("api/countries/{id}")
	public ResponseEntity<Optional<Country>> deleteCountry(@PathVariable Long id){
		try {
			Optional<Country> country =  countryRepo.findById(id);
			if(country.isEmpty()) {
				return ResponseEntity.ok().build();
			}
			countryRepo.deleteById(id);		
			return ResponseEntity.status(204).body(country); 		
		}catch(Exception e) {
			return ResponseEntity.status(500).build();			
		}	
	}
	
	@PutMapping("api/countries/{id}")
	public ResponseEntity<Country> updateCountry(@RequestBody Country countryUpdated, @PathVariable Long id){
		Optional<Country> country = countryRepo.findById(id);
		try {
			if(country.isEmpty()) {
				return ResponseEntity.status(400).build();
			}			
			countryUpdated.setId(id);
			return ResponseEntity.ok().body(countryUpdated);	
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

}
