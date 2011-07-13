package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.Comment;
import in.espirit.tracer.model.Defect;
import in.espirit.tracer.model.Requirement;
import in.espirit.tracer.model.Task;
import in.espirit.tracer.model.Ticket;

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
			tableName = "defectdetails";
		}
		else if(type.equalsIgnoreCase("task")) {
			tableName = "taskdetails";
		}
		else if(type.equalsIgnoreCase("requirement")) {
			tableName = "requirementdetails";
		}
		return tableName;
	}
	
	public static String registerTicket(Ticket ticket) throws Exception {
		
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		
		String id = TicketDao.getSeqID();
		
		String query = "INSERT INTO " + tableName(ticket.getType());
				
		if(ticket.getType().equalsIgnoreCase("requirement")) {
			query += " (id, shortdesc, description, priority, status, reporter, owner, related, component, milestone, importance, progress, storypoint) ";
		}
		else {
			query += " (id, shortdesc, description, priority, status, reporter, owner, related, component, milestone, importance, progress) ";
		}
		
		if(ticket.getType().equalsIgnoreCase("requirement")) {
			query += "VALUES(" + id + ",'" + TicketDao.nullCheck(ticket.getShortDesc())+"','" + 
			TicketDao.nullCheck(ticket.getDesc()) +"','" + TicketDao.nullCheck(ticket.getPriority()) +"','" + TicketDao.nullCheck(ticket.getStatus()) +
			"','" + TicketDao.nullCheck(ticket.getReporter())+"','" + TicketDao.nullCheck(ticket.getOwner())+
			"','" + TicketDao.nullCheck(ticket.getRelated())+"','" + TicketDao.nullCheck(ticket.getComponent())+
			"','" + TicketDao.nullCheck(ticket.getMilestone()) +"','" +  TicketDao.nullCheck(ticket.getImportance()) + 
			"'," + ticket.getProgress()+"," + ((Requirement) ticket).getStoryPoint() + ")";
		}
		else {
			query += "VALUES(" + id + ",'" + ticket.getShortDesc()+"','" + 
			TicketDao.nullCheck(ticket.getDesc()) +"','" + TicketDao.nullCheck(ticket.getPriority()) +"','" + TicketDao.nullCheck(ticket.getStatus()) +
			"','" + TicketDao.nullCheck(ticket.getReporter())+"','" + TicketDao.nullCheck(ticket.getOwner())+
			"','" + TicketDao.nullCheck(ticket.getRelated())+"','" + TicketDao.nullCheck(ticket.getComponent())+
			"','" + TicketDao.nullCheck(ticket.getMilestone()) +"','" +  TicketDao.nullCheck(ticket.getImportance()) + 
			"'," + ticket.getProgress()+")";
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
		return id;	
		//return regDefId;
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
			filter[0] = "priority='" + priority + "'";			
		}
		
		if (!(status==null)) {
			filter[1] = "status='" + status + "'";			
		}
	
		if (!(userName==null)) {
			filter[2] = "owner='" + userName + "'";
		}
	
		if (!(milestone==null)) {
			filter[3] = "milestone='" + milestone + "'";
		}
		
		if (!(reporter==null)) {
			filter[4] = "reporter='" + reporter + "'";
		}
		
		if (!(importance==null)) {
			filter[5] = "importance='" + importance + "'";
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
	
		query = "SELECT id, shortdesc, priority, status, importance, reporter, owner, component, milestone, type FROM " + tableName(type);
		if (!selQuery.equals("")) {
		 query += " where " + selQuery;
		}		
	
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Ticket d = new Ticket();
				d.setId(rs.getString(1));
				d.setShortDesc(rs.getString(2));
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
	
	
	public static ArrayList<Ticket> getRelatedTickets(String id, String type) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		
		String query="SELECT id, shortdesc, priority, status, reporter, owner, component, milestone FROM " + tableName(type);

		if (id!=null) {
			query +=" where id!=" + id;
		}
		
			
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				Ticket d = new Ticket();
				d.setId(rs.getString(1));
				d.setShortDesc(rs.getString(2));
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
			fields = "id, shortdesc, description, priority, status, reporter, owner, related, component, milestone, type, importance, progress, storypoint";
		}
		else {
			fields = "id, shortdesc, description, priority, status, reporter, owner, related, component, milestone, type, importance, progress";
		}
		
		query = "SELECT " + fields + " FROM "+ tableName(type) + " where id='" + id + "'";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {			
				d.setId(rs.getString(1));
				d.setShortDesc(rs.getString(2));
				d.setDesc(rs.getString(3));
				d.setPriority(rs.getString(4));
				d.setStatus(rs.getString(5));
				d.setReporter(rs.getString(6));
				d.setOwner(rs.getString(7));
				d.setRelated(rs.getString(8));
				d.setComponent(rs.getString(9));
				d.setMilestone(rs.getString(10));
				d.setType(rs.getString(11));
				d.setImportance(rs.getString(12));
				d.setProgress(rs.getString(13));
				if (type.equalsIgnoreCase("requirement")) {
					((Requirement) d).setStoryPoint(rs.getString(14));
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

	public static Integer editTicket(Ticket ticket) throws Exception {
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		Integer row;
	
		 String query = "UPDATE " + tableName(ticket.getType()) +
				" SET shortdesc='" + ticket.getShortDesc() + 
				"', description='" + ticket.getDesc() +
				"', priority='" + TicketDao.nullCheck(ticket.getPriority()) + 
				"', status='" + TicketDao.nullCheck(ticket.getStatus()) + 
				"', reporter='" + TicketDao.nullCheck(ticket.getReporter()) + 
				"', owner='" + TicketDao.nullCheck(ticket.getOwner()) + 
				"', related='" + TicketDao.nullCheck(ticket.getRelated()) + 
				"', component='" + TicketDao.nullCheck(ticket.getComponent()) + 
				"', milestone='" + ticket.getMilestone() + 
				"', progress=" + ticket.getProgress() + 
				", importance='" + TicketDao.nullCheck(ticket.getImportance()) + "'";
		 
		 if(ticket.getType().equalsIgnoreCase("requirement")) {
			 query +=", storypoint=" + ((Requirement) ticket).getStoryPoint();			 
		 }		 
			query +=" WHERE id='" + ticket.getId() + "'";
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
		return row;	
		
	}

	public static ArrayList<Comment> getComments(String id) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Comment> result = new ArrayList<Comment>();
				
		String query = "";
		
		query = "SELECT username, timestamp, comment FROM comments where ticketid='" + id + "'";
		
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
	
	public static void insertComment(String id,Comment comment) throws Exception {
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		
		String query = "Insert into comments (ticketid, username, timestamp,comment) VALUES ('" +
		id +"','" + comment.getUserName() +"','" + comment.getTimeStamp() + "','" +
		comment.getComment() +"')";
	
		try {
			st = con.createStatement();
			st.executeUpdate(query);
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
	}
	
	public static String getSeqID() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String result="";
				
		String query = "";
		
		query = "SELECT nextVal('ticketid_sequence')";
		
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
	
}
