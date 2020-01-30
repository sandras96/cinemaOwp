package cinemaDAO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {
	
	public static String DATETIME_FORMAT = "dd-MM-yyyy HH:mmM:ss";

	public static Date parseDate(String date) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
		return dateFormat.parse(date);
	}
	
	public static String formatDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
		return dateFormat.format(date);
	}
}
