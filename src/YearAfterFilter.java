
// To extract movies that are produced after a specific year
public class YearAfterFilter implements Filter {
	private int year;
	
	public YearAfterFilter(int year) {
		this.year = year;
	}
	
	@Override
	public boolean satisfies(String id){
		return MovieDatabase.getYear(id) >= year;
	}

}
