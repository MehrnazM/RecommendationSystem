import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream; 

public class GeneralAverage implements AverageRating{
	
	private int minRaters;
	private Filter f;
	private int top;
	
	public GeneralAverage(int minRaters, Filter f, int top) {
		this.minRaters = minRaters;
		this.f = f;
		this.top = top;
	}
	
	/* We need the number of ratings per movie to obtain the average rating*/
	public int getNumRatingPerMovie(String movieId) {
		
		int count = 0;
		int numOfRaters = RaterDatabase.size();
		//Rating file is sorted based on raters IDs. If the number of raters is 10, then the raters IDs are: 1,2,3,4,...,10
		for(int i = 1; i <= numOfRaters; i++) {
			if(RaterDatabase.hasRating(Integer.toString(i), movieId)) {
				count++;
			}
		}
		return count;
	}
	
	/* Each movie should have at least minimalRaters number of ratings 
	 so then the average rating is reliable*/
	
	public double getAverageByID(String movieId) {
		
		if(getNumRatingPerMovie(movieId) >= minRaters) {
			
			int numOfRaters = RaterDatabase.size();
			
			Double sum = IntStream.range(1, numOfRaters+1).filter(x -> RaterDatabase.hasRating(Integer.toString(x), movieId)).
					mapToDouble(x -> RaterDatabase.getRating(Integer.toString(x), movieId)).sum();
			
			return sum/(double)getNumRatingPerMovie(movieId);
		}
		
		return -1;
	}
	
	/* This function returns a list of all movies that have reliable average rating and filtered by f*/
	public ArrayList<Rating> getAverageRatings() {
		
		ArrayList<Rating> averageRatingList = new ArrayList<Rating>();
		ArrayList<String> movieIDs = MovieDatabase.filterBy(f);		
		for(String id: movieIDs) {
			double avg = getAverageByID(id);
			if(avg != -1) {
				averageRatingList.add(new Rating(id, avg));
			}
		}
		Collections.sort(averageRatingList,Collections.reverseOrder());
		return averageRatingList;
	}
	
	public void printRatings() {
		
		/*Printing all eligible movie sorted by their average ratings*/
		ArrayList<Rating> ratedMovies = getAverageRatings();
		if(top > ratedMovies.size()) {
			top = ratedMovies.size();
			System.out.println("There are less movies than you specified. " + "Top " + top +  " movies are: ");
		}
		else{System.out.println("Top " + top +  " movies are: ");}
		for(int i = 0; i < top; i++) {
			
			Rating rating = ratedMovies.get(i);
			System.out.printf("%.1f", rating.getValue());
			System.out.print(":" + MovieDatabase.getTitle(rating.getItem()));
			System.out.print("\n");
		}
		
	}
	
}
