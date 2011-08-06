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

	public static ArrayList<Activity> getActivities(String count, String userName, String activityDate) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Activity> result = new ArrayList<Activity>();

		String query="";

		query = "SELECT f_timeStamp, f_activity FROM t_activity";
			

		if (userName != null || activityDate != null) {
			query +=" WHERE ";			
		}
		
		if (userName != null) {
			query += " f_userName='" + userName + "'";
		}
		
		if (userName != null && activityDate != null) {
			query +=" AND";			
		}

		if (activityDate !=null) {
			query +=" date(f_timestamp)='" +  activityDate +"'";  // Date should be yyyy-mm-dd format for which the UI returns.
		}
		
		query +=" ORDER BY f_ID DESC";
		
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
			//throw new Exception(e.getMessage());

		} // catch Close

		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

		return changeLinks(result);
	}
	
	public static ArrayList<Activity> getRecentActivities() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Activity> result = new ArrayList<Activity>();

		String query="";

		query = "SELECT f_timeStamp, f_activity FROM t_activity ORDER BY f_ID DESC LIMIT 5";
		
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
			logger.error("Getting recent activities failed with " + e.getMessage());
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

		return changeLinks(result);
	}

	private static ArrayList<Activity> changeLinks(ArrayList<Activity> result) {
		if (!result.isEmpty()) {
			for(Activity a : result) {
				String s1 = a.getActivity();
				// This part can be improved or optimized for better practices.
				Integer pos = s1.lastIndexOf("#");  // This is to determine whether the activity has a number associated with it.
				if (pos > 0) {
					// Pattern will be <username> has <action> <tickettype> #<no.>. Action can be created, edited or commented.
					String[] a1 = s1.split(" "); 				
					String id = "";
					String link = "";			
					if(a1.length==5){
						id = s1.substring(pos);				
						link = "<a href='./" + a1[3] + "/" + id.substring(1)  + "'>" + id + "</a>";	
						a.setActivity(a1[0] + " " + a1[1] + " " + a1[2] + " " + a1[3] + " " + link);
					}
					else {
						id = s1.substring(pos, s1.substring(pos, s1.length()).indexOf(" ") + pos);
						link =  "<a href='./";
						if (s1.indexOf("defect") > 0) {
							link +=  "defect";		
						}
						else if (s1.indexOf("task") > 0) {
							link +=  "task";	
						}
						else if (s1.indexOf("requirement") > 0) {
							link +=  "requirement";	
						}
						link +=  "/" + id.substring(1)  + "'>" + id + "</a>";	
						a.setActivity(s1.replaceFirst(id, link));					
					}			
				}		
			}	
		}
		return result;	
	}

}
