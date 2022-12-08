package favMovies;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
//@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = { DemoApplication.class })
@ContextConfiguration(classes = FavMoviesApplication.class)
@SpringBootTest
class RestTests {
	
	String movieURL = "http://localhost:8080/api/movies/";

	@Autowired
	private WebApplicationContext webAppContext;

	private MockMvc mocksMvc;

	@BeforeEach
	public void setup() {
		mocksMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();

	}

	@Test
	public void apiStatusOk() throws Exception {
		mocksMvc.perform(get(movieURL)).andExpect(status().isOk());
	}

	@Test
	void checkProductInfo() throws Exception {
		long id = 300;
		mocksMvc.perform(get(movieURL + id))
		.andExpect(jsonPath("$.title").value("The Sender"))
		.andExpect(jsonPath("$.tmdbId").value(69870))
		.andDo(print());
	}
}
