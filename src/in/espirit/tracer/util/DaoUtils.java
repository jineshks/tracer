package in.espirit.tracer.util;

import in.espirit.tracer.database.connection.ConnectionFactory;
import in.espirit.tracer.database.connection.ConnectionPool;
import java.sql.Connection;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DaoUtils {

	private static Logger logger = Logger.getLogger(DaoUtils.class.getName());
	
	public static boolean executeUpdate(String query) throws Exception{
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
