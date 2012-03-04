package bookmark.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import bookmark.model.Bookmark;
import bookmark.model.User;

public class BookmarkManagerTest {
	@Test
	public void testBookmark() {
		User user = new User("Charles", "zombie.fml");
		assertTrue(LoginManager.register(user));
		assertTrue(LoginManager.login(user));

		BookmarkManager bmm = new BookmarkManager(user);
		@SuppressWarnings("serial")
		List<Bookmark> bms = new ArrayList<Bookmark>() {
			{
				add(new Bookmark("http://www.reddit.com", "reddit"));
				add(new Bookmark("http://redis.io", "redis is great"));
				add(new Bookmark("http://news.ycombinator.com", "Hacker News"));
				add(new Bookmark("http://mail.google.com", "GMail"));
				add(new Bookmark("http://twitter.com", "twitter"));
			}
		};
		Set<Bookmark> s = bmm.getAll();
		assertEquals(0, s.size());

		for (Bookmark bm : bms)
			assertTrue(bmm.add(bm));
		s = bmm.getAll();
		assertEquals(bms.size(), s.size());
		assertTrue(s.containsAll(bms));

		Bookmark v = bms.get(0);
		bmm.remove(v);
		s = bmm.getAll();
		assertEquals(bms.size() - 1, s.size());
		assertTrue(bms.containsAll(s));
		assertFalse(s.contains(v));

		for (int i = 1; i < bms.size(); i++) {
			bmm.remove(bms.get(i));
			s = bmm.getAll();
			assertEquals(bms.size() - 1 - i, s.size());
		}

		for (Bookmark bm : bms)
			assertTrue(bmm.add(bm));
		assertTrue(LoginManager.logout(user));
		s = bmm.getAll();
		assertEquals(0, s.size());
	}
}
