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

import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.LoginInfo;
import de.hdm.sms.shared.bo.User;

public class EditUser extends VerticalPanel {
	private final ListBox listOfUsers = new ListBox();
	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private String selectedUser;

	private Label firstnameLabel = new Label("Vorname");
	private Label lastnameLabel = new Label("Nachname");

	private TextBox firstnameTextBox = new TextBox();
	private TextBox lastnameTextBox = new TextBox();

	private int tempUserId;
	private VerticalPanel userItemPanel = new VerticalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button deleteUserButton = new Button("Benutzer löschen");
	private Button editUserButton = new Button("Benutzer editieren");
	private LoginInfo loginInfo;
	private User u = new User();
	private User uS = new User();

	public EditUser() {

	}

	// CHeck if User is registered in the system
	public User getUserIdByEMailAdress(String eMailAdress) {

		asyncObj.getOneUserIdByEmailAdress(eMailAdress,
				new AsyncCallback<User>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(User result) {
						if (result.geteMailAdress() != null) {
							//
							u.setId(result.getId());
							u.setFirstName(result.getFirstName());
							u.setLastName(result.getLastName());
							u.seteMailAdress(result.geteMailAdress());
							loadAllUser();
						} else {
							Window.alert("Bitte registrieren Sie sich zuerst!");
							RootPanel.get("rightside").clear();
							CreateUser cU = new CreateUser();
							cU.setLoginInfo(loginInfo);
							RootPanel.get("rightside").add(cU);
						}

					}

				});
		return u;

	}

	// Get user informations
	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	// Get the Id by splitting the Listbox value
	private String getIDbyDropDownText(String selectedUser) {

		// get ID by ListBox text
		// Splitt " - 569:Bla" into " - 569", "Bla"
		String[] SplitStepOne = selectedUser.split(":");

		// Splitt " - 569" into " - ", "569"
		String[] SplitStepTwo = SplitStepOne[0].split(" ");

		return SplitStepTwo[1];
	}

	// update User with Object u
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

	// Delete User by Id
	private void deleteUser(int DeleteUserId) {
		asyncObj.deleteUserById(DeleteUserId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Benutzer erfolgreich gel�scht");
				RootPanel.get("rightside").clear();

			}
		});
	}

	// Fill the Listbox with all User
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
					if (result.get(i).getId() != -1) {
						listOfUsers.addItem(" - " + result.get(i).getId() + ":"
								+ result.get(i).getFirstName() + " "
								+ result.get(i).getLastName() + " ("
								+ result.get(i).geteMailAdress() + ")");
					}
				}

			}
		});
		// CLear RootPanel and add ListBox of Users
		RootPanel.get("rightside").clear();
		RootPanel.get("rightside").add(new Label("Benutzer auswaehlen"));
		RootPanel.get("rightside").add(listOfUsers);
	}

	// Load
	public void onLoad() {
		getUserIdByEMailAdress(loginInfo.getEmailAddress());
		// ChangeHandler of Userlistbox
		listOfUsers.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				RootPanel.get("rightside").clear();
				selectedUser = listOfUsers.getItemText(listOfUsers
						.getSelectedIndex());
				tempUserId = Integer
						.parseInt(getIDbyDropDownText(selectedUser));

				asyncObj.getOneUserById(tempUserId, new AsyncCallback<User>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fehlgeschlagen");

					}

					@Override
					public void onSuccess(User result) {
						// Fill the uS Object of User with result of the
						// AsyncCallback
						uS = result;
						userItemPanel.add(new Label("E-Mail Adresse"));
						userItemPanel.add(new Label(uS.geteMailAdress()));
						firstnameTextBox.setText(result.getFirstName());
						lastnameTextBox.setText(result.getLastName());

						// Panel userItemPanel: Fill it
						userItemPanel.add(firstnameLabel);
						userItemPanel.add(firstnameTextBox);
						userItemPanel.add(lastnameLabel);
						userItemPanel.add(lastnameTextBox);

						userItemPanel.add(buttonPanel);
						buttonPanel.add(editUserButton);
						buttonPanel.add(deleteUserButton);
						RootPanel.get("rightside").add(userItemPanel);
						RootPanel.get("rightside").add(buttonPanel);

						// Clickhandler of the delete User Button
						deleteUserButton.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								deleteUser(tempUserId);

							}
						});
						// Clickhandler of the edit User Button
						editUserButton.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								asyncObj.getOneUserIdByEmailAdress(
										uS.geteMailAdress(),
										new AsyncCallback<User>() {

											@Override
											public void onFailure(
													Throwable caught) {
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void onSuccess(User result) {
												// UpdateUser if result is not
												// null and Textboxes not empty
												if (result.geteMailAdress() != null) {
													if (firstnameTextBox
															.getValue()
															.isEmpty()
															|| lastnameTextBox
																	.getValue()
																	.isEmpty()) {
														Window.alert("Bitte alle Felder befüllen!");
													} else {
														// Fill User Object u
														User u = new User();
														u.setId(tempUserId);
														u.setFirstName(firstnameTextBox
																.getText());
														u.setLastName(lastnameTextBox
																.getText());
														u.seteMailAdress(uS
																.geteMailAdress());
														updateUser(u);

													}
												}

											}
										});

							}
						});

					}
				});

			}
		});
	}
}