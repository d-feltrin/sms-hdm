package de.hdm.sms.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.client.gui.ImageSMS;
import de.hdm.sms.client.gui.Startside;
import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.bo.User;

public class EditUser extends VerticalPanel {
	private final ListBox listOfUsers = new ListBox();
	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private String selectedUser;
	private Label userIdLabel = new Label("Id");
	private Label firstnameLabel = new Label("Vorname");
	private Label lastnameLabel = new Label("Nachname");
	private Label eMailAdressLabel = new Label("E-Mail Adresse");
	private TextBox userIdTextBox = new TextBox();
	private TextBox firstnameTextBox = new TextBox();
	private TextBox lastnameTextBox = new TextBox();
	private TextBox eMailAdressTextBox = new TextBox();
	private String userIdString;
	private VerticalPanel userItemPanel = new VerticalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button deleteUserButton = new Button("Benutzer löschen");
	private Button editUserButton = new Button("Benutzer editieren");

	public EditUser() {

	}
private void updateUser(User u) {
	asyncObj.updateUserById(u, new AsyncCallback<Void>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Benutzer erfolgreich editiert");
			RootPanel.get("rightside").clear();
						
		}
	});
}
	private void deleteUser(int DeleteUserId) {
		asyncObj.deleteUserById(DeleteUserId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Benutzer erfolgreich gelöscht");
				RootPanel.get("rightside").clear();
				RootPanel.get("rightside").add(new ImageSMS());
				RootPanel.get("leftside").clear();
				RootPanel.get("leftside").add(new Startside());
				

			}
		});
	}

	private void loadAllUser() {

		listOfUsers.setSize("180px", "35px");
		// ListOfComponents.addStyleName("mainmenu-dropdown");
		listOfUsers.addItem("---");

		asyncObj.loadAllUsers(new AsyncCallback<ArrayList<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<User> result) {
				for (int i = 0; i < result.size(); i++) {

					listOfUsers.addItem(result.get(i).geteMailAdress());

					
				}

			}
		});
		RootPanel.get("rightside").add(listOfUsers);
	}

	public void onLoad() {
		loadAllUser();
		buttonPanel.add(editUserButton);
		buttonPanel.add(deleteUserButton);
		
		listOfUsers.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				RootPanel.get("rightside").clear();
				selectedUser = listOfUsers.getItemText(listOfUsers
						.getSelectedIndex());

				asyncObj.getOneUserIdByName(selectedUser,
						new AsyncCallback<User>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(User result) {
								userIdString = String.valueOf(result.getId());
								userIdTextBox.setText(userIdString);
								userIdTextBox.setEnabled(false);
								firstnameTextBox.setText(result.getFirstName());
								lastnameTextBox.setText(result.getLastName());
								eMailAdressTextBox.setText(result
										.geteMailAdress());
								userItemPanel.add(userIdLabel);
								userItemPanel.add(userIdTextBox);
								userItemPanel.add(firstnameLabel);
								userItemPanel.add(firstnameTextBox);
								userItemPanel.add(lastnameLabel);
								userItemPanel.add(lastnameTextBox);
								userItemPanel.add(eMailAdressLabel);
								userItemPanel.add(eMailAdressTextBox);
								userItemPanel.add(buttonPanel);
								RootPanel.get("rightside").clear();
								RootPanel.get("rightside").add(listOfUsers);
								RootPanel.get("rightside").add(userItemPanel);
								deleteUserButton
										.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												deleteUser(Integer
														.parseInt(userIdString));

												

											}
										});
								editUserButton.addClickHandler(new ClickHandler() {
									
									@Override
									public void onClick(ClickEvent event) {

										if (firstnameTextBox.getValue().isEmpty() || lastnameTextBox.getValue().isEmpty() || eMailAdressTextBox.getValue().isEmpty()) 
										{
											Window.alert("Bitte alle Felder befüllen!");
										} else {
											User u = new User();
											u.setId(Integer.parseInt(userIdString));
											u.setFirstName(firstnameTextBox.getText());
											u.setLastName(lastnameTextBox.getText());
											u.seteMailAdress(eMailAdressTextBox.getText());
											updateUser(u);
											
										}
									}
								});

							}
						});

			}
		});
	}
}