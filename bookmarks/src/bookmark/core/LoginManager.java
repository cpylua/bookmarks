package bookmark.core;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import bookmark.model.User;
import bookmark.util.CookieHelper;
import bookmark.util.JedisKeyBuilder;
import bookmark.util.JedisPoolHelper;

public final class LoginManager {
	public static boolean login(User user) {
		if (Authenticator.check(user))
			return true;
		Jedis jedis = JedisPoolHelper.getResource();
		if (jedis == null)
			return false;

		try {
			String key = JedisKeyBuilder.nameUid(user.getName());
			String uid = jedis.get(key);
			if (uid == null)
				return false;

			String pwd = jedis.get(JedisKeyBuilder.uidPassword(uid));
			if (pwd.equals(user.getPwd())) {
				String cookie = CookieHelper.genCookie();
				Transaction t = jedis.multi();
				t.set(JedisKeyBuilder.uidAuth(uid), cookie);
				t.set(JedisKeyBuilder.authUid(cookie), uid);
				t.exec();
				return true;
			}
			return false;
		} finally {
			JedisPoolHelper.returnResource(jedis);
		}
	}

	public static boolean logout(User user) {
		if (!Authenticator.check(user))
			return false;

		Jedis jedis = JedisPoolHelper.getResource();
		if (jedis == null)
			return false;

		try {
			String uid = jedis.get(JedisKeyBuilder.nameUid(user.getName()));
			String key = JedisKeyBuilder.uidAuth(uid);
			String auth = jedis.get(key);
			Transaction t = jedis.multi();
			t.del(key);
			t.del(JedisKeyBuilder.authUid(auth));
			t.exec();
			return true;
		} finally {
			JedisPoolHelper.returnResource(jedis);
		}
	}

	public static boolean register(User user) {
		Jedis jedis = JedisPoolHelper.getResource();
		if (jedis == null)
			return false;

		try {
			String key = JedisKeyBuilder.nameUid(user.getName());
			if (jedis.exists(key)) {
				return false;
			}
			key = JedisKeyBuilder.nextUid();
			String uid = String.valueOf(jedis.incr(key));

			Transaction t = jedis.multi();
			t.set(JedisKeyBuilder.uidName(uid), user.getName());
			t.set(JedisKeyBuilder.uidPassword(uid), user.getPwd());
			t.set(JedisKeyBuilder.nameUid(user.getName()), uid);
			t.exec();
			return true;
		} finally {
			JedisPoolHelper.returnResource(jedis);
		}
	}

	private LoginManager() {
	}
}
