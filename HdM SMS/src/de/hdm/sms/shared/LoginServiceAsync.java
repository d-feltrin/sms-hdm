package de.hdm.sms.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void getUserInfo(String uri, AsyncCallback<LoginInfo>callback);
}
