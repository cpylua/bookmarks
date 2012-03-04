package bookmark.util;

public class JedisKeyBuilder {
	public static String nextUid() {
		return "global:nextUserId";
	}

	public static String uidName(String id) {
		return String.format("uid:%s:username", id);
	}

	public static String uidPassword(String id) {
		return String.format("uid:%s:password", id);
	}

	public static String nameUid(String name) {
		return String.format("username:%s:uid", name);
	}

	public static String uidBookmarks(String id) {
		return String.format("uid:%s:bookmarks", id);
	}

	public static String uidAuth(String id) {
		return String.format("uid:%s:auth", id);
	}

	public static String authUid(String cookie) {
		return String.format("auth:%s:uid", cookie);
	}
}
