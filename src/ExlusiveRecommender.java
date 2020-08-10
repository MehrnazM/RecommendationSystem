import java.util.ArrayList;
import java.util.Scanner;

public class ExlusiveRecommender extends Recommender{
	
	
	/*This function returns movieToRate number of top rated movies*/
	private  ArrayList<String> getItemsToRate(int movieToRate,Filter f) {
		
		//We need a list of top rated movies
		AverageRating general = new GeneralAverage(minRaters, f, top);
		ArrayList<Rating> generalAverage = general.getAverageRatings();
		ArrayList<String> allMovies = MovieDatabase.filterBy(f);
		int size = allMovies.size();
		ArrayList<String> retMovies = new ArrayList<>();
		if(size < movieToRate) {
			System.out.println("The number of movies to rate that you specified is larger than filtered list. This number is adjusted");
			movieToRate = size;
		}
		
		for(int i = 0; i < movieToRate; i++) {
			
			String movieId = generalAverage.get(i).getItem();
			retMovies.add(movieId);
		}
		
		return retMovies;
	}
	
	/*We ask the user to rate some movies to calculate the similarity score list*/
	public String getRating(ArrayList<String> movieList) {
		
		Scanner in = new Scanner(System.in);
		int id = RaterDatabase.size() + 1;
		Rater rater = new Rater(String.valueOf(id));
		System.out.println("Please enter your rating for each movie: ");
		
		for(String movieId: movieList) {
			System.out.println(MovieDatabase.getTitle(movieId) + ":");
			String rating = in.nextLine();
			double doubleRating = 0.0;
			try {
				doubleRating = Double.parseDouble(rating); 
			}catch(NumberFormatException e) {
				System.out.println("input is not an double value, exit..."); 
				System.exit(0);
			}
			rater.addRating(movieId, doubleRating);
		}
		
		RaterDatabase.addRater(String.valueOf(id), rater);
		in.close();
		return String.valueOf(id);
	}
	
	@Override
	public void showRecommendation(){
		
		Scanner in = new Scanner(System.in);
		int numSimilarRaters = 0; int movieToRate = 0;
		
		greetings();
		System.out.println("How many top raters you want to compare with yourself?");
		String similarRaters = in.next();
		try {
			numSimilarRaters = Integer.parseInt(similarRaters); 
		}catch(NumberFormatException e) {
			System.out.println("input is not an int value, exit..."); 
			System.exit(0);
		}
		
		System.out.println("How many movies you want to rate?");
		String rating = in.next();
		try {
			movieToRate = Integer.parseInt(rating); 
		}catch(NumberFormatException e) {
			System.out.println("input is not an int value, exit..."); 
			System.exit(0);
		}
		
		Filter f = getFilters();
		String id = getRating(getItemsToRate(movieToRate,f)); //This id is a new id belong to the user
		AverageRating average = new ExclusiveAverage(minRaters, numSimilarRaters, id, top, f);
		average.printRatings();
		in.close();
	}
}
