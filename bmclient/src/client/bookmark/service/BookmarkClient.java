package client.bookmark.service;

import java.util.Set;

import client.bookmark.model.Bookmark;



public interface BookmarkClient {
	public boolean register();

	public boolean login();

	public boolean logout();

	public boolean addBookmark(String url, String desc);

	public boolean addBookmark(Bookmark bm);

	public boolean removeBookmark(String url);

	public boolean removeBookmark(Bookmark bm);

	public Set<Bookmark> getAllBookmarks();
}
