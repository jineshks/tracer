package in.espirit.tracer.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * ConnectionPool represents a pool of Database Connections
 * <p>
 * 
 * <pre>
 * Usage: 
 * 	ConnectionPool pool = ConnectionFactory.getPool();
 * 	Connection con = pool.getConnection(); //represents same java.sql.Connection
 * 	Statement stmt = con.createStatement();
 * </pre>
 * 
 * </p>
 * 
 */
public class ConnectionPool {
	private int poolSize;
	private ArrayList connections = new ArrayList();
	private static ResourceBundle bundle = ResourceBundle.getBundle("dbpool");

	/**
	 * Constructor for ConnectionPool
	 */
	protected ConnectionPool() {
		super();
		poolSize = Integer.parseInt(getResourceMessage("poolSize"));
	}

	/**
	 * Method to get a DB Connection from the pool.
	 * 
	 * @return dbcon - Database Connection object
	 * @throws SQLException
	 */
	public synchronized Connection getConnection() throws SQLException {
		for (int i = 0; i < connections.size(); i++) {
			DBConnection dbcon = (DBConnection) connections.get(i);
			if (dbcon.isFree()) {
				dbcon.setUse();
				return dbcon;
			}
		}

		try {
			Class.forName(getResourceMessage("driverClass"));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}

		DBConnection dbcon = new DBConnection(DriverManager.getConnection(
				getResourceMessage("dburl"), getResourceMessage("dbuser"),
				getResourceMessage("dbpwd")), this);
		if (connections.size() < poolSize) {
			connections.add(dbcon);
		}
		dbcon.setUse();
		return dbcon;
	}

	/**
	 * Method called from DBConnection to return the connection used. Will be
	 * invoked when close operation is called on the DBConection.
	 * 
	 * @param retcon
	 *            - DBConnection that is returned.
	 * @throws SQLException
	 */
	protected void returnConnection(DBConnection retcon) throws SQLException {
		if (connections.contains(retcon)) {
			retcon.setFree();
		} else {
			if (!retcon.getConnection().isClosed()) {
				retcon.getConnection().close();
			}
		}
	}

	/**
	 * Method to close all connections when the object is Garbage Collected.
	 * Note: Static objects are never Garbage collected. This was not true prior
	 * to Java 1.4 versions.
	 */
	protected void finalize() {
		for (int i = 0; i < connections.size(); i++) {
			Connection con = (Connection) connections.get(i);
			try {
				if (!con.isClosed())
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connections.remove(con);
		}
	}

	/**
	 * Method to get resource from a bundle
	 * 
	 * @param code
	 * @return the value for the input key
	 */
	private static String getResourceMessage(String code) {
		try {
			return bundle.getString(code);
		} catch (MissingResourceException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
