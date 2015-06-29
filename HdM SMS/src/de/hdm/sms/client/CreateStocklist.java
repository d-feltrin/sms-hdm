package de.hdm.sms.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.LoginInfo;
import de.hdm.sms.shared.bo.User;

public class CreateStocklist extends VerticalPanel {
	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private User u = new User();
	private LoginInfo loginInfo;
	private Label stocklistNameLabel = new Label("Stuecklistenname");
	private TextBox stocklistNameTextBox = new TextBox();
	private VerticalPanel metaDataPanel = new VerticalPanel();

	public CreateStocklist() {

	}

	public User getUserIdByEMailAdress(String eMailAdress) {

		asyncObj.getOneUserIdByEmailAdress(eMailAdress,
				new AsyncCallback<User>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(User result) {
						u.setId(result.getId());
						u.setFirstName(result.getFirstName());
						u.setLastName(result.getLastName());
						u.seteMailAdress(result.geteMailAdress());

					}

				});
		return u;

	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public void onLoad() {
		getUserIdByEMailAdress(loginInfo.getEmailAddress());
		metaDataPanel.add(stocklistNameLabel);
		metaDataPanel.add(stocklistNameTextBox);
		RootPanel.get("rightside").add(metaDataPanel);

	}
}
