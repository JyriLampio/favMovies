package favMovies.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.http.HttpClient.Redirect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@ConfigurationProperties
public class ApiParser {
	
	//Adds all genres to database
	public String addInitialGenres(GenreRepo genreRepo) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("/home/jyri/Documents/Projects/Eclipse-workspace/favMoviesTMDB/src/main/resources/static/genrelist.json"));
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
	 String returnLink(int id) {
		String url = "https://www.themoviedb.org/movie/" + id;
		return url;		
	}

	// Adds sample movies to database
	public String addInitialMovies(MovieRepo movieRepo, LanguageRepo languageRepo, GenreRepo genreRepo, PublishYearRepo publishYearRepo) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("/home/jyri/Documents/Projects/Eclipse-workspace/favMoviesTMDB/src/main/resources/static/movielist.json"));
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
			String link =  returnLink(jsonArray.get(i).getAsJsonObject().get("id").getAsInt());
			String language = jsonArray.get(i).getAsJsonObject().get("original_language").getAsString().toUpperCase();
			int genreId = jsonArray.get(i).getAsJsonObject().get("genre_ids").getAsJsonArray().get(0).getAsInt();
			
			String releaseDate = jsonArray.get(i).getAsJsonObject().get("release_date").getAsString();
			String[] parsedReleaseYear = releaseDate.split("-", 2);
			String releaseYear = parsedReleaseYear[0];
			int year = Integer.parseInt(releaseYear);

			addLanguages(languageRepo, language);
			addYears(publishYearRepo, year);
			
			movieRepo.save(new Movie(title, overview, publishYearRepo.findByName(year), link, languageRepo.findByName(language), genreRepo.findBytmdbid(genreId)));

		}
		return line;
	}
	// Add movie to database with The Movie Database's movie ID.
	public static String AddMovie(MovieRepo movieRepo, LanguageRepo languageRepo, GenreRepo genreRepo, PublishYearRepo publishYearRepo, int movieId) throws IOException {
		String title3 = "";
		try {
			InputStream is = new URL("https://api.themoviedb.org/3/movie/"+movieId+"?api_key=2a1b0dfe58587a98b4a5ed3a2e9d35d1").openStream();
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
			String link =  "https://www.themoviedb.org/movie/" + gsonObj.getAsJsonObject().get("id").getAsString();
			String language = gsonObj.getAsJsonObject().get("original_language").getAsString().toUpperCase();
			title3 = title;

			int genreId = gsonObj.getAsJsonArray("genres").get(0).getAsJsonObject().get("id").getAsInt();
			
			ApiParser.addLanguages(languageRepo, language);
			
			String releaseDate = gsonObj.getAsJsonObject().get("release_date").getAsString();
			String[] parsedReleaseYear = releaseDate.split("-", 2);
			String releaseYear = parsedReleaseYear[0];
			int year = Integer.parseInt(releaseYear);
			ApiParser.addYears(publishYearRepo, year);
			
			movieRepo.save(new Movie(title, overview, publishYearRepo.findByName(year), link, languageRepo.findByName(language), genreRepo.findBytmdbid(genreId)));

		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println('2');
			return "0";
		}
		return title3;
	}
	
	//Looks if the language is in the database. If not, adds it.
	public static void addLanguages(LanguageRepo languageRepo, String language) throws IOException {
		Language a = languageRepo.findByName(language);
		
		if (a == null) {
			languageRepo.save(new Language(language));
		}
	}
	
	//Looks if the year is in the database. If not, adds it.
	public static void addYears(PublishYearRepo publishYearRepo, int releaseYear) throws IOException {
		PublishYear a = publishYearRepo.findByName(releaseYear);
		
		if (a == null) {
			publishYearRepo.save(new PublishYear(releaseYear));
		}
	}
}
