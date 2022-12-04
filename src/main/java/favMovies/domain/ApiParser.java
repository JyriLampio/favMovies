package favMovies.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.http.HttpClient.Redirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import favMovies.web.SecurityController;

@ConfigurationProperties
@Service
public class ApiParser {

	@Autowired
	private MovieRepo movieRepo;
	@Autowired
	private GenreRepo genreRepo;
	@Autowired
	private LanguageRepo languageRepo;
	@Autowired
	private PublishYearRepo publishYearRepo;
	@Autowired
	private ApplicationUserRepo applicationUserRepo;
	@Autowired
	private SecurityController securityController;

	// Adds all genres to database
	public String addInitialGenres() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(
				"/home/jyri/Documents/Projects/Eclipse-workspace/favMoviesTMDB/src/main/resources/static/genrelist.json"));
		String line;
		StringBuilder sbuilder = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sbuilder.append(line);
		}

		line = sbuilder.toString();
		JsonObject gsonObj = new Gson().fromJson(line.toString(), JsonObject.class);
		JsonArray jsonArray = gsonObj.getAsJsonArray("genres");

		for (int i = 0; i < jsonArray.size(); i++) {
			String name = jsonArray.get(i).getAsJsonObject().get("name").getAsString();
			int Id = jsonArray.get(i).getAsJsonObject().get("id").getAsInt();
			genreRepo.save(new Genre(Id, name));
		}
		return line;
	}

	public static boolean checkDuplicate(MovieRepo movieRepo, int tmdbId) {
		Movie i = movieRepo.findByTmdbId(tmdbId);
		if (i == null) {
			return false;
		}
		return true;
	}

	// Adds sample movies to database
	public String addInitialMovies() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(
				"/home/jyri/Documents/Projects/Eclipse-workspace/favMoviesTMDB/src/main/resources/static/movielist.json"));
		String line;
		StringBuilder sbuilder = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sbuilder.append(line);
		}

		line = sbuilder.toString();
		JsonObject gsonObj = new Gson().fromJson(line.toString(), JsonObject.class);
		JsonArray jsonArray = gsonObj.getAsJsonArray("items");

		for (int i = 0; i < jsonArray.size(); i++) {
			String title = jsonArray.get(i).getAsJsonObject().get("title").getAsString();
			String overview = jsonArray.get(i).getAsJsonObject().get("overview").getAsString();
			int tmdbId = jsonArray.get(i).getAsJsonObject().get("id").getAsInt();
			String language = jsonArray.get(i).getAsJsonObject().get("original_language").getAsString().toUpperCase();
			int genreId = jsonArray.get(i).getAsJsonObject().get("genre_ids").getAsJsonArray().get(0).getAsInt();

			String releaseDate = jsonArray.get(i).getAsJsonObject().get("release_date").getAsString();
			String[] parsedReleaseYear = releaseDate.split("-", 2);
			String releaseYear = parsedReleaseYear[0];
			int year = Integer.parseInt(releaseYear);

			addLanguages(language);
			addYears(year);

			movieRepo.save(new Movie(title, overview, publishYearRepo.findByName(year), tmdbId,
					languageRepo.findByName(language), genreRepo.findBytmdbid(genreId)));

		}
		return line;
	}

	// Add movie to database with The Movie Database's movie ID.
	public String addMovie(int movieId) throws IOException {
		String title3 = "";
		try {
			InputStream is = new URL(
					"https://api.themoviedb.org/3/movie/" + movieId + "?api_key=2a1b0dfe58587a98b4a5ed3a2e9d35d1")
					.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuilder sbuilder = new StringBuilder();

			while ((line = br.readLine()) != null) {
				sbuilder.append(line);
			}

			line = sbuilder.toString();
			JsonObject gsonObj = new Gson().fromJson(line.toString(), JsonObject.class);

			String title = gsonObj.getAsJsonObject().get("title").getAsString();
			String overview = gsonObj.getAsJsonObject().get("overview").getAsString();
			int tmdbId = gsonObj.getAsJsonObject().get("id").getAsInt();
			String language = gsonObj.getAsJsonObject().get("original_language").getAsString().toUpperCase();
			title3 = title;

			int genreId = gsonObj.getAsJsonArray("genres").get(0).getAsJsonObject().get("id").getAsInt();

			addLanguages(language);

			String releaseDate = gsonObj.getAsJsonObject().get("release_date").getAsString();
			String[] parsedReleaseYear = releaseDate.split("-", 2);
			String releaseYear = parsedReleaseYear[0];
			int year = Integer.parseInt(releaseYear);
			addYears(year);

			boolean duplicateFound = ApiParser.checkDuplicate(movieRepo, tmdbId);

			if (duplicateFound == false) {
				movieRepo.save(new Movie(title, overview, publishYearRepo.findByName(year), tmdbId,
						languageRepo.findByName(language), genreRepo.findBytmdbid(genreId)));
			} else {
				return "1";
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println('2');
			return "0";
		}
		return title3;
	}

	// Looks if the language is in the database. If not, adds it.
	public void addLanguages(String language) throws IOException {
		Language a = languageRepo.findByName(language);

		if (a == null) {
			languageRepo.save(new Language(language));
		}
	}

	// Looks if the year is in the database. If not, adds it.
	public void addYears(int releaseYear) throws IOException {
		PublishYear a = publishYearRepo.findByName(releaseYear);

		if (a == null) {
			publishYearRepo.save(new PublishYear(releaseYear));
		}
	}
}
