package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.Activity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class ActivityDao {
	
	private static Logger logger = Logger.getLogger(TicketDao.class.getName());
	
	
public static void registerActivity(Activity activity) throws Exception {
		
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		
		String query = "INSERT INTO t_activity (f_username, f_timeStamp, f_activity) VALUES('" + activity.getUserName() + "','" + activity.getTimeStamp() + "','" + activity.getActivity() +"')";

		try {
			st = con.createStatement();
			st.executeUpdate(query);

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("registration of activity failed with " + e.getMessage());
			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());

		} // catch Close

		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	
	}

public static ArrayList<Activity> getActivities(String count) throws Exception{
	ConnectionPool pool = ConnectionFactory.getPool();
	Connection con = pool.getConnection();
	Statement st = null;
	ResultSet rs = null;
	ArrayList<Activity> result = new ArrayList<Activity>();
	
	String query="";
	
	query = "SELECT f_timeStamp, f_activity FROM t_activity ORDER BY f_ID DESC";
	
	if (!count.equalsIgnoreCase("all")) {
		query += " LIMIT " + count;
	}
	
	
	try {
		st = con.createStatement();
		rs = st.executeQuery(query);
			
		while (rs.next()) {
			Activity a = new Activity();
			a.setTimeStamp(rs.getString(1));
			a.setActivity(rs.getString(2));
			result.add(a);
		}
		if (rs != null) {
		
			rs.close();
		}

		if (st != null) {
			st.close();
		}

	} catch (Exception e) {
		logger.error("Getting all activities failed with " + e.getMessage());
		if (rs != null) {
			rs.close();
		}

		if (st != null) {
			st.close();
		}
		//throw new Exception(e.getMessage()+query+"-SELECTED QUERY>-"+selQuery+"-STATUS>-"+status+"-USERNAME>-"+UserName+"-PRIORITY-"+priority);
		throw new Exception(e.getMessage());

	} // catch Close

	finally {
		if (con != null)
			con.close(); // close connection		
	}// end finally	
	
	if (!result.isEmpty()) {
		for(Activity a : result) {
			String s1 = a.getActivity();
			// This part can be improved or optimized for better practices.
			Integer pos = s1.lastIndexOf("#");  // This is to determine whether the activity has a number associated with it.
			if (pos > 0) {
				// Pattern will be <username> has <action> <tickettype> #<no.>. Action can be created, edited or commented.
				String[] a1 = s1.split(" ");  
				String id = s1.substring(pos);				
				String link = "<a href='./" + a1[3] + "/" + id.substring(1)  + "'>" + id + "</a>";	
				a.setActivity(a1[0] + " " + a1[1] + " " + a1[2] + " " + a1[3] + " " + link);				
			}		
		}	
	}
	return result;
}


}
