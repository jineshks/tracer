package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.Defect;
import in.espirit.tracer.model.Requirement;
import in.espirit.tracer.model.Task;
import in.espirit.tracer.model.Ticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;


public class TicketDao {
	
	private static Logger logger = Logger.getLogger(TicketDao.class.getName());
	
	public static String nullCheck(String input){
		if (input == null) { return "";}
		return input;
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
	
	public static void registerTicket(Ticket ticket, String loggedUser) throws Exception {
		
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
	    
		String comment = "";
		if (ticket.getNewComments()!=null) {	
			Calendar curDate = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String date = df.format(curDate.getTime());	
			comment = ticket.getNewComments() + " (" + loggedUser + "," + date + ")";
		}		
				
		String query = "INSERT INTO " + tableName(ticket.getType());
				
		if(ticket.getType().equalsIgnoreCase("requirement")) {
			query += " (shortdesc, description, priority, status, reporter, owner, related, component, milestone, comments, importance, progress, storypoint) ";
		}
		else {
			query += " (shortdesc, description, priority, status, reporter, owner, related, component, milestone, comments, importance, progress) ";
		}
		
		if(ticket.getType().equalsIgnoreCase("requirement")) {
			query += "VALUES('" + TicketDao.nullCheck(ticket.getShortDesc())+"','" + 
			TicketDao.nullCheck(ticket.getDesc()) +"','" + TicketDao.nullCheck(ticket.getPriority()) +"','" + TicketDao.nullCheck(ticket.getStatus()) +
			"','" + TicketDao.nullCheck(ticket.getReporter())+"','" + TicketDao.nullCheck(ticket.getOwner())+
			"','" + TicketDao.nullCheck(ticket.getRelated())+"','" + TicketDao.nullCheck(ticket.getComponent())+
			"','" + TicketDao.nullCheck(ticket.getMilestone()) +"','" +  comment  +"','" +  TicketDao.nullCheck(ticket.getImportance()) + 
			"'," + ticket.getProgress()+"," + ((Requirement) ticket).getStoryPoint() + ")";
		}
		else {
			query += "VALUES('" + ticket.getShortDesc()+"','" + 
			TicketDao.nullCheck(ticket.getDesc()) +"','" + TicketDao.nullCheck(ticket.getPriority()) +"','" + TicketDao.nullCheck(ticket.getStatus()) +
			"','" + TicketDao.nullCheck(ticket.getReporter())+"','" + TicketDao.nullCheck(ticket.getOwner())+
			"','" + TicketDao.nullCheck(ticket.getRelated())+"','" + TicketDao.nullCheck(ticket.getComponent())+
			"','" + TicketDao.nullCheck(ticket.getMilestone()) +"','" +  comment  +"','" +  TicketDao.nullCheck(ticket.getImportance()) + 
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
		//return row;	
		//return regDefId;
		}
	
	/* public static String getAllDefectsString(String UserName, String priority, String status) {
		//return "PRIORITY--" + priority + "--" + !(priority==null) + "---" + loop; 
		return query;		
	} */
	
	public static ArrayList<Ticket> getAllTickets(String type, String userName, String priority, String status, String milestone) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		
		String query="";
		String selQuery="";
		String[] filter= new String[4];

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
	
		query = "SELECT id, shortdesc, priority, status, reporter, owner, component, milestone, type FROM " + tableName(type);
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
			fields = "id, shortdesc, description, priority, status, reporter, owner, related, component, milestone, comments, type, importance, progress, storypoint";
		}
		else {
			fields = "id, shortdesc, description, priority, status, reporter, owner, related, component, milestone, comments, type, importance, progress";
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
				d.setComments(rs.getString(11).split("<br>"));
				d.setType(rs.getString(12));
				d.setImportance(rs.getString(13));
				d.setProgress(rs.getString(14));
				if (type.equalsIgnoreCase("requirement")) {
					((Requirement) d).setStoryPoint(rs.getString(15));
				}
				
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
		String comment="";
		
		if (ticket.getNewComments()!=null) {		
			Calendar curDate = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String date = df.format(curDate.getTime());			
			comment = TicketDao.getTicketComments(ticket.getType(),ticket.getId()); 
			if (comment!="") {
				comment += "<br>";
			}
			comment += ticket.getNewComments() + " (" + loggedUser + "," + date + ")";
		}
	//	else {
		//	comment =  TicketDao.getTicket(ticket.getType(),ticket.getId()).getComments();
		//}	
		
		 String query = "UPDATE " + tableName(ticket.getType()) +
				" SET shortdesc='" + ticket.getShortDesc() + 
				"', description='" + ticket.getDesc() +
				"', priority='" + TicketDao.nullCheck(ticket.getPriority()) + 
				"', status='" + TicketDao.nullCheck(ticket.getStatus()) + 
				"', reporter='" + TicketDao.nullCheck(ticket.getReporter()) + 
				"', owner='" + TicketDao.nullCheck(ticket.getOwner()) + 
				"', related='" + TicketDao.nullCheck(ticket.getRelated()) + 
				"', component='" + TicketDao.nullCheck(ticket.getComponent()) + 
				"', milestone='" + ticket.getMilestone();
		 if (comment!="") {
				query+="', comments='" + comment;
		 }
				query+="', progress=" + ticket.getProgress() + 
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

	private static String getTicketComments(String type, String id) throws Exception {
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String result="";
				
		String query = "";
				
		query = "SELECT comments FROM "+ tableName(type) + " where id='" + id + "'";
		
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
			logger.error("Getting ticket comments failed with error " + e.getMessage());
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
