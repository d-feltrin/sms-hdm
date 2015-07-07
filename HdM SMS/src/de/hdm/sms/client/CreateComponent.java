package de.hdm.sms.client;

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
	private LoginInfo loginInfo;

	public CreateComponent() {

	}

	// Get the loginInformations
	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public void onLoad() {
		// Get User Informations
		asyncObj.getOneUserIdByEmailAdress(loginInfo.getEmailAddress(),
				new AsyncCallback<User>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(User result) {
						// set User Informations and Add the Panel
						if (result.geteMailAdress() != null) {
							u.setId(result.getId());
							u.setFirstName(result.getFirstName());
							u.setLastName(result.getLastName());
							u.seteMailAdress(result.geteMailAdress());
							createComponentPanel.add(nameLabel);
							createComponentPanel.add(nameTextbox);
							createComponentPanel.add(descriptionLabel);
							createComponentPanel.add(descriptionTextbox);
							createComponentPanel.add(materialDescriptionLabel);
							createComponentPanel
									.add(materialDescriptionTextbox);
							createComponentPanel.add(createComponentButton);
							RootPanel.get("rightside").clear();

							RootPanel.get("rightside")
									.add(createComponentPanel);
						} else {
							// If User not registered in the system add the
							// CreateUser method
							Window.alert("Bitte registrieren Sie sich zuerst!");
							RootPanel.get("rightside").clear();
							CreateUser cU = new CreateUser();
							cU.setLoginInfo(loginInfo);
							RootPanel.get("rightside").add(cU);
						}

					}

				});
		// Clickhandler to create the Component
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

					asyncObj.insertComponent(c, new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							Window.alert("Bauteil " + c.getName()
									+ " erfolgreich angelegt.");
							RootPanel.get("rightside").clear();

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