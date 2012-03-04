package bookmark.model;

import org.apache.commons.codec.digest.DigestUtils;

public class User implements Comparable<User> {
	private final String name;
	private final String pwd;

	public User(String name, String pwd) {
		if (name == null || pwd == null)
			throw new NullPointerException();
		this.name = name;
		this.pwd = DigestUtils.shaHex(pwd);
	}

	public User(String name) {
		this(name, "");
	}

	public String getName() {
		return name;
	}

	public String getPwd() {
		return pwd;
	}

	@Override
	public String toString() {
		return String.format("%s:%s", name, pwd);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof User))
			return false;
		User u = (User) o;
		return name.equals(u.name) && pwd.equals(u.pwd);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = result * 31 + name.hashCode();
		result = result * 31 + pwd.hashCode();
		return result;
	}

	@Override
	public int compareTo(User o) {
		int diff = name.compareTo(o.name);
		if (diff != 0)
			return diff;

		diff = pwd.compareTo(o.pwd);
		if (diff != 0)
			return diff;

		return 0;
	}
}
