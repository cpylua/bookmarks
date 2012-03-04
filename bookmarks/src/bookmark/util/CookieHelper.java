package bookmark.util;

import java.util.UUID;

public class CookieHelper {
	public static String genCookie() {
		return UUID.randomUUID().toString();
	}
}
