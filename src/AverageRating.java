/**
 * This is an interface of obtaining the average ratings. There are two types:
 * 1. General
 * 2. Exclusive(Weighted average rating)
 * 
 * @author Mehrnaz
 */

import java.util.ArrayList;

public interface AverageRating {
	
	public int getNumRatingPerMovie(String movieId);
	public ArrayList<Rating> getAverageRatings(); 
	public void printRatings();
}
