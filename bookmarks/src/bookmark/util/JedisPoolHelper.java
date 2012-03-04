package bookmark.util;

import org.apache.commons.pool.impl.GenericObjectPool.Config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public final class JedisPoolHelper {
	private static final JedisPool pool = new JedisPool(new Config(),
			"localhost");

	public static Jedis getResource() {
		return pool.getResource();
	}

	public static void returnResource(Jedis res) {
		pool.returnResource(res);
	}
}
