package in.espirit.tracer.action;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/autocomplete")
public class AutocompleteActionBean extends BaseActionBean {

	private String q = "a";

	private static final List<String> cities = Arrays.asList("Aberdeen", "Ada", "Adens","Sana");

	// Getters and Setters

	/**
	 * Forwards the user to the example page.
	 * 
	 * @return forward to the jsp
	 */
	@DefaultHandler
	public Resolution main() {
		System.out.println("Main");
		return new ForwardResolution("/WEB-INF/autocomplete.jsp");
	}

	/**
	 * Returns the city's list for the autocomplete drop down.
	 * 
	 * @return a StreamingResolution with the city's list
	 */
	public Resolution autocomplete() {
		System.out.println("Autocomplete");
		StringBuilder results = new StringBuilder();

		if (q != null) {
			System.out.println("building list");
			for (String city : cities) {
				if (city.indexOf(q) != -1) {
					results.append(city).append("\n");
				}
			}
		}
		
		System.out.println("List "+results.toString());
		return new StreamingResolution("text/plain", results.toString());
	}
}