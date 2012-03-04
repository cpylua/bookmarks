package client.bookmark.service;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.description.PolicyInclude;
import org.apache.neethi.Policy;
import org.apache.rampart.policy.model.CryptoConfig;
import org.apache.rampart.policy.model.RampartConfig;

import client.bookmark.model.Bookmark;

public class BookmarkClientImpl implements BookmarkClient {
	private final String user;
	private final String password;
	private final BookmarkServiceStub stub;
	private static final Set<Bookmark> EMPTY_BOOKMARK_SET = Collections
			.emptySet();

	/**
	 * Initialize a service stub
	 * 
	 * @param user
	 *            User name, can not be null.
	 * @param password
	 *            User password, can be set later.
	 * @throws AxisFault
	 *             If an exception arises when initializing service stub.
	 * @throws NullPointerException
	 *             If user or password is null.
	 */
	@SuppressWarnings("deprecation")
	public BookmarkClientImpl(String user, String password) throws AxisFault {
		// To be able to load the client configuration from axis2.xml
		ConfigurationContext ctx = ConfigurationContextFactory
		.createConfigurationContextFromFileSystem("client-repo", null);
		stub = new BookmarkServiceStub(ctx,
		"http://localhost:8080/axis2/services/BookmarkService");
		ServiceClient sc = stub._getServiceClient();
		sc.engageModule("rampart");

		Policy rampartConfig = getRampartConfig();
		sc.getAxisService()
		.getPolicyInclude()
		.addPolicyElement(PolicyInclude.AXIS_SERVICE_POLICY,
				rampartConfig);

		if (user == null || password == null)
			throw new NullPointerException();
		this.user = user;
		this.password = password;
	}

	@Override
	public boolean register() {
		if (user == null || password == null)
			return false;

		BookmarkServiceStub.Regsiter reg = new BookmarkServiceStub.Regsiter();
		reg.setName(user);
		reg.setPwd(password);
		try {
			BookmarkServiceStub.RegsiterResponse res = stub.regsiter(reg);
			return res.get_return();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean login() {
		if (user == null || password == null) {
			return false;
		}

		BookmarkServiceStub.Login login = new BookmarkServiceStub.Login();
		login.setName(user);
		login.setPwd(password);
		try {
			BookmarkServiceStub.LoginResponse res = stub.login(login);
			return res.get_return();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean logout() {
		if (user == null)
			return false;

		BookmarkServiceStub.Logout logout = new BookmarkServiceStub.Logout();
		logout.setName(user);
		try {
			BookmarkServiceStub.LogoutResponse res = stub.logout(logout);
			return res.get_return();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean addBookmark(String url, String desc) {
		if (user == null)
			return false;

		BookmarkServiceStub.AddBookmark add = new BookmarkServiceStub.AddBookmark();
		add.setName(user);
		add.setDesc(desc);
		add.setUrl(url);
		try {
			BookmarkServiceStub.AddBookmarkResponse res = stub.addBookmark(add);
			return res.get_return();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean addBookmark(Bookmark bm) {
		return addBookmark(bm.getUrl(), bm.getDesc());
	}

	@Override
	public boolean removeBookmark(String url) {
		if (user == null)
			return false;

		BookmarkServiceStub.RemoveBookmark remove = new BookmarkServiceStub.RemoveBookmark();
		remove.setName(user);
		remove.setUrl(url);
		try {
			BookmarkServiceStub.RemoveBookmarkResponse res = stub
			.removeBookmark(remove);
			return res.get_return();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean removeBookmark(Bookmark bm) {
		return removeBookmark(bm.getUrl());
	}

	@Override
	public Set<Bookmark> getAllBookmarks() {
		if (user == null)
			return EMPTY_BOOKMARK_SET;

		BookmarkServiceStub.GetAllBookmark getall = new BookmarkServiceStub.GetAllBookmark();
		getall.setName(user);
		try {
			BookmarkServiceStub.GetAllBookmarkResponse res = stub
			.getAllBookmark(getall);
			BookmarkServiceStub.Bookmark[] r = res.get_return();
			if (r == null || r.length == 0)
				return EMPTY_BOOKMARK_SET;

			Set<Bookmark> ret = new LinkedHashSet<Bookmark>(r.length);
			for (BookmarkServiceStub.Bookmark i : r) {
				Bookmark j = new Bookmark(i.getUrl(), i.getDesc());
				ret.add(j);
			}
			return Collections.unmodifiableSet(ret);
		} catch (Exception e) {
			return EMPTY_BOOKMARK_SET;
		}
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	private static Policy getRampartConfig() {
		RampartConfig rampartConfig = new RampartConfig();
		rampartConfig.setUser("client");
		rampartConfig
		.setPwCbClass("client.bookmark.service.BookmarkPasswordHandler");

		CryptoConfig sigCrypto = new CryptoConfig();
		sigCrypto
		.setProvider("org.apache.ws.security.components.crypto.Merlin");
		Properties props = new Properties();
		props.setProperty("org.apache.ws.security.crypto.merlin.keystore.type",
		"JKS");
		props.setProperty("org.apache.ws.security.crypto.merlin.file",
		"keys/client.jks");
		props.setProperty(
				"org.apache.ws.security.crypto.merlin.keystore.password",
		"clientPW");
		sigCrypto.setProp(props);
		rampartConfig.setSigCryptoConfig(sigCrypto);

		Policy policy = new Policy();
		policy.addAssertion(rampartConfig);
		return policy;
	}
}
