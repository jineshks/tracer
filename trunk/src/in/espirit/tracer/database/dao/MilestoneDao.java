package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import in.espirit.tracer.model.Ticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



public class MilestoneDao {
	public static ArrayList<Ticket> getList(String key) throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		
		String query = "SELECT id, shortdesc, priority, status, reporter, owner, component, milestone, type " +
				"FROM defectdetails where milestone='" + key +"' " +
				"UNION ALL " +
				"select id, shortdesc, priority, status, reporter, owner, component, milestone, type " +
				"from taskdetails where milestone='" + key + "' " +
				"UNION ALL " +
				"select id, shortdesc, priority, status, reporter, owner, component, milestone, type " +
				"from requirementdetails where milestone='" + key + "'";
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