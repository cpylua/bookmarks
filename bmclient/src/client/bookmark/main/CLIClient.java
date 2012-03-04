package client.bookmark.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.axis2.AxisFault;

import client.bookmark.model.Bookmark;
import client.bookmark.service.BookmarkClient;
import client.bookmark.service.BookmarkClientImpl;

public class CLIClient {
	public static void main(String[] args) {
		BookmarkClient client = null;
		System.out.print("Initializing client...");
		try {
			client = new BookmarkClientImpl("John", "19899");
		} catch (AxisFault f) {
			f.printStackTrace();
			return;
		}
		System.out.print("OK\n");

		System.out.print("John is registering...");
		check(client.register());

		System.out.print("John is logging in...");
		check(client.login());

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

		for (Bookmark bm : bms) {
			System.out.printf("Adding bookmark[%s]...", bm);
			check(client.addBookmark(bm));
		}
		System.out.print("Retrieving all bookmarks from server...");
		Set<Bookmark> s = client.getAllBookmarks();
		check(!s.isEmpty());
		System.out.print("Dumping bookmarks...\n");
		for (Bookmark bm : s) {
			System.out.println(bm);
		}

		System.out.printf("Removing bookmark[%s] by Bookmark...", bms.get(0));
		check(client.removeBookmark(bms.get(0)));
		System.out.printf("Removing bookmark[%s] by url...",
				"http://twitter.com");
		check(client.removeBookmark("http://twitter.com"));
		System.out.print("Retrieving all bookmarks from server...");
		s = client.getAllBookmarks();
		check(!s.isEmpty());
		System.out.print("Dumping bookmarks...\n");
		for (Bookmark bm : s) {
			System.out.println(bm);
		}

		System.out.print("John is logging out...");
		check(client.logout());
	}

	private static void check(boolean status) {
		if (!status) {
			System.out.print("failed\n");
			System.exit(-1);
		} else {
			System.out.print("OK\n");
		}
	}
}
