/**
 * dotProduct method first translate the rating from 0 to 10 to -5 to 5 
 * and then return the dot product of the ratings of the movies they have both have ratings for.
 * 
 * 
 * In getSimilarities method we return an ArrayList of Ratings where item is raters IDs and value is the result of
 * dotProduct method which is similarity score. We only include the products that are non negative.
 * The result is sorted from highest to lowest. 
 * 
 * 
 * getAverageRatings method returns an ArrayList of Ratings again, but here the item is the movie id and 
 * the value is the weighted average. We only consider numSimilarRaters number of raters ArrayList. 
 * For a movie to be eligible to be in the return array, it should have at least minRaters number
 * of rating amongst top raters (first numSimilarRaters). The weighted average is: multiplying the 
 * similarity score and the rating each rater has for that movie. Then we divide the summation of all 
 * such multiplications by the number of raters.  
 * 
 * 
 * @author Mehrnaz
 */




import java.util.ArrayList;
import java.util.Collections;

public class ExclusiveAverage implements AverageRating{
	
	private int minRaters;
	private int numSimilarRaters;
	private ArrayList<Rating> similarityList;
	private String id;
	private Filter f;
	private int top;
	
	public ExclusiveAverage(int minRaters, int numSimilarRaters, String id, int top, Filter f) {
		
		this.minRaters = minRaters;
		this.numSimilarRaters = numSimilarRaters;
		this.id = id;
		this.f = f;
		this.top = top;
	}
	
	/*This function calculate the similarity score between "me" and another rater*/
	private double dotProduct(Rater me, Rater other) {
		
		ArrayList<String> myRatings = me.getItemsRated();
		ArrayList<String> otherRatings = other.getItemsRated();
		ArrayList<String> similarRatings = new ArrayList<>();
		
		/*First we find the shared movie IDs between these two raters*/
		for(String movieId: myRatings) {
			if(otherRatings.contains(movieId)) {
				similarRatings.add(movieId);
			}
		}
		double res = similarRatings.stream().mapToDouble(r -> (me.getRating(r)-5)*(other.getRating(r)-5)).sum();		
		return res;
	}
	
	/*This function calculates the similarity score of all raters comparing to a specific rater.
	 * The list is reverse sorted.
	 * */
	private void getSimilarities(){
		
		ArrayList<Rater> ratersList = RaterDatabase.getRaters();
		similarityList = new ArrayList<>();
		
		for(Rater rater: ratersList) {
			
			//We don't want to compare the rater that its id is the parameter to this method with itself.
			if(!rater.getID().equals(id)) {
				double dotProductResult = dotProduct(RaterDatabase.getRater(id), rater);
				if(dotProductResult > 0) {
					similarityList.add(new Rating(rater.getID(),dotProductResult));
				}
			}
		}
		Collections.sort(similarityList,Collections.reverseOrder());
	}
	
	/*Number of rating for each movie should be at least minRaters and it should be from top numSimilarRaters */ 
	public int getNumRatingPerMovie(String movieId) {
		
		int count = 0;
		
		for(int i = 0; i < numSimilarRaters; i++) {
			String raterId = similarityList.get(i).getItem();
			if(RaterDatabase.hasRating(raterId, movieId)) {
				count++;
			}
			
		}
		return count;
	}
	
	public ArrayList<Rating> getAverageRatings(){
		
		getSimilarities();
		ArrayList<String> movieList = MovieDatabase.filterBy(f);
		ArrayList<Rating> weightedAvgMovies = new ArrayList<>();
		
		if(numSimilarRaters > similarityList.size()) {
			System.out.println("There are less similar raters to you than you specified. exit....");
			System.exit(0);
		}
		for(String movieId: movieList) {
			
			double sum  = 0.0;
			int numRaters = getNumRatingPerMovie(movieId);
			if( numRaters >= minRaters) {
				
				for(int i = 0; i < numSimilarRaters; i++) {
					
					String raterId = similarityList.get(i).getItem();
					if(RaterDatabase.hasRating(raterId , movieId)) {
						
						double rating = RaterDatabase.getRating(raterId, movieId);
						double similarityScore = similarityList.get(i).getValue();
						
						sum += rating * similarityScore;
					}
				}
				
				double avg = sum/numRaters;
				weightedAvgMovies.add(new Rating(movieId,avg));
			}
		}
		if(weightedAvgMovies.isEmpty()) {
			System.out.println("There are no exclusive recommendation for you, please try general recommendation! exit...");
			System.exit(0);
		}
		Collections.sort(weightedAvgMovies,Collections.reverseOrder());
		return weightedAvgMovies;
	}
	
	/*It prints top number of ratings*/
	public void printRatings() {
		
		ArrayList<Rating> averageList = getAverageRatings();
		
		if(top > averageList.size()) {
			top = averageList.size();
			System.out.println("There are less movies than you specified. " + "Top " + top +  " movies are: ");
		}
		else{System.out.println("Top " + top +  " movies are: ");}
		for(int i = 0; i < top; i++) {
			Rating rating = averageList.get(i);
			System.out.printf("%.1f", rating.getValue());
			System.out.print(":" + MovieDatabase.getTitle(rating.getItem()));
			System.out.print("\n");
		}
	}
	
}
