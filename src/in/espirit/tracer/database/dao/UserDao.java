package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.User;
import in.espirit.tracer.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;


public class UserDao{
	
	private static Logger logger = Logger.getLogger(UserDao.class.getName());
	
	public static boolean registerUser(User user) throws Exception {
	
	//The userstatus will default have an approvalStatus value of 0.
	
	String query = "INSERT INTO t_userdetails (f_username, f_password, f_displayname, f_email, f_emailsecond, f_phone, f_chatid, f_web, f_team, f_status, f_whoami, f_skills, f_passion)" +
		"VALUES('" + user.getUserName().toLowerCase() +"','" + user.getPassword() + "','" + StringUtils.nullCheck(user.getDisplayName()) + 
		"','" + StringUtils.nullCheck(user.getEmail()) + "','" + StringUtils.nullCheck(user.getEmailSecond()) +  "','" + StringUtils.nullCheck(user.getPhone()) + 
		"','" + StringUtils.nullCheck(user.getChatId()) + "','" + StringUtils.nullCheck(user.getWeb()) + "','" + StringUtils.nullCheck(user.getTeam()) +
		"','" + StringUtils.nullCheck(user.getStatus()) + "','" + StringUtils.nullCheck(user.getWhoAmI()) + "','" + StringUtils.nullCheck(user.getSkills()) +
		"','" + StringUtils.nullCheck(user.getPassion()) + "')";
	
		boolean flag = executeUpdate(query);
		return flag ;
	}
	
	public static User getUserByName(String userName) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		User u = new User();
			
		String query = "SELECT f_username, f_password, f_displayname, f_email, f_emailsecond, f_phone, f_chatid, f_web, f_team, f_status, f_whoami, f_skills, f_passion, f_approvalstatus FROM t_userdetails where f_userName='" + userName + "'";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			while (rs.next()) {		
				u.setUserName(rs.getString(1));
				u.setPassword(rs.getString(2));
				u.setDisplayName(rs.getString(3));
				u.setEmail(rs.getString(4));
				u.setEmailSecond(rs.getString(5));
				u.setPhone(rs.getString(6));
				u.setChatId(rs.getString(7));
				u.setWeb(rs.getString(8));
				u.setTeam(rs.getString(9));
				u.setStatus(rs.getString(10));
				u.setWhoAmI(rs.getString(11));
				u.setSkills(rs.getString(12));
				u.setPassion(rs.getString(13));		
				u.setApprovalStatus(Integer.parseInt(rs.getString(14)));
			}
			if (rs != null) {

				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting user details failed with error " + e.getMessage());
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

		return u;
	}
	
	public static ArrayList<User> getUserList() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		
		ArrayList<User> result = new ArrayList<User>();
			
		String query = "SELECT f_displayname, f_email, f_emailsecond, f_phone, f_chatid, f_web, f_team, f_status, f_whoami, f_skills, f_passion, f_userName FROM t_userdetails where f_approvalStatus=1 ORDER BY f_team ASC";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			while (rs.next()) {	
				User u = new User();
				u.setDisplayName(rs.getString(1));
				u.setEmail(rs.getString(2));
				u.setEmailSecond(rs.getString(3));
				u.setPhone(rs.getString(4));
				u.setChatId(rs.getString(5));
				u.setWeb(rs.getString(6));
				u.setTeam(rs.getString(7));
				u.setStatus(rs.getString(8));
				u.setWhoAmI(rs.getString(9));
				u.setSkills(rs.getString(10));
				u.setPassion(rs.getString(11));		
				u.setUserName(rs.getString(12));
				result.add(u);
			}
			if (rs != null) {

				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting user list failed with error " + e.getMessage());
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
	
	public static ArrayList<User> getUserApprovalList() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		
		ArrayList<User> result = new ArrayList<User>();
			
		String query = "SELECT f_userName, f_displayname, f_email, f_team, f_skills  FROM t_userdetails where f_approvalStatus=0";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			while (rs.next()) {	
				User u = new User();
				u.setUserName(rs.getString(1));
				u.setDisplayName(rs.getString(2));
				u.setEmail(rs.getString(3));
				u.setTeam(rs.getString(4));
				u.setSkills(rs.getString(5));
				result.add(u);
			}
			if (rs != null) {

				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting approval user list failed with error " + e.getMessage());
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
	
	public static boolean editUser(User user) throws Exception {
		
		//The userstatus will default have an approvalStatus value of 0.
				
		String query = "UPDATE t_userdetails" +
		" SET f_displayname='" + StringUtils.nullCheck(user.getDisplayName()) + 
		"', f_email='" + StringUtils.nullCheck(user.getEmail()) +
		"', f_emailsecond='" + StringUtils.nullCheck(user.getEmailSecond()) + 
		"', f_phone='" + StringUtils.nullCheck(user.getPhone()) + 
		"', f_chatid='" + StringUtils.nullCheck(user.getChatId()) + 
		"', f_web='" + StringUtils.nullCheck(user.getWeb()) + 
		"', f_team='" +  StringUtils.nullCheck(user.getTeam()) + 
		"', f_status='" +  StringUtils.nullCheck(user.getStatus()) + 
		"', f_whoami='" + StringUtils.nullCheck(user.getWhoAmI()) + 
		"', f_skills='" + StringUtils.nullCheck(user.getSkills()) + 
		"', f_passion='" + StringUtils.nullCheck(user.getPassion()) + 
		"' WHERE f_userName='" + user.getUserName() +"'";
	
		boolean flag = executeUpdate(query);
		return flag;		
		}
	
	public static Boolean valueExists(String key, String value) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Boolean exists = false;
		
		String query = "SELECT * FROM t_userdetails WHERE " + key + "='" + value +"'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				exists = true;
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
		
		return exists;
	}
	
	public static String getUserRole(String userName) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String role = "";
		
			
		 
		String query = "SELECT f_role FROM t_userdetails WHERE f_username='" + userName +"'";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				role = rs.getString(1);
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
		
		return role;
		
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
		String query = "Update t_userdetails SET f_password='" + password + "' where  f_username='" + userName +"'";
		executeUpdate(query);
	}
	
	public static void adminApprove(String userName, String team, int roleIndex) throws Exception {
		// the value status 1 stands for approve where as -1 stands for rejection.
		String query = "Update t_userdetails SET f_approvalStatus=1, f_team='" + team +"', f_role=" + roleIndex +" where  f_username='" + userName +"'";
		executeUpdate(query);
	}
	
	public static void adminReject(String userName) throws Exception {
		// the value status 1 stands for approve where as -1 stands for rejection.
		String query = "DELETE FROM t_userdetails where  f_username='" + userName +"'";
		executeUpdate(query);
	}
	
	public static int UserApprovalStatus(String userName) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		int status = -2;   // just other value than -1, 0 and 1 used in our application.
		
		String query = "SELECT f_approvalStatus FROM t_userdetails WHERE f_username='" + userName +"'";

		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				status = Integer.parseInt(rs.getString(1));
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
		return status;
	}
	
	private static boolean executeUpdate(String query) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		Integer row = 0;
		
		 try {
				st = con.createStatement();
				row = st.executeUpdate(query);
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				logger.error("Execute Updated Failed with " + e.getMessage());
				if (st != null) {
					st.close();
				}
				throw new Exception(e.getMessage());
			} // catch Close

			finally {
				if (con != null)
					con.close(); 	
			}
			return (row==0)?  false:  true;
		
	}
	
}
