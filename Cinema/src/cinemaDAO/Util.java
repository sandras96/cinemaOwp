package cinemaDAO;

import java.util.Date;

public class Util {

	
	public static String createParam(String param) {
		if (param == null || param.isEmpty()) {
			return "%%";
		} else {
			return "%" + param + "%";
		}
	}
	
	public static boolean isNumeric(String param) {
		try {
			Integer.parseInt(param);
		} catch (Exception e) {
			return false;
		}
		
		return true;
		
	}
	
	
	public static long dateParam(String param) {
		if (param == null || param.isEmpty()) {
			return 0;
		} else {
			return Long.parseLong(param);
		}
	}
	
	public static long dateParamTo(String param) {
		if (param == null || param.isEmpty()) {
			return Long.MAX_VALUE;
		} else {
			return Long.parseLong(param);
					}
			}
	
}