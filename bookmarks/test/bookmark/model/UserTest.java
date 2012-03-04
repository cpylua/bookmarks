package bookmark.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserTest {
	@Test
	public void testUser() {
		User i = new User("Charles", "zombie.fml");
		User j = new User("Lisa", "1863");
		assertFalse(i.hashCode() == j.hashCode());
		User k = new User("Charles", "zombie.fml");
		assertTrue(i.hashCode() == k.hashCode());
		k = new User("Charles", "1863");
		assertFalse(i.hashCode() == k.hashCode());
		assertFalse(j.hashCode() == k.hashCode());
	}
}
