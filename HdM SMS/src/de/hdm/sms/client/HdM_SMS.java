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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


import de.hdm.sms.client.gui.Impressum;
import de.hdm.sms.client.gui.Startside;

public class HdM_SMS extends VerticalPanel implements EntryPoint {
	LoginServiceAsync loginService = GWT.create(LoginService.class);
	private VerticalPanel bottomPanel = new VerticalPanel();
	private Label aboutLabel = new Label("Impressum");

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();

	private Label loginLabel = new Label(
			"Bitte melden Sie sich mit ihrem Google Account an, um Zugang zur Applikation zu bekommen. Falls Sie noch keinen Benutzeraccount haben, benutzern Sie bitte den Button Registrieren um einen zu erstellen.");

	private Anchor signInLink = new Anchor("Anmelden");
	private Anchor signOutLink = new Anchor("Abmelden");

	public HdM_SMS() {

	}

	Logger logger = ClientsideSettings.getLogger();

	// Load Startsidte Method
	public void loadStartside() {

		// Show the User who is logged in
		Label loggedInlabel = new Label("Angemeldet als: "
				+ loginInfo.getEmailAddress());

		// get the Loggout URL
		signOutLink.setHref(loginInfo.getLogoutUrl());
		Startside startside = new Startside();
		startside.setLoginInfo(loginInfo);

		// Clear and Fill the RootPanel
		RootPanel.get("leftside").clear();
		RootPanel.get("leftside").add(startside);
		RootPanel.get("leftside").add(loggedInlabel);
		RootPanel.get("leftside").add(signOutLink);

	}

	// Login Widget
	public Widget loadLogin() {
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);

		RootPanel.get("leftside").add(loginPanel);
		return loginPanel;
	}

	// Load
	public void onModuleLoad() {

		aboutLabel.addStyleName("impressum");
		bottomPanel.add(aboutLabel);

		RootPanel.get("bottom").add(aboutLabel);

		aboutLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				RootPanel.get("rightside").clear();
				RootPanel.get("rightside").add(new Impressum());

			}
		});

		// Get the LoginInfo By Google Login
		loginService.getUserInfo(
				com.google.gwt.core.client.GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {

					@Override
					public void onSuccess(LoginInfo result) {
						loginInfo = result;

						// If Login is true the Startside will be loaded
						if (loginInfo.isLoggedIn()) {
							logger.log(Level.INFO, "Login erfolgreich");
							loadStartside();

						} else {
							// If not logged in load the login
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
