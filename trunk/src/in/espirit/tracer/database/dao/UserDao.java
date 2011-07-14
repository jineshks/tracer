package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class UserDao{
	
	public static Integer registerUser(User user) throws Exception {
	
	ConnectionPool pool = ConnectionFactory.getPool();
	Connection con = pool.getConnection();
	Statement st = null;
	//ResultSet rs = null;
	Integer row;
	
	String query = "INSERT INTO t_userdetails VALUES('" + user.getUserName().toLowerCase()+"','" + user.getPassword() +"','" + user.getEmail() +"')";
	try {
		st = con.createStatement();
		row = st.executeUpdate(query);
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
	return row;	
	}
	
	public static Boolean userExists(String userName) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Boolean userExists = false;
		
		String query = "SELECT * FROM t_userdetails WHERE f_username='" + userName +"'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				userExists = true;
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
		
		return userExists;
	}
	
	public static Boolean checkPassword(String userName, String password) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Boolean checkPassword = false;
		String passwordInDb="";
		
		String query = "SELECT f_password FROM t_userdetails WHERE f_username='" + userName +"'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				passwordInDb = rs.getString(1);
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

		//return passwordInDb + "---" + password;
		
		if (passwordInDb.equals(password)) {
				checkPassword = true;
		}		
		return checkPassword;
	}
	
	public static ArrayList<String> getUserNames() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<String> result = new ArrayList<String>();
		
		String query = "SELECT f_userName FROM t_userdetails";
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
	
	public static void changePassword(String userName, String password) throws Exception {
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		    
		String query = "Update t_userdetails SET f_password='" + password + "' where  f_username='" + userName +"'";
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
