package cinemaDAO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatDate {

	public static String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";

	public static Date parseDate(String date) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
		return dateFormat.parse(date);
	}

	public static String formatDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
		return dateFormat.format(date);
	}

	public static Calendar dateToCalendar(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return calendar;

	}
	
	public static Calendar dateEndCalendar(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		return calendar;

	}

	public static Date calendarToDate(Calendar calendar) {
		return calendar.getTime();
	}

}
