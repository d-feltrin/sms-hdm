package de.hdm.sms.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.util.logging.Logger;

import de.hdm.sms.client.gui.Startside;
import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.LoginInfo;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateComponent extends VerticalPanel {
	private VerticalPanel createComponentPanel = new VerticalPanel();
	private Label nameLabel = new Label("Name des Bauteils");
	private Label descriptionLabel = new Label("Beschreibung");
	private Label materialDescriptionLabel = new Label("Materialbezeichnung");
	private TextBox nameTextbox = new TextBox();
	private TextBox descriptionTextbox = new TextBox();
	private TextBox materialDescriptionTextbox = new TextBox();
	private Button createComponentButton = new Button("Bauteil anlegen");
	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private Component c = new Component();
	private User u = new User();
	private LoginInfo loginInfo = null;
	private final Date today = new Date(System.currentTimeMillis());
	
	public CreateComponent() {

	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public void onLoad() {
		asyncObj.getUserByEmail(loginInfo.getEmailAddress(),
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

		createComponentPanel.add(nameLabel);
		createComponentPanel.add(nameTextbox);
		createComponentPanel.add(descriptionLabel);
		createComponentPanel.add(descriptionTextbox);
		createComponentPanel.add(materialDescriptionLabel);
		createComponentPanel.add(materialDescriptionTextbox);
		createComponentPanel.add(createComponentButton);
		RootPanel.get("rightside").add(createComponentPanel);

		createComponentButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (nameTextbox.getValue().isEmpty()
						|| descriptionTextbox.getValue().isEmpty()
						|| materialDescriptionTextbox.getValue().isEmpty()) {
					Window.alert("Bitte alle Felder befüllen");
				} else {
					c.setDescription(descriptionTextbox.getValue());
					c.setName(nameTextbox.getValue());
					c.setMaterialDescription(materialDescriptionTextbox
							.getValue());
					c.setModifier(u.getId());
					c.setCreationdate(today);

					asyncObj.insertComponent(c, new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							Window.alert("Bauteil " + c.getName()
									+ " erfolgreich angelegt.");
							RootPanel.get("rightside").clear();
							// Startside sS = new Startside();
							// RootPanel.get().add(sS);

						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}
					});
				}

			}
		});

	}

}