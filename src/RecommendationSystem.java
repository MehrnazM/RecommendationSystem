import java.util.Scanner;

public class RecommendationSystem {
	
	public static void main(String[] args){
		
		String movieFile = "ratedmoviesfull.csv";
		String raterFile = "ratings.csv";
		MovieDatabase.initialize(movieFile);
		RaterDatabase.initialize(raterFile);
		
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome To Our Recommendation System!");
		System.out.println("Would you like a general recommendation (enter g) or exclusive recommendation (enter e)?");
		String choice = in.next();
		
		if (!choice.equals("g") && !choice.equals("e")) {
			System.out.println("Please press only 'g' or 'e'");
			choice = in.next();
		}
		
		if(choice.equals("g")) {
			Recommender gr = new GeneralRecommender();
			gr.showRecommendation();
		}
		if(choice.equals("e")) {
			Recommender er = new ExlusiveRecommender();
			er.showRecommendation();
		}
		in.close();	
			
			
		
	}
}
