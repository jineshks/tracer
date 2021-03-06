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
			
	public static ArrayList<Activity> getListingActivities(String userName, String fromDate, String toDate, Integer offset, Integer size) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Activity> result = new ArrayList<Activity>();

		String query="";
		String selQuery = "";

		query = "SELECT f_timeStamp, f_activity FROM t_activity";
		selQuery = formFilterQuery(userName, fromDate, toDate);
		//query += " ORDER BY f_ID DESC";
		query += selQuery + " ORDER BY f_ID DESC OFFSET " + offset + " LIMIT " + size;
	
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
	
	public static ArrayList<Activity> getRSSActivities(String date) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Activity> result = new ArrayList<Activity>();

		String query="";

		query = "SELECT f_timeStamp, f_activity, f_username FROM t_activity WHERE  date(f_timestamp)='" + date + "'";
		query += " ORDER BY f_ID DESC";
				
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				Activity a = new Activity();
				a.setTimeStamp(rs.getString(1));
				a.setActivity(rs.getString(2));
				a.setUserName(rs.getString(3));
				result.add(a);
			}
			if (rs != null) {

				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting all RSS activities failed with " + e.getMessage());
			if (rs != null) {
				rs.close();
			}

			if (st != null) {
				st.close();
			}			
		} // catch Close

		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

		return result;
	}
	
	private static String formFilterQuery(String userName, String fromDate, String toDate) {
		String selQuery = "";
			
		String[] filter = new String[3];
			
		if (userName != null) {
			filter[0] = "f_userName='" + userName + "'";
		}
		
		if (fromDate !=null) {
			filter[1]=" date(f_timestamp)>='" +  fromDate +"'";  // Date should be yyyy-mm-dd format for which the UI returns.
		}
		
		if (toDate !=null) {
			filter[2]=" date(f_timestamp)<='" +  toDate +"'";  // Date should be yyyy-mm-dd format for which the UI returns.			
		}

		for(String s:filter){
			if (!(s==null)) {
				if (selQuery.equals("")) {
					selQuery = s;
				}
				else {
					selQuery = selQuery + " AND " + s;
 				}
			}	
		}	
		
		if (!selQuery.equalsIgnoreCase("")) {
			selQuery = " WHERE " + selQuery;
		}
		return selQuery;		
	}
	
	public static Integer getActivitiesCount(String userName,String fromDate,String toDate) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Integer result = 0;

		String query="";
		String selQuery = "";
			
		query = "SELECT count(f_id) FROM t_activity";
		selQuery = formFilterQuery(userName, fromDate, toDate);

		query += selQuery;
				
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				result  = Integer.parseInt(rs.getString(1));
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

		return result;
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
					a.setActivity(changeActivityDesc(a.getActivity(), "result"));
			}	
		}
		return result;	
	}

	public static String changeActivityDesc(String s1, String returntype) {
		String result = "";
		String url = "";
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
				result  = a1[0] + " " + a1[1] + " " + a1[2] + " " + a1[3] + " " + link;
				url = "/" + a1[3] + "/" + id.substring(1);
			}
			else {
				if ((s1.length()-pos)>5) {
					id = s1.substring(pos, s1.substring(pos, s1.length()).indexOf(" ") + pos);
				}
				else {
					id = s1.substring(pos, s1.length());
				}					
				link =  "<a href='./";
				if (s1.indexOf("defect") > 0) {
					link +=  "defect";		
					url = "/defect";
				}
				else if (s1.indexOf("task") > 0) {
					link +=  "task";
					url = "/task";
				}
				else if (s1.indexOf("requirement") > 0) {
					link +=  "requirement";
					url = "/requirement";
				}
				link +=  "/" + id.substring(1)  + "'>" + id + "</a>";	
				result = s1.replaceFirst(id, link);
				url += "/" + id.substring(1);
				
			}	
		}		
		return (returntype.equalsIgnoreCase("link")? url : result);
	}


}
