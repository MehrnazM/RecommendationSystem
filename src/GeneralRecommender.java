
/*This recommender only asks general questions and ask to add filter and print a general rating*/
public class GeneralRecommender extends Recommender{
	
	public void showRecommendation() {
		
		greetings();
		Filter f = getFilters();
		AverageRating average = new GeneralAverage(minRaters, f, top);
		average.printRatings();
	}
}
