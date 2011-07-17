package in.espirit.tracer.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
	public static String getDatetimeInFormat(String format) {
		Calendar curDate = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		String date = df.format(curDate.getTime());
		return date;
	}

}
