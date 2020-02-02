package cinemaDAO;

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
}