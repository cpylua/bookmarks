package bookmark.service;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class ServicePasswordHandler implements CallbackHandler {
	@Override
	@SuppressWarnings("deprecation")
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
			if (pwcb.getIdentifer().equals("service")) {
				pwcb.setPassword("servicePW");
				return;
			}
		}
	}
}
