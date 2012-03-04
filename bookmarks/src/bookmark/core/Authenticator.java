package bookmark.core;

import redis.clients.jedis.Jedis;
import bookmark.model.User;
import bookmark.util.JedisKeyBuilder;
import bookmark.util.JedisPoolHelper;

final class Authenticator {
	static boolean check(User user) {
		Jedis jedis = JedisPoolHelper.getResource();
		if (jedis == null)
			return false;

		try {
			String key = JedisKeyBuilder.nameUid(user.getName());
			String uid = jedis.get(key);
			if (uid != null) {
				key = JedisKeyBuilder.uidAuth(uid);
				String auth = jedis.get(key);
				if (auth != null) {
					key = JedisKeyBuilder.authUid(auth);
					String id = jedis.get(key);
					if (uid.equals(id))
						return true;
				}
			}
			return false;
		} finally {
			JedisPoolHelper.returnResource(jedis);
		}
	}

	private Authenticator() {
	}
}
