package favMovies.domain;

import org.springframework.data.repository.CrudRepository;

public interface LanguageRepo extends CrudRepository<Language, Long> {
	Language findByName(String name);
	Language findById(long id);
}
