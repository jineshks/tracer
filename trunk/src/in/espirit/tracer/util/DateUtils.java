package in.espirit.tracer.util;

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

}
