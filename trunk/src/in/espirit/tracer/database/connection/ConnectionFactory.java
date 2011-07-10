package in.espirit.tracer.database.connection;

/**
 * ConnectionFactory that provides Connection Pool.
 */
public class ConnectionFactory {
	private static ConnectionPool pool = new ConnectionPool();

	/**
	 * Method to get the connection pool
	 * 
	 * @return pool - ConnectionPool
	 */
	public static ConnectionPool getPool() {
		return pool;
	}
}
