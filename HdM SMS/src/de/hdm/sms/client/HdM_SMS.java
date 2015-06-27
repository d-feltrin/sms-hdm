package de.hdm.sms.client;

import de.hdm.sms.client.ClientsideSettings;
import de.hdm.sms.shared.LoginInfo;
import de.hdm.sms.shared.LoginService;
import de.hdm.sms.shared.LoginServiceAsync;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.sms.client.gui.ImageSMS;
import de.hdm.sms.client.gui.Impressum;
import de.hdm.sms.client.gui.Startside;

public class HdM_SMS extends VerticalPanel implements EntryPoint {

	private VerticalPanel bottomPanel = new VerticalPanel();
	private Label aboutLabel = new Label("Impressum");

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();

	private Label loginLabel = new Label(
			"Bitte melden Sie sich mit ihrem Google Account an, um Zugang zur Applikation zu bekommen. Falls Sie noch keinen Benutzeraccount haben, benutzern Sie bitte den Button Registrieren um einen zu erstellen.");

	private Anchor signInLink = new Anchor("Anmelden");
	private Anchor signOutLink = new Anchor("Abmelden");	
	private Anchor registerLink = new Anchor("Registrieren");

	public HdM_SMS() {

	}

	Logger logger = ClientsideSettings.getLogger();

	public void loadStartside() {

		// Anzeige des angemeldeten Benutzers
		Label loggedInlabel = new Label("Angemeldet als: "
				+ loginInfo.getEmailAddress());

		// Logout Url holen
		signOutLink.setHref(loginInfo.getLogoutUrl());
		Startside startside = new Startside();
		startside.setLoginInfo(loginInfo);
		RootPanel.get("leftside").clear();
		RootPanel.get("leftside").add(startside);
		RootPanel.get("leftside").add(loggedInlabel);
		RootPanel.get("leftside").add(signOutLink);
		

	}

	public Widget loadLogin() {
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);		
		loginPanel.add(registerLink);
		RootPanel.get("leftside").add(loginPanel);
		return loginPanel;
	}

	// ONLOAD
	// ########################################################################################################

	public void onModuleLoad() {
		registerLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("rightside").clear();
				RootPanel.get("rightside").add(new CreateUser());
				
			}
		});
		

		aboutLabel.addStyleName("impressum");
		bottomPanel.add(aboutLabel);

		RootPanel.get("rightside").add(new ImageSMS());
		RootPanel.get("bottom").add(aboutLabel);

		aboutLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				RootPanel.get("rightside").clear();
				RootPanel.get("rightside").add(new Impressum());

			}
		});

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.getUserInfo(
				com.google.gwt.core.client.GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {

					@Override
					public void onSuccess(LoginInfo result) {
						loginInfo = result;

						// Bei erfolgreichem Login, wir die Anwendnung aufgebaut
						if (loginInfo.isLoggedIn()) {
							logger.log(Level.INFO, "Login erfolgreich");
							loadStartside();

						} else {
							// Ist der Nutzer nicht eingeloggt, wird ein Link
							// zum Google Login und zum Registrieren angezeigt.
							loadLogin();
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						logger.log(Level.WARNING, "Login not available");
					}
				});

	}
}
