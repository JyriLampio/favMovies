package favMovies.domain;

import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepo extends CrudRepository<ApplicationUser, Long> {
	
	ApplicationUser findByUsername(String username);
	ApplicationUser findById(long id);
	ApplicationUser findByRole(String role);
	ApplicationUser findByFirstName(String firstName);
}