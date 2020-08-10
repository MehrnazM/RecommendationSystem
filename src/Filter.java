/**
 * This is an interface for all possible filters to apply on movie database
 */

public interface Filter {
	public boolean satisfies(String id);
}
