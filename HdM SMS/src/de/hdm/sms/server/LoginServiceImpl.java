package de.hdm.sms.server;

import de.hdm.sms.server.db.UserMapper;
import de.hdm.sms.shared.LoginInfo;
import de.hdm.sms.shared.LoginService;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Meldet den User über den Google Accounts Service an.
 * 
 */
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	private static final long serialVersionUID = -1682217411238580183L;

	@Override
	public LoginInfo getUserInfo(String uri) {
		// Nutzer von Google holen
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo loginInfo = new LoginInfo();

		if (user != null) {

			UserMapper userMapper = UserMapper.userMapper();

			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setLogoutUrl(userService.createLogoutURL(uri));

			// In der Datenbank nachsehen, ob User schon registiert ist.
			de.hdm.sms.shared.bo.User 
			User = (userMapper
					.getOneUserIdByEmailAdress(loginInfo.getEmailAddress()));
			if (User != null) {
				loginInfo.setUser(User);
			}
			// User 'registrieren'
			else {
				User = new de.hdm.sms.shared.bo.User();
				User.seteMailAdress(loginInfo.getEmailAddress());
				userMapper.insertUser(User);
				loginInfo.setUser(userMapper.
						getOneUserIdByEmailAdress(loginInfo.getEmailAddress()));
			}

		} else {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(uri));
		}
		return loginInfo;

	}
}
