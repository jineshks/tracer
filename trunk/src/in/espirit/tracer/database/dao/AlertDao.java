package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.Alert;
import in.espirit.tracer.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;



public class AlertDao {

	private static Logger logger = Logger.getLogger(AlertDao.class.getName());
	
	public static Alert getAlert(String id) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Alert result = new Alert();
		
		String query = "SELECT f_id, f_name, f_desc, f_startDateTime, f_endDateTime FROM t_alert where f_id=" + id;

		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				result.setId(rs.getString(1));
				result.setName(rs.getString(2));
				result.setDesc(rs.getString(3));
				// Logic is both date and time are stored in same field. But for client it should be different field. So passing that information.
				String startDateTime = rs.getString(4);
				if (startDateTime!=null) {
					result.setStartDate(startDateTime.substring(0, startDateTime.indexOf(" ")));
					result.setStartTime(startDateTime.substring(startDateTime.indexOf(" "), startDateTime.lastIndexOf(":")));					
				}
				String endDateTime = rs.getString(5);
				if (endDateTime!=null) {
					result.setEndDate(endDateTime.substring(0, endDateTime.indexOf(" ")));
					result.setEndTime(endDateTime.substring(endDateTime.indexOf(" "), endDateTime.lastIndexOf(":")));
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
			logger.fatal("Getting Information Failed"+e.getMessage());
			throw new Exception(e.getMessage());
		
		} // catch Close
		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

	return result;
	}
	
	public static ArrayList<Alert> getActiveAlerts() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Alert> result = new ArrayList<Alert>();
		
		//Needs to be changed
		String query = "select f_id, f_name, f_endDateTime from t_alert where f_startDateTime <= CURRENT_TIMESTAMP AND f_endDateTime >= CURRENT_TIMESTAMP";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Alert a = new Alert();
				a.setId(rs.getString(1));
				a.setName(rs.getString(2));
				//String startDateTime = rs.getString(3);
				//a.setStartDate(startDateTime.substring(0, startDateTime.indexOf(" ")));
				//a.setStartTime(startDateTime.substring(startDateTime.indexOf(" "), startDateTime.lastIndexOf(":")));
				String endDateTime = rs.getString(3);
				a.setEndDate(endDateTime.substring(0, endDateTime.indexOf(" ")));
				a.setEndTime(endDateTime.substring(endDateTime.indexOf(" "), endDateTime.lastIndexOf(":")));							
				result.add(a);
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
	
	public static ArrayList<Alert> getAllAlert() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Alert> result = new ArrayList<Alert>();
		
		//Needs to be changed
		String query = "select f_id, f_name, f_startDateTime, f_endDateTime from t_alert ORDER by f_id ASC";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Alert a = new Alert();
				a.setId(rs.getString(1));
				a.setName(rs.getString(2));
				String startDateTime = rs.getString(3);
				if (startDateTime!=null) {
					a.setStartDate(startDateTime.substring(0, startDateTime.indexOf(" ")));
					a.setStartTime(startDateTime.substring(startDateTime.indexOf(" "), startDateTime.lastIndexOf(":")));					
				}
				String endDateTime = rs.getString(4);
				if (endDateTime!=null) {
					a.setEndDate(endDateTime.substring(0, endDateTime.indexOf(" ")));
					a.setEndTime(endDateTime.substring(endDateTime.indexOf(" "), endDateTime.lastIndexOf(":")));
				}							
				result.add(a);
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
			logger.fatal("Getting alert List failed"+e.getMessage());
			throw new Exception(e.getMessage());
		} // catch Close
		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

	return result;
	}
	
	
	public static void registerAlert(Alert alert) throws Exception {
		
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		
		String fields = "f_name, f_desc";
		
		String startDateTime = "";
		String endDateTime = "";
		
		if (alert.getStartDate()!=null && alert.getStartTime()!=null) {
			fields +=", f_startDateTime";
			startDateTime = "','" + StringUtils.nullCheck(alert.getStartDate()) + " " + StringUtils.nullCheck(alert.getStartTime());
		}
		    
		if (alert.getEndDate()!=null && alert.getEndTime()!=null) {
			fields +=", f_endDateTime";
			endDateTime ="','" +  StringUtils.nullCheck(alert.getEndDate()) + " " + StringUtils.nullCheck(alert.getEndTime());
		}
		
		String query = "INSERT INTO t_alert (" + fields + ") VALUES('" +
		StringUtils.nullCheck(alert.getName())+ "','" +StringUtils.nullCheck(alert.getDesc()) + 
		startDateTime + endDateTime +"')";
		
		try {
			st = con.createStatement();
			st.executeUpdate(query);
	
			if (st != null) {
				st.close();
			}

		} catch (Exception e) {

			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());

		} // catch Close

		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	
		//return row;	
		//return regDefId;
		}

	public static void editAlert(Alert alert) throws Exception {
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
					
		String query = "Update t_alert "+
		"SET  f_name='" + StringUtils.nullCheck(alert.getName()) +
		"',  f_desc='" + StringUtils.nullCheck(alert.getDesc());
		
		if (alert.getStartDate()!=null && alert.getStartTime()!=null) {
			query += "',  f_startDateTime='" + StringUtils.nullCheck(alert.getStartDate()) + " " + StringUtils.nullCheck(alert.getStartTime());
		}
		    
		if (alert.getEndDate()!=null && alert.getEndTime()!=null) {
			query += "',  f_endDateTime='" + StringUtils.nullCheck(alert.getEndDate()) + " " +  StringUtils.nullCheck(alert.getEndTime());
		}
				    
		query +="' where  f_id=" + alert.getId();
				
		try {
			st = con.createStatement();
			st.executeUpdate(query);
			if (st != null) {
				st.close();
			}

		} catch (Exception e) {

			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());

		} // catch Close

		finally {
			if (con != null)
				con.close(); // close connection		
		}		
	}

	public static void deleteAlert(Alert alert) throws Exception {
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		    
		String query = "Delete from t_alert where f_id=" + alert.getId();
		try {
			st = con.createStatement();
			st.executeUpdate(query);
			if (st != null) {
				st.close();
			}

		} catch (Exception e) {

			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());

		} // catch Close

		finally {
			if (con != null)
				con.close(); // close connection		
		}		
	}
	
}
