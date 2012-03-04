package bookmark.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bookmark.model.Bookmark;

public class BookmarkServiceTest {
	@Test
	public void testService() {
		BookmarkService bms = new BookmarkServiceImpl();
		String name = "Charles";
		String pwd = "pwd";
		String url = "http://reddit.com";
		String desc = "reddit";

		assertTrue(bms.regsiter(name, pwd));
		assertTrue(bms.login(name, pwd));
		assertTrue(bms.addBookmark(name, url, desc));
		Bookmark bm = new Bookmark(url, desc);
		Bookmark[] s = bms.getAllBookmark(name);
		assertEquals(1, s.length);
		assertTrue(bm.equals(s[0]));
		assertTrue(bms.removeBookmark(name, url));
		assertTrue(bms.logout(name));
		assertFalse(bms.regsiter(name, pwd));
	}
}
