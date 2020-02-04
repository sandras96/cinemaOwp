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
	
	public static long createDateParam(String param, String direction) {
		if (Util.isNumeric(param)) {
			return Integer.parseInt(param);
		} else {
			if (direction.equals("max")) {
				return Long.MAX_VALUE; // vratimo maksimalan integer
			} else {
				return 0; // minimalan datum, epoch time od 1.1.1970
			}
		}
	}
}