/**
 * All methods and fields in this database are static.
 * We use Loader class to initialize the database.
 * 
 * @author Mehrnaz
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class RaterDatabase {
	
	private static HashMap<String, Rater> raterMap;
	
	public static void initialize(String raterFile){
        if (raterMap == null) {
            raterMap = new HashMap<String,Rater>();
            loadRaters(raterFile);
        }
    }
	
	private static void loadRaters(String filename){
    	Loader load = new Loader();
    	try {
			load.loadRaters(filename);;
		} 
    	catch (IOException e1) {
			
    		System.out.println("The rater source is not loaded! Exit...");
			System.exit(0);
		}
    	ArrayList<Rater> list = load.getRatersList();
    	for (Rater r : list) {
    		raterMap.put(r.getID(), r);
    	}
    }
	
	/*Checking if the database has been initialized*/
	private static void check() {
    	if(raterMap ==null) {
    		System.out.println("Please initialize the RaterDatabase.");
    		System.exit(0);
    	}
    }
	
	public static void addRater(String id, Rater rater) {
		raterMap.put(id, rater);
	}
	public static int size() {
		check();
        return raterMap.size();
    }
	
	/*Check if this rater has a rating for this movie*/
	public static boolean hasRating(String id, String item) {
		check();
		return raterMap.get(id).hasRating(item);
	}
	
	/*Get the ratings for this rater*/
	public static double getRating(String id, String item) {
		check();
		return raterMap.get(id).getRating(item);
	}
	
	/*Get the rater with this id*/
	public static Rater getRater(String id) {
    	
    	check();
    	return raterMap.get(id);
    }
	
	public static ArrayList<String> getRatings(String id){
		check();
		return raterMap.get(id).getItemsRated();
	}
	/*Get a list of all raters in the database*/
	public static ArrayList<Rater> getRaters() {
		
		check();
    	ArrayList<Rater> list = new ArrayList<Rater>(raterMap.values());
    	return list;
    }

}
