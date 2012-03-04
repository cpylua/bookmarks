package bookmark.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import bookmark.model.User;
import bookmark.util.JedisKeyBuilder;
import bookmark.util.JedisPoolHelper;

public class LoginTest {
	private static Jedis jedis;

	@BeforeClass
	public static void setUp() throws Exception {
		jedis = JedisPoolHelper.getResource();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		JedisPoolHelper.returnResource(jedis);
	}

	@Test
	public void testRegister() {
		User user = new User("Charles", "zombie.fml");
		LoginManager.register(user);
		String uid = jedis.get(JedisKeyBuilder.nameUid(user.getName()));
		String name = jedis.get(JedisKeyBuilder.uidName(uid));
		String pwd = jedis.get(JedisKeyBuilder.uidPassword(uid));
		assertEquals(user.getName(), name);
		assertEquals(user.getPwd(), pwd);
		assertFalse(LoginManager.register(user));
	}

	@Test
	public void testLoginOut() {
		User user = new User("Lisa", "1863");
		boolean ret = LoginManager.login(user);
		assertFalse(ret);

		user = new User("Charles", "1863");
		ret = LoginManager.login(user);
		assertFalse(ret);

		user = new User("Charles", "zombie.fml");
		ret = LoginManager.login(user);
		assertTrue(ret);

		ret = LoginManager.login(user);
		assertTrue(ret);

		user = new User("Charles", "1863");
		ret = LoginManager.login(user);
		assertTrue(ret);

		user = new User("Lisa", "1863");
		ret = LoginManager.login(user);
		assertFalse(ret);

		// logout
		ret = LoginManager.logout(user);
		assertFalse(ret);

		user = new User("Charles", "zombie.fml");
		ret = LoginManager.logout(user);
		assertTrue(ret);
		ret = LoginManager.logout(user);
		assertFalse(ret);

		user = new User("Lisa", "1863");
		LoginManager.register(user);
		ret = LoginManager.login(user);
		assertTrue(ret);
		ret = LoginManager.logout(user);
		assertTrue(ret);
	}
}
