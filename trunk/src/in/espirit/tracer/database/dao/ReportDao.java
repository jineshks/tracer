package in.espirit.tracer.database.dao;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

public class ReportDao {

	private static Logger logger = Logger.getLogger(MilestoneDao.class.getName());

	public static LinkedHashMap<String, Integer> getSprintStoryPoint() throws Exception{
		ConnectionPool pool = ConnectionFactory.getPool();
		Connection con = pool.getConnection();
		Statement st = null;
		ResultSet rs = null;
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		
		String query = "select f_milestone, sum(f_storypoint) from t_requirementdetails GROUP BY f_milestone order by f_milestone ASC";
	
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
				
			while (rs.next()) {
				if (rs.getString(1).equalsIgnoreCase("")) {
					result.put("--", rs.getInt(2));
				}
				else {
					result.put(rs.getString(1), rs.getInt(2));
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
			logger.fatal(e.getMessage());
			throw new Exception(e.getMessage());
		} // catch Close
		finally {
			if (con != null)
				con.close(); // close connection		
		}// end finally	

	return result;
	}
	
}
