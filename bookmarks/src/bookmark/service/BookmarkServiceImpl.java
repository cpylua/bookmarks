package bookmark.service;

import bookmark.core.BookmarkManager;
import bookmark.core.LoginManager;
import bookmark.model.Bookmark;
import bookmark.model.User;

public class BookmarkServiceImpl implements BookmarkService {

	@Override
	public boolean regsiter(String name, String pwd) {
		User user = null;
		try {
			user = new User(name, pwd);
		} catch (NullPointerException e) {
			return false;
		}
		return LoginManager.register(user);
	}

	@Override
	public boolean login(String name, String pwd) {
		User user = null;
		try {
			user = new User(name, pwd);
		} catch (NullPointerException e) {
			return false;
		}
		return LoginManager.login(user);
	}

	@Override
	public boolean logout(String name) {
		User user = null;
		try {
			user = new User(name);
		} catch (NullPointerException e) {
			return false;
		}
		return LoginManager.logout(user);
	}

	@Override
	public boolean addBookmark(String name, String url, String desc) {
		try {
			User user = new User(name);
			Bookmark bm = new Bookmark(url, desc);
			BookmarkManager bmm = new BookmarkManager(user);
			return bmm.add(bm);
		} catch (NullPointerException e) {
			return false;
		}
	}

	@Override
	public boolean removeBookmark(String name, String url) {
		try {
			User user = new User(name);
			BookmarkManager bmm = new BookmarkManager(user);
			return bmm.remove(url);
		} catch (NullPointerException e) {
			return false;
		}
	}

	@Override
	public Bookmark[] getAllBookmark(String name) {
		User user = null;
		try {
			user = new User(name);
			BookmarkManager bmm = new BookmarkManager(user);
			return bmm.getAll().toArray(new Bookmark[0]);
		} catch (NullPointerException e) {
			return new Bookmark[0];
		}
	}
}
