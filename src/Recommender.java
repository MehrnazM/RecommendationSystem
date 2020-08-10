import java.util.Scanner;

/**
 * This is the recommender abstract class. In this project we have only two subclasses: 
 * 
 * 1. General: This recommender only shows top rating movies and does not ask for your 
 * 	ratings to show the movies that are close to your taste.
 * 
 * 2. Exclusive: This recommender first asks you to rate some movies and then shows top rating movies
 * 	that are picked for you.
 * 
 * @author Mehrnaz
 *
 */



public abstract class Recommender {
	
	protected int minRaters;
	protected int top;
	
	protected void greetings() {
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("How many recommendation you want us to show you?");
		String topMovies = in.next();
		try {
			 top = Integer.parseInt(topMovies); 
		}catch(NumberFormatException e) {
			System.out.println("input is not an int value, exit..."); 
			System.exit(0);
		}
		
		System.out.println("How many ratings at least each movie should have?");
		String min = in.next();
		try {
			minRaters = Integer.parseInt(min); 
		}catch(NumberFormatException e) {
			 System.out.println("input is not an int value, exit..."); 
			 System.exit(0);
		}
		
		//in.close();
	}
	
	protected Filter getFilters() {
		
		Scanner in = new Scanner(System.in);
		AllFilters filters = new AllFilters();
		
		System.out.println("Would you like to add any filters?(yes or no)");
		String filterChoice = in.nextLine();
		if (!filterChoice.equals("no") && !filterChoice.equals("yes")) {
			System.out.println("Please press only 'yes' or 'no'");
			filterChoice = in.next();
		}
		
		if(filterChoice.equals("no")) {
			
			filters.addFilter(new TrueFilter());
			//in.close();
			return filters;
		}
		else if(filterChoice.equals("yes")) {
			
			System.out.println("Do you have any genre in mind?(Enter a genre or no)");
			String genre  = in.next();
			if(!genre.equals("no")) {
				filters.addFilter(new GenreFilter(genre));
			}
			
			System.out.println("Do you want to see recommendations for movies after a specific year?(Enter a year or no)");
			String year = in.next();
			if(!year.equals("no")) {
				int intYear = 0;
				try{
					intYear = Integer.parseInt(year); 
					filters.addFilter(new YearAfterFilter(intYear));
				}catch(NumberFormatException e) {
					System.out.println("input is not an int value, exit..."); 
					System.exit(0);
				}
				
			}
			
			System.out.println("Do you have a minimum length in mind?(Enter minimum length in minute or no) ");
			String minute = in.next();
			if(!minute.equals("no")) {
				int intMinute = 0;
				try{
					intMinute = Integer.parseInt(minute); 
					filters.addFilter(new MinutesFilter(intMinute));
				}catch(NumberFormatException e) {
					System.out.println("input is not an int value, exit..."); 
					System.exit(0);
				}
			}
			
			System.out.println("And Finaly do you want to see recommendations from a director or directors?(Enter the name of the directors with no space and comma in between or no)");
			String director = in.next();
			if(!director.equals("no")) {
				filters.addFilter(new DirectorsFilter(director));
			}
			
			//in.close();
			return filters;
		}
		
		else {
			System.out.println("What you entered is not defined. exit...");
			//in.close();
			System.exit(0);
			return null;
		}
	}
	
	abstract public void showRecommendation();
	
}
