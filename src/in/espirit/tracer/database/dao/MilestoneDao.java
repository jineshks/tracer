package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.Milestone;
import in.espirit.tracer.model.Ticket;
import in.espirit.tracer.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;



public class MilestoneDao {
	private static Logger logger = Logger.getLogger(MilestoneDao.class.getName());
	
	public static ArrayList<Ticket> getList(String key) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		
		String query = "SELECT f_id, f_title, f_priority, f_status, f_reporter, f_owner, f_component, f_milestone, f_type " +
				"FROM t_defectdetails where f_milestone='" + key +"' " +
				"UNION ALL " +
				"select f_id, f_title, f_priority, f_status, f_reporter, f_owner, f_component, f_milestone, f_type " +
				"from t_taskdetails where f_milestone='" + key + "' " +
				"UNION ALL " +
				"select f_id, f_title, f_priority, f_status, f_reporter, f_owner, f_component, f_milestone, f_type " +
				"from t_requirementdetails where f_milestone='" + key + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Ticket d = new Ticket();
				d.setId(rs.getString(1));
				d.setTitle(rs.getString(2));
				d.setPriority(rs.getString(3));
				d.setStatus(rs.getString(4));
				d.setReporter(rs.getString(5));
				d.setOwner(rs.getString(6));
				d.setComponent(rs.getString(7));
				d.setMilestone(rs.getString(8));
				d.setType(rs.getString(9));
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
		" (f_name, f_description, f_current) " + 
		"VALUES('" +  milestone.getName() +"','" + StringUtils.nullCheck(milestone.getDescription()) + "'," + milestone.getCurrent() + ")";

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
	
		String query = "SELECT f_id, f_name, f_description, f_current FROM t_milestone where f_id=" + id + "";

		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			while (rs.next()) {		
				ms.setId(rs.getString(1));
				ms.setName(rs.getString(2));
				ms.setDescription(rs.getString(3));
				if (rs.getString(4).equalsIgnoreCase("f")) {
					ms.setCurrent("FALSE");
				}
				if (rs.getString(4).equalsIgnoreCase("t")) {
					ms.setCurrent("TRUE");
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
	
	public static ArrayList<Milestone> getAllEntries() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Milestone> result = new ArrayList<Milestone>();
		
		String query="";
	
		query = "SELECT f_id, f_name, f_description, f_current FROM t_milestone";
				
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Milestone ms = new Milestone();
				ms.setId(rs.getString(1));
				ms.setName(rs.getString(2));
				ms.setDescription(rs.getString(3));
				ms.setCurrent(rs.getString(4));			
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
	

}
