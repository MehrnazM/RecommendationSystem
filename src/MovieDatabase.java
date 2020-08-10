/**
 * All methods and fields in this database are static. 
 * You can extract a list of filtered data with filterBy method
 * We use Loader class to initialize the database.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class MovieDatabase {
	
	//The keys are movie ids and the values are movie objects
    private static HashMap<String, Movie> movieMap;

    public static void initialize(String moviefile){
        if (movieMap == null) {
            movieMap = new HashMap<String,Movie>();
            loadMovies(moviefile);
        }
    }

	
    private static void loadMovies(String filename){
    	Loader load = new Loader();
    	try {
			load.loadMovies(filename);
		} 
    	catch (IOException e1) {
			
    		System.out.println("The movie source is not loaded! Exit...");
			System.exit(0);
		}
    	ArrayList<Movie> list = load.getMovieList();
    	for (Movie m : list) {
    		movieMap.put(m.getID(), m);
    	}
    }
    
    /*Checking if the database has been initialized*/
    private static void check() {
    	if(movieMap ==null) {
    		System.out.println("Please initialize the MovieDatabase.");
    		System.exit(0);
    	}
    }
    
    public static int getYear(String id){
    	check();
        return movieMap.get(id).getYear();
    	
    }

    public static String getGenres(String id){
    	check();
        return movieMap.get(id).getGenres();
    }

    public static String getTitle(String id){
    	check();
        return movieMap.get(id).getTitle();
    }

    public static Movie getMovie(String id) {
    	check();
        return movieMap.get(id);
    }

    public static String getPoster(String id) {
    	check();
        return movieMap.get(id).getPoster();
    }

    public static int getMinutes(String id) {
    	check();
        return movieMap.get(id).getMinutes();
    }

    public static String getCountry(String id) {
    	check();
        return movieMap.get(id).getCountry();
    }

    public static String getDirector(String id) {
    	check();
        return movieMap.get(id).getDirector();
    }

    public static int size() {
    	check();
        return movieMap.size();
    }

    public static ArrayList<String> filterBy(Filter f){
    	check();
        ArrayList<String> list = new ArrayList<String>();
        for(String id : movieMap.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }
        
        return list;
    }
    

}
