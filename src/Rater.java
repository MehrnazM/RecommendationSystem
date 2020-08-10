/**
 * This is rater class. Each rater has an id and a map of movies that this rater has a rating for and the value of the rating. 
 * In all methods, item refers to the movie id
 * 
 * @author Mehrnaz 
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Rater{
    private String myID;
    private HashMap<String,Rating> ratingMap;
    
    //each rater has a id and a map of movies that she has rated
    public Rater(String id) {
        myID = id;
        ratingMap = new HashMap<>();
    }
    
   
    //the key in the rating hashmap is a movie id and the value is a rating object
    public void addRating(String item, double rating) {
        ratingMap.put(item, new Rating(item,rating));
    }

    //to check if this rater has any rating for this movie (item is a movie id)
    public boolean hasRating(String item) {
       
        return ratingMap.containsKey(item);
    }

    //the returned id is the rater's id
    public String getID() {
        return myID;
    }

    
    public double getRating(String item) {
        
    	if(ratingMap.containsKey(item)) {
    		return ratingMap.get(item).getValue();
    	}
        return -1;
    }

    public int numRatings() {
        return ratingMap.size();
    }

    //the returned list is the ids of all movies that this rater has rated
    public ArrayList<String> getItemsRated() {
    	
        return new ArrayList<String>(ratingMap.keySet());
    }
}

