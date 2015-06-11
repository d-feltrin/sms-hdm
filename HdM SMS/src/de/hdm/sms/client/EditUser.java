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
	private final ListBox ListOfUsers = new ListBox();
	private final AServiceAsync AsyncObj = GWT.create(AService.class);
	private String selectedUser;
	private Label UserIdLabel = new Label("Id");
	private Label FirstnameLabel = new Label("Vorname");
	private Label LastnameLabel = new Label("Nachname");
	private Label eMailAdressLabel = new Label("E-Mail Adresse");
	private TextBox UserIdTextBox = new TextBox();
	private TextBox FirstnameTextBox = new TextBox();
	private TextBox LastnameTextBox = new TextBox();
	private TextBox eMailAdressTextBox = new TextBox();
	private String UserIdString;
	private VerticalPanel UserItemPanel = new VerticalPanel();
	private HorizontalPanel ButtonPanel = new HorizontalPanel();
	private Button DeleteUserButton = new Button("Benutzer l�schen");
	private Button EditUserButton = new Button("Benutzer editieren");

	public EditUser() {

	}
private void updateUser(User u) {
	AsyncObj.updateUserById(u, new AsyncCallback<Void>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Benutzer erfolgreich editiert");
			RootPanel.get("rightside").clear();
			Startside sS = new Startside();
			RootPanel.get().add(sS);
			
			
		}
	});
}
	private void DeleteUser(int DeleteUserId) {
		AsyncObj.deleteUserById(DeleteUserId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Benutzer erfolgreich gel�scht");
				RootPanel.get("rightside").clear();
				RootPanel.get("rightside").add(new ImageSMS());
				//Startside sS = new Startside();
				//RootPanel.get().add(sS);
				RootPanel.get("leftside").clear();
				RootPanel.get("leftside").add(new Startside());
				

			}
		});
	}

	private void LoadAllUser() {

		ListOfUsers.setSize("180px", "35px");
		// ListOfComponents.addStyleName("mainmenu-dropdown");
		ListOfUsers.addItem("---");

		AsyncObj.loadAllUsers(new AsyncCallback<ArrayList<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<User> result) {
				for (int i = 0; i < result.size(); i++) {

					ListOfUsers.addItem(result.get(i).geteMailAdress());

					
				}

			}
		});
		RootPanel.get("rightside").add(ListOfUsers);
	}

	public void onLoad() {
		LoadAllUser();
		ButtonPanel.add(EditUserButton);
		ButtonPanel.add(DeleteUserButton);
		
		ListOfUsers.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				RootPanel.get("rightside").clear();
				selectedUser = ListOfUsers.getItemText(ListOfUsers
						.getSelectedIndex());

				AsyncObj.getOneUserIdByName(selectedUser,
						new AsyncCallback<User>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(User result) {
								UserIdString = String.valueOf(result.getId());
								UserIdTextBox.setText(UserIdString);
								UserIdTextBox.setEnabled(false);
								FirstnameTextBox.setText(result.getFirstName());
								LastnameTextBox.setText(result.getLastName());
								eMailAdressTextBox.setText(result
										.geteMailAdress());
								UserItemPanel.add(UserIdLabel);
								UserItemPanel.add(UserIdTextBox);
								UserItemPanel.add(FirstnameLabel);
								UserItemPanel.add(FirstnameTextBox);
								UserItemPanel.add(LastnameLabel);
								UserItemPanel.add(LastnameTextBox);
								UserItemPanel.add(eMailAdressLabel);
								UserItemPanel.add(eMailAdressTextBox);
								UserItemPanel.add(ButtonPanel);
								RootPanel.get("rightside").clear();
								RootPanel.get("rightside").add(ListOfUsers);
								RootPanel.get("rightside").add(UserItemPanel);
								DeleteUserButton
										.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												DeleteUser(Integer
														.parseInt(UserIdString));

												

											}
										});
								EditUserButton.addClickHandler(new ClickHandler() {
									
									@Override
									public void onClick(ClickEvent event) {

										if (FirstnameTextBox.getValue().isEmpty() || LastnameTextBox.getValue().isEmpty() || eMailAdressTextBox.getValue().isEmpty()) 
										{
											Window.alert("Bitte alle Felder bef�llen!");
										} else {
											User u = new User();
											u.setId(Integer.parseInt(UserIdString));
											u.setFirstName(FirstnameTextBox.getText());
											u.setLastName(LastnameTextBox.getText());
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