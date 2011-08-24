package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.UserDao;

import java.util.List;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/autocomplete")
public class AutocompleteActionBean extends BaseActionBean {

	@SuppressWarnings("static-access")
	public Resolution getUsers() {
		String start = this.getContext().getRequest().getParameter("term");
		StringBuilder results = new StringBuilder();
		String result = "";
		UserDao userdao = new UserDao();
		List<String> userlist;
		try {
			userlist = userdao.getUserNames();
			results.append("[");
			for (String user : userlist) {
				if (user.indexOf(start) != -1) {
					results.append("{\"value\":\"" + user + "\"},")
							.append("\n");
				}
			}

			// The format for JSON. only value or Name and Value
			// results.append("["+
			// "{\"value\":\"jinesh\"},"+
			// "{\"name\":\"Minu\",\"value\":\"minu\"},"+
			// "]");

			result = results.substring(0, results.length() - 2); // removing the trailing comma
			result += "]";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new StreamingResolution("text/plain", result);
	}
}