package bookmark.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BookmarkTest {
	@Test
	public void testBookmark() {
		Bookmark i = new Bookmark("http://www.reddit.com", "reddit");
		Bookmark j = new Bookmark("http://redis.io", "redis is great");
		assertFalse(i.hashCode() == j.hashCode());
		Bookmark k = new Bookmark("http://redis.io", "reddit");
		assertFalse(i.hashCode() == k.hashCode());
		assertFalse(j.hashCode() == k.hashCode());
		k = new Bookmark("http://redis.io", "redis is great");
		assertTrue(j.hashCode() == k.hashCode());
	}
}
