/**
 * Filtered data have length between minMinutes and maxMinutes. Length is based on minute.
 * @author Mehrnaz
 *
 */

public class MinutesFilter implements Filter {
	
	private int minMinutes;
	
	public MinutesFilter(int minMinutes) {
		
		this.minMinutes = minMinutes;
	}
	@Override
	public boolean satisfies(String id) {
		int movieLength = MovieDatabase.getMinutes(id);
		if(movieLength >= minMinutes) {
			return true;
		}
		return false;
	}

}
