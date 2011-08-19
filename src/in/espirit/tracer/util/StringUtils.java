package in.espirit.tracer.util;

import java.util.Map;
import java.util.Map.Entry;

public class StringUtils {

	public static String nullCheck(String input){
		return (input==null)?"":input;
	}

	public static String templateToMail(String template, Map<String, String> m) {
		String output = template;
		for(Entry<String, String> e :m.entrySet()) {
			output = output.replaceAll(e.getKey(), e.getValue());						
		}	
		return output;		
	}
	
}
