package bookmark.service;

import bookmark.model.Bookmark;

public interface BookmarkService {
	public boolean regsiter(String name, String pwd);

	public boolean login(String name, String pwd);

	public boolean logout(String name);

	public boolean addBookmark(String name, String url, String desc);

	public boolean removeBookmark(String name, String url);

	public Bookmark[] getAllBookmark(String name);
}
