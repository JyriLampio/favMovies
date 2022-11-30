package favMovies.domain;

import org.springframework.data.repository.CrudRepository;

public interface GenreRepo extends CrudRepository<Genre, Long> {
	Genre findByName(String name);
	Genre findBytmdbid(int genreID);
	Genre findById(long id);
}
