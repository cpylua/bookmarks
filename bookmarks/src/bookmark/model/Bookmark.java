package bookmark.model;

public class Bookmark implements Comparable<Bookmark> {
	private final String url;
	private final String desc;

	public Bookmark(String url, String desc) {
		if (url == null || desc == null)
			throw new NullPointerException();
		this.url = url;
		this.desc = desc;
	}

	public String getUrl() {
		return url;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", url, desc);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Bookmark))
			return false;
		Bookmark bm = (Bookmark) o;
		return bm.url.equals(url) && bm.desc.equals(desc);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + url.hashCode();
		result = 31 * result + desc.hashCode();
		return result;
	}

	@Override
	public int compareTo(Bookmark o) {
		int diff = url.compareTo(o.url);
		if (diff != 0)
			return diff;

		diff = desc.compareTo(o.desc);
		if (diff != 0)
			return diff;

		return 0;
	}
}
