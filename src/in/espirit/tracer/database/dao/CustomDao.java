package in.espirit.tracer.database.dao;

import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

import org.apache.log4j.Logger;





/**
 * This class is used to call Custom.properties file and fetches the value from it. 
 * @author 
 *
 */
public class CustomDao {  
	
	private static PropertyResourceBundle bundle =(PropertyResourceBundle) PropertyResourceBundle.getBundle("custom"); 
		
	private static Logger logger = Logger.getLogger(CustomDao.class.getName());
				
	public static String getResourceMessage(String code) {
		try {
			return bundle.getString(code);
		} catch (MissingResourceException e) {			
			logger.fatal("Error in Resource Bundle" + e.getMessage());
			return "";
		}
	}
		
	public static ArrayList<String> getValues(String key) {		
		Integer num = Integer.parseInt(getResourceMessage(key+".num"));
		ArrayList<String> result = new ArrayList<String>();
		for (int i=1;i<=num;i++) {
			result.add(getResourceMessage(key+"."+i));
		}		
		return result;
	}
	
	
	public static int getIndexValue(String key, String value) {		
	
		Integer num = Integer.parseInt(getResourceMessage(key+".num"));
		for (int i=1;i<=num;i++) {
			if (getResourceMessage(key+"."+i).equalsIgnoreCase(value)) {
				return i;
			}
		}
		return -1;				
	}
}


