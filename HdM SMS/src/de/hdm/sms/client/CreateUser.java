package de.hdm.sms.client;

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

import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.bo.User;

public class CreateUser extends VerticalPanel {
	private VerticalPanel CreateUserPanel = new VerticalPanel();
	private Label FirstnameLabelOfUser = new Label("Vorname");
	private Label LastnameLabelOfUser = new Label("Nachname");
	private Label eMailAdressLabelOfUser = new Label("E-Mail Adresse");
	private TextBox FirstnameTextBoxOfUser = new TextBox();
	private TextBox LastnameTextBoxOfUser = new TextBox();
	private TextBox eMailAdressTextBoxOfUser = new TextBox();
	private Button UserCreateButton = new Button("Benutzer anlegen");
	private final AServiceAsync AsyncObj = GWT.create(AService.class);
	private User u = new User();

	public CreateUser() {

	}

	public void onLoad() {
		CreateUserPanel.add(FirstnameLabelOfUser);
		CreateUserPanel.add(FirstnameTextBoxOfUser);
		CreateUserPanel.add(LastnameLabelOfUser);
		CreateUserPanel.add(LastnameTextBoxOfUser);
		CreateUserPanel.add(eMailAdressLabelOfUser);
		CreateUserPanel.add(eMailAdressTextBoxOfUser);
		CreateUserPanel.add(UserCreateButton);
		
		RootPanel.get("leftside").add(CreateUserPanel);

		UserCreateButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (FirstnameTextBoxOfUser.getValue().isEmpty()
						|| LastnameTextBoxOfUser.getValue().isEmpty()
						|| eMailAdressTextBoxOfUser.getValue().isEmpty()) {
					Window.alert("Bitte alle Felder befüllen!");
				} else {

					u.setFirstName(FirstnameTextBoxOfUser.getValue());
					u.setLastName(LastnameTextBoxOfUser.getValue());
					u.seteMailAdress(eMailAdressTextBoxOfUser.getValue());
					AsyncObj.insertUser(u, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Benutzer " + u.getFirstName() + " "
									+ u.getLastName() + " erfolgreich angelegt.");
							

						}
					});

				}

			}
		});
	}

}