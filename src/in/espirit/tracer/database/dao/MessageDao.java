package in.espirit.tracer.database.dao;

import java.util.ArrayList;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.Messaging;
import in.espirit.tracer.util.DaoUtils;
import in.espirit.tracer.util.DateUtils;
import in.espirit.tracer.util.StringUtils;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MessageDao {

	private static Logger logger = Logger.getLogger(MilestoneDao.class.getName());


	public static boolean registerEntry(Messaging message) throws Exception {

		String query = "INSERT INTO t_message" + 
		" (f_from, f_to, f_cc, f_subject, f_sentdate, f_important, f_notify, f_message) " + 
		"VALUES('" +  message.getFrom() +"','" + message.getTo() +
		"','" + StringUtils.nullCheck(message.getCc()) + "','" + message.getSubject() + 		
		"','" + DateUtils.getDatetimeInFormat("yyyy-MM-dd HH:mm") + "'," +  message.getImportant() + "," + message.getNotify() + ",'" + message.getMessage() + "')";
		boolean flag = false;
		flag = DaoUtils.executeUpdate(query);
		return flag;
	}


	public static ArrayList<Messaging> getMessages(String loggedUser, String offset, String count) throws Exception {

		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Messaging> result = new ArrayList<Messaging>();

		String query="";

		query = "SELECT f_id, f_from, f_sentdate, f_subject FROM t_message";
		query += " WHERE f_to like '%" + loggedUser + "%' OR f_cc like '%" + loggedUser + "%'";
		query += " ORDER by f_id DESC";
		
		if (offset != null) {
			query +=" OFFSET " + offset;
		}
		
		if (count != null) {
			query +=" LIMIT " + count;
		}

		try {
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				Messaging mes = new Messaging();
				mes.setId(rs.getInt(1));
				mes.setFrom(rs.getString(2));
				mes.setSentdate(rs.getString(3));
				mes.setSubject(rs.getString(4));
				result.add(mes);
			}
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			logger.error("Getting user messages failed with " + e.getMessage());
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
				throw new Exception(e.getMessage());
		} 
		finally {
			if (con != null)
				con.close(); // close connection		
		}
		return result;
	}	

	public static String getMessagesCount(String loggedUser) throws Exception {

		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String count = "";
		String query="";

		query = "SELECT count(f_id) FROM t_message";
		query += " WHERE f_to like '%" + loggedUser + "%' OR f_cc like '%" + loggedUser + "%'";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				count = rs.getString(1);
			}
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			logger.error("Getting user messages failed with " + e.getMessage());
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
				throw new Exception(e.getMessage());
		} 
		finally {
			if (con != null)
				con.close(); // close connection		
		}
		return count;
	}	

	
	public static Messaging getMessageDetails(int id) throws Exception {

		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Messaging result = new Messaging();

		String query="";

		query = "SELECT f_id, f_from, f_to, f_cc, f_sentdate, f_subject, f_important, f_notify, f_message FROM t_message WHERE f_id=" + id;

		try {
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				result.setId(rs.getInt(1));
				result.setFrom(rs.getString(2));
				result.setTo(rs.getString(3));
				result.setCc(rs.getString(4));
				result.setSentdate(rs.getString(5));
				result.setSubject(rs.getString(6));
				result.setImportant(rs.getInt(7));
				result.setNotify(rs.getInt(8));
				result.setMessage(rs.getString(9));				
			}
			if (rs != null) {

				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting user messages failed with " + e.getMessage());
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
