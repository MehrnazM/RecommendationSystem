/**
 * The input of the filter is one string which contains one or several director names. 
 * The names are separated with ",". The purpose of the filter is to extract the movies
 * that are directed by at least have one of these directors.
 * @author Mehrnaz
 *
 */

public class DirectorsFilter implements Filter {
	
	String[] director;
	
	public DirectorsFilter(String directors) {
		
		director = directors.split(",");
	}
	
	@Override
	public boolean satisfies(String id) {
		
		String directors = MovieDatabase.getDirector(id);
		for(int i = 0; i < director.length; i++) {
			if(directors.contains(director[i])) {
				return true;
			}
		}
		return false;
	}

}