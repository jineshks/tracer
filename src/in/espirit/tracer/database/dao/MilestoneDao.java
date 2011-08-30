package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.Milestone;
import in.espirit.tracer.model.Requirement;
import in.espirit.tracer.model.Ticket;
import in.espirit.tracer.util.DaoUtils;
import in.espirit.tracer.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;



public class MilestoneDao {
	private static Logger logger = Logger.getLogger(MilestoneDao.class.getName());
	
	public static ArrayList<Ticket> getList(String key, String importance, String priority, String status, String phase, String component, String reporter, String owner, String tags) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		
		
		String selQuery="";
		String[] filter= new String[9];
		
		filter[0] = "f_milestone='" + key + "'";
		
		if (!(importance==null)) {
			filter[1] = "f_importance='" + importance + "'";
		}

		if (!(priority==null)) {
			filter[2] = "f_priority='" + priority + "'";			
		}
		
		if (!(status==null)) {
			filter[3] = "f_status='" + status + "'";			
		}
		
		if (!(phase==null)) {
			filter[4] = "f_phase='" + phase + "'";			
		}
		
		if (!(component==null)) {
			filter[5] = "f_component='" + component + "'";
		}
		
		if (!(reporter==null)) {
			filter[6] = "f_reporter='" + reporter + "'";
		}
		
		if (!(owner==null)) {
			filter[7] = "f_owner='" + owner + "'";
		}
		
		if (!(tags==null)) {
			filter[8] = "f_tags like '%" + tags + "%'";
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
		
		String fields = "f_id, f_title, f_priority, f_owner, f_importance, f_type";
		
		
		
		String query = "SELECT " + fields + " FROM t_defectdetails WHERE " + selQuery +
				" UNION ALL " +
				"SELECT " + fields + " FROM t_taskdetails WHERE " + selQuery +
				" UNION ALL " +
				"SELECT " + fields + " FROM t_requirementdetails WHERE " + selQuery + " ORDER by f_priority ASC";				
				
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Ticket d = new Ticket();
				d.setId(rs.getString(1));
				d.setTitle(rs.getString(2));
				d.setPriority(rs.getString(3));
				d.setOwner(rs.getString(4));
				d.setImportance(rs.getString(5));
				d.setType(rs.getString(6));
				result.add(d);
			}
			if (rs != null) {			
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());
		} // catch Close
		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

	return result;
	}
	
	public static void registerEntry(Milestone milestone) throws Exception {
		
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		
		String query = "INSERT INTO t_milestone" + 
		" (f_name, f_description, f_startdate, f_enddate, f_current) " + 
		"VALUES('" +  milestone.getName() +"','" + StringUtils.nullCheck(milestone.getDescription()) +
		"','" + StringUtils.nullCheck(milestone.getStartDate()) + "','" + StringUtils.nullCheck(milestone.getEndDate()) + 		
		"'," + milestone.getCurrent() + ")";

		try {
			st = con.createStatement();
			st.executeUpdate(query);

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("registration of milestone failed with " + e.getMessage());
			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());

		} // catch Close

		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	
		//register activity
		//String activity = loggedUser + " has created " + ticket.getType() + " #" + id;
		//handleActivity(activity, loggedUser);					
	}	
	
	public static Milestone getEntry(String id) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Milestone ms = new Milestone();
	
		String query = "SELECT f_id, f_name, f_description, f_startdate, f_enddate, f_current FROM t_milestone where f_id=" + id + "";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			while (rs.next()) {		
				ms.setId(rs.getString(1));
				ms.setName(rs.getString(2));
				ms.setDescription(rs.getString(3));
				ms.setStartDate(rs.getString(4));
				ms.setEndDate(rs.getString(5));
				if (rs.getString(6)!=null) {	
					if (rs.getString(6).equalsIgnoreCase("t")) {
						ms.setCurrent("TRUE");
					}
				}
				
			}
			if (rs != null) {

				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting milestone failed with error " + e.getMessage());
			if (rs != null) {
				rs.close();
			}

			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());

		} // catch Close

		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

		return ms;
	}

	public static void editTicket(Milestone ms) throws Exception {
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;

		String query = "UPDATE t_milestone" + 
		" SET f_name='" + ms.getName() +
		"', f_description='" + StringUtils.nullCheck(ms.getDescription()) +
		"', f_startdate='" + StringUtils.nullCheck(ms.getStartDate()) + 
		"', f_enddate='" + StringUtils.nullCheck(ms.getEndDate()) + 
		"', f_current=" + ms.getCurrent() +
		" WHERE f_id=" + ms.getId();
		try {
			st = con.createStatement();
			st.executeUpdate(query);
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			logger.error("Edited save of milestone failed with " + e.getMessage());
			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());

		} // catch Close

		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally
		//String activity = loggedUser + " has updated " + ticket.getType() + " #" + ticket.getId();
		//handleActivity(activity, loggedUser);	
	}
	
	public static ArrayList<Milestone> getAllEntries(String fromDate, String toDate) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Milestone> result = new ArrayList<Milestone>();
		
		String query="";
	
		query = "SELECT f_id, f_name, f_description, f_startdate, f_enddate, f_current FROM t_milestone";
		
		if (fromDate != null || toDate != null) {
			query +=" WHERE ";
		}
		
		if (fromDate != null) {
			query += "f_startdate>='" + fromDate + "'";
		}
		
		if (fromDate != null && toDate != null) {
			query +=" AND ";
		}
				
		if (toDate != null) {
			query += "f_enddate<='" + toDate + "'";
		}
		
		query += " ORDER by f_id DESC";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Milestone ms = new Milestone();
				ms.setId(rs.getString(1));
				ms.setName(rs.getString(2));
				ms.setDescription(rs.getString(3));
				ms.setStartDate(rs.getString(4));
				ms.setEndDate(rs.getString(5));
				ms.setCurrent(rs.getString(6));			
				result.add(ms);
			}
			if (rs != null) {			
				rs.close();
			}
			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting all milestone failed with " + e.getMessage());
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
	
	public static ArrayList<String> getMilestoneNames() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<String> result = new ArrayList<String>();
		
		String query = "SELECT f_name FROM t_milestone";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				result.add(rs.getString(1));
			}
			if (rs != null) {			
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());
		} // catch Close
		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

	return result;
	}
	
	public static String[] getMilestoneDates(String milestone) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String[] result = new String[2];
		
		String query = "SELECT f_startdate, f_enddate FROM t_milestone where f_name='" + milestone + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				result[0] = rs.getString(1);
				result[1] = rs.getString(2);			
			}
			if (rs != null) {			
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());
		} // catch Close
		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

	return result;
	}
	
	
	public static String getCurrentMilestone() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String result = null;
		
		String query="";
	
		query = "SELECT f_name FROM t_milestone where f_current=TRUE";
				
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
			result = rs.getString(1);
			}
			if (rs != null) {
			
				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting all milestone failed with " + e.getMessage());
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
	
	public static Milestone getCurrentMilestoneDetails() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Milestone result = new Milestone();
		
		String query="";
	
		query = "SELECT f_id, f_name, f_startdate, f_enddate FROM t_milestone where f_current=TRUE";
				
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				result.setId(rs.getString(1));
				result.setName(rs.getString(2));
				result.setStartDate(rs.getString(3));
				result.setEndDate(rs.getString(4));
			}
			if (rs != null) {
			
				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting all milestone failed with " + e.getMessage());
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
	
	public static ArrayList<Requirement> getMilestoneStory(String milestone) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Requirement> result = new ArrayList<Requirement>();
		
		String query = "select f_id, f_storypoint from t_requirementdetails where f_milestone='" + milestone + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Requirement r = new Requirement();
				r.setId(rs.getString(1));
				r.setStoryPoint(rs.getString(2));
				result.add(r);
			}
			if (rs != null) {			
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());
		} // catch Close
		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

	return result;
	}
	
	public static double getTaskProgressTotal(String parentId) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		double d = 0;
		
		String query = "select avg(f_progress) from t_taskdetails where f_parentticket =" + parentId;
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {			
				if (rs.getString(1) != null) {
					d = Double.parseDouble(rs.getString(1));
				}
			}
			if (rs != null) {			
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());
		} // catch Close
		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

	return d;
	}
	
	public static void changeCurrentMilestone() throws Exception {
		String query = "Update t_milestone set f_current=FALSE where f_name='" + getCurrentMilestone() + "'";
		DaoUtils.executeUpdate(query);		
	}
	
	public static int calcProgress(String name) throws Exception {
		//(((sum of task progress)/(no of tasks)x story point)+((sum of task progress)/(no of tasks)x story point)+...)/(sum of story points)
		ArrayList<Requirement> req = getMilestoneStory(name);
		double top = 0;    // has the top value in the division operation.
		double bottom = 0;  // has the bottom value in the division operation.
		for(Requirement r : req) {
			if (r.getStoryPoint()!=null) {
				int storypoint = Integer.parseInt(r.getStoryPoint());
				double prog = getTaskProgressTotal(r.getId());
				top += (prog * storypoint);
				bottom += storypoint;		
			}
		}
		return (int) (top/bottom);
	}
	
	public static int getSprintStoryPoint(String milestone) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		int result = 0;
		
		String query = "select sum(f_storypoint) from t_requirementdetails where f_milestone='" + milestone + "'";
	
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				result = rs.getInt(1);								
			}
			if (rs != null) {			
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			logger.fatal(e.getMessage());
			throw new Exception(e.getMessage());
		} // catch Close
		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

	return result;
	}
	
	
	public static int getSprintTotalTickets(String milestone) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		int result = 0;
		
		String query = "select count(f_id) from t_defectdetails where f_milestone='" + milestone + "'"+
		" UNION ALL select count(f_id) from t_taskdetails where f_milestone='" + milestone + "'" + 
		" UNION ALL select count(f_id) from t_requirementdetails where f_milestone='" + milestone + "'";
	
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				result += rs.getInt(1);				
			}
			if (rs != null) {			
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			logger.fatal(e.getMessage());
			throw new Exception(e.getMessage());
		} // catch Close
		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

	return result;
	}
}
