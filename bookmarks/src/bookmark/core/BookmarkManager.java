package bookmark.core;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import bookmark.model.Bookmark;
import bookmark.model.User;
import bookmark.util.JedisKeyBuilder;
import bookmark.util.JedisPoolHelper;

public class BookmarkManager {
	private final User user;
	private static final Set<Bookmark> EMPTY_BOOKMARK_SET = Collections
	.emptySet();

	public BookmarkManager(User user) {
		if (user == null)
			throw new NullPointerException("user can not be null!");
		this.user = user;
	}

	public boolean add(Bookmark bm) {
		if (!Authenticator.check(user))
			return false;
		Jedis jedis = JedisPoolHelper.getResource();
		if (jedis == null)
			return false;

		try {
			String uid = jedis.get(JedisKeyBuilder.nameUid(user.getName()));
			jedis.hset(JedisKeyBuilder.uidBookmarks(uid), bm.getUrl(),
					bm.getDesc());
			return true;
		} finally {
			JedisPoolHelper.returnResource(jedis);
		}
	}

	public boolean add(String url, String desc) {
		Bookmark bm = new Bookmark(url, desc);
		return add(bm);
	}

	public boolean remove(String url) {
		if (!Authenticator.check(user))
			return false;
		Jedis jedis = JedisPoolHelper.getResource();
		if (jedis == null)
			return false;

		try {
			String uid = jedis.get(JedisKeyBuilder.nameUid(user.getName()));
			Long ret = jedis.hdel(JedisKeyBuilder.uidBookmarks(uid), url);
			return (ret.longValue() == 1L);
		} finally {
			JedisPoolHelper.returnResource(jedis);
		}
	}

	public boolean remove(Bookmark bm) {
		return remove(bm.getUrl());
	}

	public Set<Bookmark> getAll() {
		if (!Authenticator.check(user))
			return EMPTY_BOOKMARK_SET;
		Jedis jedis = JedisPoolHelper.getResource();
		if (jedis == null)
			return EMPTY_BOOKMARK_SET;

		try {
			String uid = jedis.get(JedisKeyBuilder.nameUid(user.getName()));
			Map<String, String> ret = jedis.hgetAll(JedisKeyBuilder
					.uidBookmarks(uid));
			Set<Bookmark> bms = new LinkedHashSet<Bookmark>(ret.size());
			for (Map.Entry<String, String> entry : ret.entrySet()) {
				bms.add(new Bookmark(entry.getKey(), entry.getValue()));
			}
			return Collections.unmodifiableSet(bms);
		} finally {
			JedisPoolHelper.returnResource(jedis);
		}
	}
}
