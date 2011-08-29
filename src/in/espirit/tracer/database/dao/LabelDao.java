package in.espirit.tracer.database.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeSet;

import org.apache.log4j.Logger;

public class LabelDao {
	
	private static Logger logger = Logger.getLogger(CustomDao.class.getName());
	

	public LinkedHashMap<String, String> getLabelItems(String path) {
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		Properties pro = new Properties();
		try {
			//InputStream is = this.getClass().getClassLoader().getResourceAsStream("LabelResources.properties");	
	
			FileInputStream is = new FileInputStream(path + "/WEB-INF/classes/LabelResources.properties");	
			
			pro.load(is);
			Enumeration<Object> keys = pro.keys();
			
			TreeSet<String> ts= new TreeSet<String>();
			
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();							
				ts.add((String) key); 						
			}
			
			for (String s : ts) {
				String value = (String) pro.get(s);
				result.put(s, value);	
			}
			
			
		} catch (FileNotFoundException e) {
			logger.error("No file found - labelresources.properties " + e.getMessage());
		} catch (IOException e) {
			logger.error("Not able to do IO operation for labelresources.properties" + e.getMessage());	
		}		
		return result;
	}
	
	public static boolean updateProperties(HashMap<String, String> pair,String path) {
		boolean flag = false;		
		try {
			Properties pro = new Properties();		
				
			for(Entry<String, String> entry : pair.entrySet()) {
				pro.put(entry.getKey(), entry.getValue());				
			}
			pro.store(new FileOutputStream(path + "/WEB-INF/classes/LabelResources.properties"), "Edited");   //IF needed we can add user name..but first see the comments in the file				
			flag = true;				
		} catch (FileNotFoundException e) {
			logger.error("No file found - labelresources.properties " + e.getMessage());
		} catch (IOException e) {
			logger.error("Not able to do IO operation for labelresources.properties " + e.getMessage());
			
		}
			
		return flag;
	}
	
}
