package favMovies.domain;

import org.springframework.data.repository.CrudRepository;

//import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends CrudRepository<Movie, Long> {

}
