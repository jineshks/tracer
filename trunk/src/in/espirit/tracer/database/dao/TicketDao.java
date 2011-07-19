package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.Activity;
import in.espirit.tracer.model.Comment;
import in.espirit.tracer.model.Defect;
import in.espirit.tracer.model.Requirement;
import in.espirit.tracer.model.Task;
import in.espirit.tracer.model.Ticket;
import in.espirit.tracer.util.DateUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;


public class TicketDao {
	
	private static Logger logger = Logger.getLogger(TicketDao.class.getName());
	
	public static String nullCheck(String input){
		return (input==null)?"":input;
	}
	
	public static String tableName(String type) {
		String tableName="";
		if (type.equalsIgnoreCase("defect")) {
			tableName = "t_defectdetails";
		}
		else if(type.equalsIgnoreCase("task")) {
			tableName = "t_taskdetails";
		}
		else if(type.equalsIgnoreCase("requirement")) {
			tableName = "t_requirementdetails";
		}
		return tableName;
	}
	
	public static void handleActivity(String activity, String loggedUser) throws Exception {		
		Activity a = new Activity();
		a.setTimeStamp(DateUtils.getDatetimeInFormat("yyyy/MM/dd HH:mm"));		
		a.setActivity(activity);
		a.setUserName(loggedUser);
		ActivityDao.registerActivity(a);		
	}
	
	public static String registerTicket(Ticket ticket, String loggedUser) throws Exception {
		
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		
		String id = TicketDao.getSeqID();
		String query = "INSERT INTO " + tableName(ticket.getType());
				
		if(ticket.getType().equalsIgnoreCase("requirement")) {
			query += " (f_id, f_title, f_description, f_priority, f_status, f_reporter, f_owner, f_parentticket, f_component, f_milestone, f_importance, f_tags, f_progress, f_storypoint) ";
		}
		else {
			query += " (f_id, f_title, f_description, f_priority, f_status, f_reporter, f_owner, f_parentticket, f_component, f_milestone, f_importance, f_tags, f_progress) ";
		}
	
		if(ticket.getType().equalsIgnoreCase("requirement")) {
			query += "VALUES(" + id + ",'" + TicketDao.nullCheck(ticket.getTitle())+"','" + 
			TicketDao.nullCheck(ticket.getDesc()) +"','" + TicketDao.nullCheck(ticket.getPriority()) +"','" + TicketDao.nullCheck(ticket.getStatus()) +
			"','" + TicketDao.nullCheck(ticket.getReporter())+"','" + TicketDao.nullCheck(ticket.getOwner())+
			"'," + ticket.getParentTicket()+",'" + TicketDao.nullCheck(ticket.getComponent())+
			"','" + TicketDao.nullCheck(ticket.getMilestone()) +"','" +  TicketDao.nullCheck(ticket.getImportance()) + 
			"','" + TicketDao.nullCheck(ticket.getTags()) + "'," + ticket.getProgress()+"," + ((Requirement) ticket).getStoryPoint() + ")";
		}
		else {
			query += "VALUES(" + id + ",'" + TicketDao.nullCheck(ticket.getTitle())+"','" + 
			TicketDao.nullCheck(ticket.getDesc()) +"','" + TicketDao.nullCheck(ticket.getPriority()) +"','" + TicketDao.nullCheck(ticket.getStatus()) +
			"','" + TicketDao.nullCheck(ticket.getReporter())+"','" + TicketDao.nullCheck(ticket.getOwner())+
			"'," + ticket.getParentTicket()+",'" + TicketDao.nullCheck(ticket.getComponent())+
			"','" + TicketDao.nullCheck(ticket.getMilestone()) +"','" +  TicketDao.nullCheck(ticket.getImportance()) + 
			"','" + TicketDao.nullCheck(ticket.getTags()) + "'," + ticket.getProgress()+")";
		}
		try {
			st = con.createStatement();
			st.executeUpdate(query);

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("registration of ticket failed with " + e.getMessage());
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
		String activity = loggedUser + " has created " + ticket.getType() + " #" + id;
		handleActivity(activity, loggedUser);			
		return id;	
	}
		
	public static ArrayList<Ticket> getAllTickets(String type, String userName, String priority, String status, String milestone, String reporter, String importance) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		
		String query="";
		String selQuery="";
		String[] filter= new String[6];

		if (!(priority==null)) {
			filter[0] = "f_priority='" + priority + "'";			
		}
		
		if (!(status==null)) {
			filter[1] = "f_status='" + status + "'";			
		}
	
		if (!(userName==null)) {
			filter[2] = "f_owner='" + userName + "'";
		}
	
		if (!(milestone==null)) {
			filter[3] = "f_milestone='" + milestone + "'";
		}
		
		if (!(reporter==null)) {
			filter[4] = "f_reporter='" + reporter + "'";
		}
		
		if (!(importance==null)) {
			filter[5] = "f_importance='" + importance + "'";
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
	
		query = "SELECT f_id, f_title, f_priority, f_status, f_importance, f_reporter, f_owner, f_component, f_milestone, f_type FROM " + tableName(type);
		if (!selQuery.equals("")) {
		 query += " where " + selQuery;
		}		
	
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Ticket d = new Ticket();
				d.setId(rs.getString(1));
				d.setTitle(rs.getString(2));
				d.setPriority(rs.getString(3));
				d.setStatus(rs.getString(4));
				d.setImportance(rs.getString(5));
				d.setReporter(rs.getString(6));
				d.setOwner(rs.getString(7));
				d.setComponent(rs.getString(8));
				d.setMilestone(rs.getString(9));
				d.setType(rs.getString(10));
				result.add(d);
			}
			if (rs != null) {
			
				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting all tickets failed with " + e.getMessage());
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
	
	public static ArrayList<Ticket> getMyTickets(String type, String userName) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		
		String query="";
		
		query = "SELECT f_id, f_title FROM " + tableName(type) + " WHERE f_owner='" + userName +"'";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Ticket d = new Ticket();
				d.setId(rs.getString(1));
				d.setTitle(rs.getString(2));
				result.add(d);
			}
			if (rs != null) {
			
				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting my tickets failed with " + e.getMessage());
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
	
	public static ArrayList<Ticket> getRelatedTickets(String id, String type) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		
		String query="SELECT f_id, f_title, f_priority, f_status, f_reporter, f_owner, f_component, f_milestone FROM " + tableName(type);

		if (id!=null) {
			query +=" where f_id!=" + id;
		}
		
			
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
				result.add(d);
			}
			if (rs != null) {
			
				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting related list tickets failed with" + e.getMessage());
			
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
	
	public static Ticket getTicket(String type, String id) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Ticket d;
		if(type.equalsIgnoreCase("defect")) {
			d = new Defect();
		}
		else if(type.equalsIgnoreCase("task")){
			d = new Task();
		}		
		else if(type.equalsIgnoreCase("requirement")) {
			d = new Requirement();
		}
		else {
			d = new Ticket();
		}
		
		String query = "";
		String fields = "";
		if(type.equalsIgnoreCase("requirement")) {
			fields = "f_id, f_title, f_description, f_priority, f_status, f_reporter, f_owner, f_parentticket, f_component, f_milestone, f_type, f_importance, f_tags, f_progress, f_storypoint";
		}
		else {
			fields = "f_id, f_title, f_description, f_priority, f_status, f_reporter, f_owner, f_parentticket, f_component, f_milestone, f_type, f_importance, f_tags, f_progress";
		}
		
		query = "SELECT " + fields + " FROM "+ tableName(type) + " where f_id='" + id + "'";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {			
				d.setId(rs.getString(1));
				d.setTitle(rs.getString(2));
				d.setDesc(rs.getString(3));
				d.setPriority(rs.getString(4));
				d.setStatus(rs.getString(5));
				d.setReporter(rs.getString(6));
				d.setOwner(rs.getString(7));
				d.setParentTicket(rs.getString(8));
				d.setComponent(rs.getString(9));
				d.setMilestone(rs.getString(10));
				d.setType(rs.getString(11));
				d.setImportance(rs.getString(12));
				d.setTags(rs.getString(13));
				d.setProgress(rs.getString(14));
				if (type.equalsIgnoreCase("requirement")) {
					((Requirement) d).setStoryPoint(rs.getString(15));
				}
				d.setComments(TicketDao.getComments(id));
				
			}
			if (rs != null) {
			
				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting ticket failed with error " + e.getMessage());
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

	public static Integer editTicket(Ticket ticket, String loggedUser) throws Exception {
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		Integer row;
	
		 String query = "UPDATE " + tableName(ticket.getType()) +
				" SET f_title='" + ticket.getTitle() + 
				"', f_description='" + ticket.getDesc() +
				"', f_priority='" + TicketDao.nullCheck(ticket.getPriority()) + 
				"', f_status='" + TicketDao.nullCheck(ticket.getStatus()) + 
				"', f_reporter='" + TicketDao.nullCheck(ticket.getReporter()) + 
				"', f_owner='" + TicketDao.nullCheck(ticket.getOwner()) + 
				"', f_parentticket=" + ticket.getParentTicket() + 
				", f_component='" + TicketDao.nullCheck(ticket.getComponent()) + 
				"', f_milestone='" + ticket.getMilestone() + 
				"', f_progress=" + ticket.getProgress() + 
				", f_tags='" + TicketDao.nullCheck(ticket.getTags()) + 
				"', f_importance='" + TicketDao.nullCheck(ticket.getImportance()) + "'";
		 if(ticket.getType().equalsIgnoreCase("requirement")) {
			 query +=", f_storypoint=" + ((Requirement) ticket).getStoryPoint();			 
		 }		 
			query +=" WHERE f_id='" + ticket.getId() + "'";
		 try {
			st = con.createStatement();
			row = st.executeUpdate(query);
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			logger.error("Edited save of ticket failed with " + e.getMessage());
			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());

		} // catch Close

		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally
		String activity = loggedUser + " has updated " + ticket.getType() + " #" + ticket.getId();
		handleActivity(activity, loggedUser);	
		return row;	
		
	}

	public static ArrayList<Comment> getComments(String id) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Comment> result = new ArrayList<Comment>();
				
		String query = "";
		
		query = "SELECT f_username, f_timestamp, f_comment FROM t_comments where f_ticketid='" + id + "'";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {			
				Comment c = new Comment();
				c.setUserName(rs.getString(1));
				c.setTimeStamp(rs.getString(2));
				c.setComment(rs.getString(3));	
				result.add(c);
			}
			if (rs != null) {
			
				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting comments failed with error " + e.getMessage());
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
	
	public static boolean insertComment(String id,Comment comment) throws Exception {
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		int updateCount = 0;
		
		String query = "Insert into t_comments (f_ticketid, f_username, f_timestamp,f_comment) VALUES ('" +
		id +"','" + comment.getUserName() +"','" + comment.getTimeStamp() + "','" +
		comment.getComment() +"')";
	
		try {
			st = con.createStatement();
			updateCount = st.executeUpdate(query);
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			logger.error("Inserting comment failed with " + e.getMessage());
			if (st != null) {
				st.close();
			}
			throw new Exception(e.getMessage());

		} // catch Close

		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	
		
		if(updateCount != 0){
			return true;
		}else{
			return false;
		}
		
		
	}
	
	public static String getSeqID() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String result="";
				
		String query = "";
		query = "SELECT nextVal('sequence_ticketid')";
		
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
			logger.error("Getting sequence id failed with error " + e.getMessage());
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
	
	
	public static Ticket getParentTicketDetails(String id) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Ticket ticket = null;
		
		String query = "";
		
		query = "select f_id, f_title,f_type from t_defectdetails where f_id="+id+
		" UNION select f_id,f_title,f_type from t_taskdetails where f_id="+id+
		" UNION select f_id,f_title,f_type from t_requirementdetails where f_id="+id;
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {	
				ticket = new Ticket();
				ticket.setId(rs.getString(1));
				ticket.setTitle(rs.getString(2));
				ticket.setType(rs.getString(3));								
			}
			if (rs != null) {
			
				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting ticket failed with error " + e.getMessage());
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
		
		return ticket;
	}
	
	public static ArrayList<Ticket> getSubTicketDetails(String id) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Ticket> result = new ArrayList<Ticket>();;

		
		String query = "";
		
		query = "select f_id, f_title,f_type from t_defectdetails where f_parentTicket="+id+
		" UNION select f_id,f_title,f_type from t_taskdetails where f_parentTicket="+id+
		" UNION select f_id,f_title,f_type from t_requirementdetails where f_parentTicket="+id;

		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {		
				Ticket ticket = new Ticket();
				ticket.setId(rs.getString(1));
				ticket.setTitle(rs.getString(2));
				ticket.setType(rs.getString(3));	
				result.add(ticket);
			}
			if (rs != null) {
			
				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Getting ticket failed with error " + e.getMessage());
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
		return (result.size()==0)?null:result;
	}

	public static boolean ticketExists(String parentTicket) throws Exception {
			
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Boolean result = false;
		
				
		String query = "";
		query = "select f_id from t_defectdetails where f_id="+parentTicket+
		" UNION select f_id from t_taskdetails where f_id="+parentTicket+
		" UNION select f_id from t_requirementdetails where f_id="+parentTicket;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {			
				result = true;							
			}
			if (rs != null) {
			
				rs.close();
			}

			if (st != null) {
				st.close();
			}

		} catch (Exception e) {
			logger.error("Checking existing ticket failed with error " + e.getMessage());
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
	
	
}
