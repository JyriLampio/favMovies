package favMovies.domain;

import org.springframework.data.repository.CrudRepository;

public interface CountryRepo extends CrudRepository<Country, Long> {
	Country findByName(String name);
	Country findById(long id);
}
