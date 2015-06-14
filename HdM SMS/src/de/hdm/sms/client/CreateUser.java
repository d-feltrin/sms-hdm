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
	private VerticalPanel createUserPanel = new VerticalPanel();
	private Label firstnameLabelOfUser = new Label("Vorname");
	private Label lastnameLabelOfUser = new Label("Nachname");
	private Label eMailAdressLabelOfUser = new Label("E-Mail Adresse");
	private TextBox firstnameTextBoxOfUser = new TextBox();
	private TextBox lastnameTextBoxOfUser = new TextBox();
	private TextBox eMailAdressTextBoxOfUser = new TextBox();
	private Button userCreateButton = new Button("Benutzer anlegen");
	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private User u = new User();

	public CreateUser() {

	}

	public void onLoad() {
		createUserPanel.add(firstnameLabelOfUser);
		createUserPanel.add(firstnameTextBoxOfUser);
		createUserPanel.add(lastnameLabelOfUser);
		createUserPanel.add(lastnameTextBoxOfUser);
		createUserPanel.add(eMailAdressLabelOfUser);
		createUserPanel.add(eMailAdressTextBoxOfUser);
		createUserPanel.add(userCreateButton);
		
		RootPanel.get("leftside").add(createUserPanel);

		userCreateButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (firstnameTextBoxOfUser.getValue().isEmpty()
						|| lastnameTextBoxOfUser.getValue().isEmpty()
						|| eMailAdressTextBoxOfUser.getValue().isEmpty()) {
					Window.alert("Bitte alle Felder befüllen!");
				} else {

					u.setFirstName(firstnameTextBoxOfUser.getValue());
					u.setLastName(lastnameTextBoxOfUser.getValue());
					u.seteMailAdress(eMailAdressTextBoxOfUser.getValue());
					asyncObj.insertUser(u, new AsyncCallback<Void>() {

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