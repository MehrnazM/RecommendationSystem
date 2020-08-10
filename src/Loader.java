/**
 * We use this class to initialize the the rater and the movie databases
 * 
 * @author Mehrnaz
 */

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.apache.commons.csv.*;

public class Loader {
	
	private ArrayList<Movie> loadedMovies;
	private ArrayList<Rater> raters;
	
	public Loader() {
		loadedMovies = new ArrayList<Movie>();
		raters = new ArrayList<Rater>();
	}
	
	/*Loading a movies CSV file into loadedMovies ArrayList*/
	public void loadMovies(String movieFile) throws IOException  { 
		Path filePath = Paths.get("./data/" + movieFile);
		Reader reader = Files.newBufferedReader(filePath);
		
		/*The CSV file has a header in this order: id,title,year,country,genre,director,minutes,poster*/
		
		CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
		for(CSVRecord record: parser) {
			
			Movie movie = new Movie(record.get("id"), record.get("title"), record.get("year"),
					record.get("genre"),  record.get("director"), record.get("country"),
					record.get("poster"), Integer.parseInt(record.get("minutes")));
			loadedMovies.add(movie);
		}
		parser.close();
		
	}
	
	/*Loading a rater CSV file into raters ArrayList*/
	public void loadRaters(String ratingsFile) throws IOException  { 
		Path filePath = Paths.get("./data/" + ratingsFile);
		Reader reader = Files.newBufferedReader(filePath);
		
		/*The CSV file has a header in this order: rater_id,movie_id,rating,time*/
		
		CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
		int index = 0;
		
		/* the raters ids are sorted*/
		for(CSVRecord record: parser) {
			
			if(!raters.isEmpty()) {
				if(record.get("rater_id").equals(raters.get(index).getID())) {
					raters.get(index).addRating(record.get("movie_id"), 
							Double.parseDouble(record.get("rating")));
				}
				else {
					Rater rater = new Rater(record.get("rater_id"));
					rater.addRating(record.get("movie_id"), Double.parseDouble(record.get("rating")));
					raters.add(rater);
					index++;
				}
			} 
			else {
				Rater rater = new Rater(record.get("rater_id"));
				rater.addRating(record.get("movie_id"), Double.parseDouble(record.get("rating")));
				raters.add(rater);
			}
			
		}
		parser.close();
	}
	
	public ArrayList<Movie> getMovieList(){
		return loadedMovies;
	}
	
	public ArrayList<Rater> getRatersList(){
		return raters;
	}
	
}
