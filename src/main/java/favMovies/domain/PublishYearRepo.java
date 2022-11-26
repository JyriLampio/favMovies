package favMovies.domain;

import org.springframework.data.repository.CrudRepository;

public interface PublishYearRepo extends CrudRepository<PublishYear, Long> {
	PublishYear findByName(int name);

}
