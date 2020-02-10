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
	public static int createParamBetweenMin(String param) {
		if (param == null || param.isEmpty()) {
			return 0;
		} else {
			return Integer.parseInt(param);
		}
	}
	public static int createParamBetweenMax(String param) {
		if (param == null || param.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return Integer.parseInt(param);
		}
	}
	
	public static double createParamPriceFrom(String param) {
		if (param == null || param.isEmpty()) {
			return 0;
		} else {
			return Double.valueOf(param);
		}
	}
	public static double createParamPriceTo(String param) {
		if (param == null || param.isEmpty()) {
			return Double.MAX_VALUE;
		} else {
			return Double.valueOf(param);
		}
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