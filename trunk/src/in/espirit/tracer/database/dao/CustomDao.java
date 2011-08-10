package in.espirit.tracer.database.dao;

import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;




/**
 * This class is used to call Custom.properties file and fetches the value from it. 
 * @author 
 *
 */
public class CustomDao {  
	
	private static PropertyResourceBundle bundle =(PropertyResourceBundle) PropertyResourceBundle.getBundle("custom"); 
		
	
				
	public static String getResourceMessage(String code) {
		try {
			return bundle.getString(code);
		} catch (MissingResourceException e) {
			throw new RuntimeException(e.getMessage());
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


