package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



public class AlertDao {

	public static Alert getAlert(String id) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Alert result = new Alert();
		
		String query = "SELECT f_id, f_name, f_desc, f_startDate, f_startTime, f_endDate, f_endTime FROM t_alert where f_id=" + id;
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				result.setId(rs.getString(1));
				result.setName(rs.getString(2));
				result.setDesc(rs.getString(3));
				result.setStartDate(rs.getString(4));
				result.setStartTime(rs.getString(5));
				result.setEndDate(rs.getString(6));
				result.setEndTime(rs.getString(7));
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
	
	public static ArrayList<Alert> getAlertList(String key) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Alert> result = new ArrayList<Alert>();
		
		//It needs to be changed. 
		
		String query = "SELECT * FROM t_alert where f_key='" + key +"'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Alert c = new Alert();
				result.add(c);
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
		String query = "select f_id, f_name, f_startDate, f_startTime, f_endDate, f_endTime from t_alert ORDER by f_id ASC";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Alert a = new Alert();
				a.setId(rs.getString(1));
				a.setName(rs.getString(2));
				a.setStartDate(rs.getString(3));
				a.setStartTime(rs.getString(4));
				a.setEndDate(rs.getString(5));
				a.setEndTime(rs.getString(6));				
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
	
	
	public static void registerAlert(Alert alert) throws Exception {
		
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		    
		String query = "INSERT INTO t_alert (f_name, f_desc, f_startDate, f_startTime, f_endDate, f_endTime) VALUES('" + 
		alert.getName()+ "','" +alert.getDesc() + "','" +alert.getStartDate()+"','" + 
		alert.getStartTime()+ "','" + alert.getEndDate()+"','" +  alert.getEndTime() +"')";
		
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
		"SET  f_name='" + alert.getName() +
		"',  f_desc='" + alert.getDesc() +
		"',  f_startDate='" + alert.getStartDate() +
		"',  f_startTime='" + alert.getStartTime() +
		"',  f_endDate='" + alert.getEndDate() +
		"',  f_endTime='" + alert.getEndTime() +		
		"' where  f_id=" + alert.getId();
				
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
