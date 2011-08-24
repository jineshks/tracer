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

		
		String fields = "(f_from, f_to, f_cc, f_subject, f_sentdate, f_important, f_notify, f_message, f_show)";
		
		// This is for showing the message list to all users and for the unread part 1 means unread and 0 means read
		//Our aim is store the show field with values <username1>:1,<username2>:1...... combining both to and cc fields.
		
		String show = "";
		String to_show = message.getTo();
		String cc_show = message.getCc();
				
		to_show +=":1";
		to_show = to_show.replaceAll(",", ":1,");
			
		show = to_show;
						
		if (cc_show != null) {
			cc_show +=":1";
			cc_show = cc_show.replaceAll(",", ":1,");
			show += "," + cc_show;
		}
			
		String values = "('" +  message.getFrom() +"','" + message.getTo() +
		"','" + StringUtils.nullCheck(message.getCc()) + "','" + message.getSubject() + 		
		"','" + DateUtils.getDatetimeInFormat("yyyy-MM-dd HH:mm") + "'," +  message.getImportant() + 
		"," + message.getNotify() + ",'" + message.getMessage() + "','" + show + "')";
		
		String query = "INSERT INTO t_message " + fields + " VALUES " + values;
	
		boolean flag = false;
		flag = DaoUtils.executeUpdate(query);
		return flag;
	}


	public static ArrayList<Messaging> getMessages(String loggedUser, String offset, String count, String tag, String from, String fromDate, String toDate) throws Exception {

		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Messaging> result = new ArrayList<Messaging>();

		String query="";

		query = "SELECT f_id, f_from, f_sentdate, f_subject, f_show FROM t_message";
		//query += " WHERE f_show like '%" + loggedUser + "%'";
		query += formFilterQuery(loggedUser, tag, from, fromDate, toDate);
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
				String show = rs.getString(5);
				int stpo = show.indexOf(loggedUser);   // Get the starting point for the user name..normally it will be stored as <username>:1,<username2>:0..... where 0 stands for read and 1 stands for unread
				String unread = show.substring(stpo + loggedUser.length()+1, stpo + loggedUser.length()+2);
				mes.setUnread(unread);
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

	public static String getMessagesCount(String loggedUser, String tag, String from, String fromDate, String toDate) throws Exception {

		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String count = "";
		String query="";

		query = "SELECT count(f_id) FROM t_message";
		//query += " WHERE f_show like '%" + loggedUser + "%'";
		query += formFilterQuery(loggedUser, tag, from, fromDate, toDate);
		
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

		query = "SELECT f_id, f_from, f_to, f_cc, f_sentdate, f_subject, f_tags, f_message FROM t_message WHERE f_id=" + id;

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
				result.setTags(rs.getString(7));
				result.setMessage(rs.getString(8));				
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

	public static String getMessageUnread(String id) throws Exception {

		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String result = "";
		
		String query="";

		query = "SELECT f_show FROM t_message WHERE f_id=" + id;

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
			logger.error("Getting user messages failed with " + e.getMessage());
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
	
	
	public static boolean changeUnread(String loggedUser, String id) throws Exception {
		boolean flag = false;
		String show = getMessageUnread(id);
		show = show.replaceAll(loggedUser+":1", loggedUser+":0");		
		String query = "UPDATE t_message set f_show='" + show + "' WHERE f_id=" + id;
		flag = DaoUtils.executeUpdate(query);
		return flag;	
	}
	
	public static boolean updateTags(String id, String tags) throws Exception {
		boolean flag = false;	
		String query = "UPDATE t_message set f_tags='" + tags + "' WHERE f_id=" + id;
		flag = DaoUtils.executeUpdate(query);
		return flag;	
	}
	
	public static boolean deleteMessage(String loggedUser, String id) throws Exception {
		boolean flag = false;
		String query;
		String show = getMessageUnread(id);
		show = show.replaceAll(loggedUser+":[01][,]?", "");
		
		if (show.equalsIgnoreCase("")) {
			query = "DELETE FROM t_message where f_id=" + id;			
		}
		else {
			query = "UPDATE t_message set f_show='" + show + "' WHERE f_id=" + id;
		}		
		flag = DaoUtils.executeUpdate(query);
		return flag;	
	}
	
	private static String formFilterQuery(String loggedUser, String tag, String from, String fromDate, String toDate) {
		String selQuery = "";
			
		String[] filter = new String[5];
			
		filter[0] = "f_show like '%" + loggedUser + "%'";
		
		if (tag != null) {
			filter[1] = "f_tags like '%" + tag + "%'";
		}
		
		if (from != null) {
			filter[2] = "f_from='" + from + "'";
		}
		
		if (fromDate !=null) {
			filter[3]=" date(f_sentdate)>='" +  fromDate +"'";  // Date should be yyyy-mm-dd format for which the UI returns.
		}
		
		if (toDate !=null) {
			filter[4]=" date(f_sentdate)<='" +  toDate +"'";  // Date should be yyyy-mm-dd format for which the UI returns.			
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
	
}
