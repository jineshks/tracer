package in.espirit.tracer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static String getDatetimeInFormat(String format) {
		Calendar curDate = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		String date = df.format(curDate.getTime());
		return date;
	}
	
	public static ArrayList<String> getInBetweenDates(String startDate, String endDate) throws ParseException {
		
		Calendar date = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		ArrayList<String> result = new ArrayList<String>();  // The return will be list of dates in the format yyyy-mm-dd.
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date stDate = df.parse(startDate);
		Date enDate = df.parse(endDate);
			
		date.setTime(stDate);
		end.setTime(enDate);
		while (date.before(end) || date.equals(end)) {
			result.add(df.format(date.getTime()));
			date.add(Calendar.DATE, 1);		
		}	
		return result;
	}

	
	public static String daysRemaining(Calendar endDate) {  
		  Calendar date = Calendar.getInstance();
		  long daysBetween = 0;  
		  if (date.before(endDate)) {
			  while (date.before(endDate)) {  
				  date.add(Calendar.DAY_OF_MONTH, 1);  
				  daysBetween++;  
			  } 
		  }
		  else {
			  while (date.after(endDate)) {  
				  date.add(Calendar.DAY_OF_MONTH, -1);  
				  daysBetween--;  
			  } 		  
		  }		  
		  
		  return String.valueOf(daysBetween);  
		}  	
	
	public static Calendar convertStringToCalendar(String date, String format) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatter.parse(date));
		return cal;		
	}
}
