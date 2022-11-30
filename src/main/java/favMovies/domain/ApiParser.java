package favMovies.domain;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import favMovies.domain.Genre;
import favMovies.domain.GenreRepo;
import net.minidev.json.writer.JsonReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ApiParser {

	
	public static void addGenres(GenreRepo genreRepo) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("/home/jyri/Documents/Projects/Eclipse-workspace/favMoviesTMDB/src/main/resources/static/genrelist.json"));
		String line;
		StringBuilder sbuilder = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sbuilder.append(line);
		}

		line = sbuilder.toString();
		JsonObject gsonObj = new Gson().fromJson(line.toString(), JsonObject.class);

		/*
		 * //Code for a single json entry JsonObject jsonOssbj =
		 * JsonParser.parseString(line).getAsJsonObject();
		 * System.out.println("PRinting  " + gsonObj.toString()); String name =
		 * gsonObj.getAsJsonObject("genres").get("name").getAsString(); String stringId
		 * = gsonObj.getAsJsonObject("genres").get("id").getAsString(); int id =
		 * Integer.parseInt(stringId); genreRepo.save(new Genre(id, name));
		 */

		JsonArray jsonArray = gsonObj.getAsJsonArray("genres");
		for (int i = 0; i < jsonArray.size(); i++) {

			String name = jsonArray.get(i).getAsJsonObject().get("name").getAsString();
			int Id = jsonArray.get(i).getAsJsonObject().get("id").getAsInt();
			genreRepo.save(new Genre(Id, name));
		}
	}
	static String returnLink(int id) {
		String url = "https://www.themoviedb.org/movie/" + id;
		return url;		
	}
	
	static String returnLinks(int id) {
		String url = "https://www.themoviedb.org/movie/" + id;
		return url;		
	}
	
	public static void addMovies(MovieRepo movieRepo, LanguageRepo languageRepo, GenreRepo genreRepo, PublishYearRepo publishYearRepo) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("/home/jyri/Documents/Projects/Eclipse-workspace/favMoviesTMDB/src/main/resources/static/movielist.json"));
		String line;
		StringBuilder sbuilder = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sbuilder.append(line);
		}

		line = sbuilder.toString();
		JsonObject gsonObj = new Gson().fromJson(line.toString(), JsonObject.class);

		/*
		 * //Code for a single json entry JsonObject jsonOssbj =
		 * JsonParser.parseString(line).getAsJsonObject();
		 * System.out.println("PRinting  " + gsonObj.toString()); String name =
		 * gsonObj.getAsJsonObject("genres").get("name").getAsString(); String stringId
		 * = gsonObj.getAsJsonObject("genres").get("id").getAsString(); int id =
		 * Integer.parseInt(stringId); genreRepo.save(new Genre(id, name));
		 */
		JsonArray jsonArray = gsonObj.getAsJsonArray("items");
		for (int i = 0; i < jsonArray.size(); i++) {

			String title = jsonArray.get(i).getAsJsonObject().get("title").getAsString();
			String overview = jsonArray.get(i).getAsJsonObject().get("overview").getAsString();
			String link =  returnLink(jsonArray.get(i).getAsJsonObject().get("id").getAsInt());
			String language = jsonArray.get(i).getAsJsonObject().get("original_language").getAsString();
			String releaseDate = jsonArray.get(i).getAsJsonObject().get("release_date").getAsString();
			String[] parsedReleaseYear = releaseDate.split("-", 2);
			String releaseYear = parsedReleaseYear[0];
			int genreId = jsonArray.get(i).getAsJsonObject().get("genre_ids").getAsJsonArray().get(0).getAsInt();
			
			int year = Integer.parseInt(releaseYear);
			addCountries(languageRepo, language);
			addYears(publishYearRepo, year);
			
			movieRepo.save(new Movie(title, overview, publishYearRepo.findByName(year), link, languageRepo.findByName(language), genreRepo.findBytmdbid(genreId)));
			//System.out.println(language);
			//String stringId = jsonArray.get(i).getAsJsonObject().get("id").getAsString();
			//int id = Integer.parseInt(stringId);
			//movieRepo.save(new Movie(id, name));
		}
	}
	
	//Looks if the language is in the database. If not, adds it.
	public static void addCountries(LanguageRepo languageRepo, String language) throws IOException {
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
