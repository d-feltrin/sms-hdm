package de.hdm.sms.client;

import de.hdm.sms.shared.FieldVerifier;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateUser extends VerticalPanel {
	
private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	private DockPanel dockPanel = new DockPanel();
	private VerticalPanel textBoxPanel = new VerticalPanel();
	private VerticalPanel buttonPanel = new VerticalPanel();
	private VerticalPanel dialogboxVPanel = new VerticalPanel();
	private Button createButton = new Button("Registrieren");
	private Button backButton = new Button("Zur\u00fcck");
	private Button closeButton = new Button("Close");
	private TextBox nameTextBox = new TextBox();
	private TextBox emailTextBox = new TextBox();
	private TextBox keywordTextBox = new TextBox();
	private Label nameLabel = new Label("Benutzername");
	private Label emailLabel = new Label("E-Mail");
	private Label keywordLabel = new Label("Passwort");
	private Label registerLabel = new Label("Registrieren");
	private Label errorLabel = new Label();
	private HTML serverResponseLabel = new HTML();
	private DialogBox dialogBox = new DialogBox();
	
	private void send(){
		
		if (nameTextBox.getValue().isEmpty()
				|| emailTextBox.getValue().isEmpty()
				|| keywordTextBox.getValue().isEmpty()) {
			Window.alert("Bitte alle Felder bef\u00fcllen!");
		} else {

																								//u.setFirstName(FirstnameTextBoxOfUser.getValue()); u=User
																								//u.setLastName(LastnameTextBoxOfUser.getValue());
																								//u.seteMailAdress(eMailAdressTextBoxOfUser.getValue());
			
			errorLabel.setText("");
			String nameToServer = nameTextBox.getText();
			if (!FieldVerifier.isValidName(nameToServer)) {
				errorLabel.setText("Please enter at least four characters");
				return;
			}
			String emailToServer = emailTextBox.getText();
			String keywordToServer = keywordTextBox.getText();
			
			serverResponseLabel.setText("");
			greetingService.createUsergreetServer(nameToServer, emailToServer, keywordToServer,
					new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							
						}

						public void onSuccess(String result) {
							dialogBox.setText("Registrierung");
							serverResponseLabel.removeStyleName("serverResponseLabelError");
							serverResponseLabel.setHTML(result);
							dialogBox.center();
							closeButton.setFocus(true);
							RootPanel.get("leftside").clear();
							RootPanel.get("leftside").add(new SearchComponent());
							
						}
					});
		}

		
	}
	
	
	// ONLOAD ########################################################################################################

	
		public void onLoad() {
			
			createButton.setPixelSize(180, 30);
			backButton.setPixelSize(180, 30);
			nameTextBox.setText("Benutzername");
			keywordTextBox.setText("********");
			emailTextBox.setText("ab123@hdm-stuttgart.de");
			
			buttonPanel.add(createButton);
			buttonPanel.add(backButton);
			
			textBoxPanel.add(nameTextBox);
			textBoxPanel.add(nameLabel);
			textBoxPanel.add(emailTextBox);
			textBoxPanel.add(emailLabel);
			textBoxPanel.add(keywordTextBox);
			textBoxPanel.add(keywordLabel);
		    
			dockPanel.add(registerLabel, DockPanel.NORTH); 		//North
			dockPanel.add(textBoxPanel, DockPanel.WEST); 		//West
			dockPanel.add(new HTML(" "), DockPanel.EAST); 		//East
			dockPanel.add(new HTML(" "), DockPanel.SOUTH); 		//South
			dockPanel.add(buttonPanel, DockPanel.NORTH); 	//Second North
			dockPanel.add(new HTML(" "), DockPanel.SOUTH); 		//Second South
			
			dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
			dockPanel.setStyleName("dockpanel");
			dockPanel.setSpacing(5);
			registerLabel.setStyleName("header");
			
		    RootPanel.get("leftside").add(dockPanel);
		    
		    nameTextBox.setFocus(true);
		    nameTextBox.selectAll();
		    
		    
	// DIALOGBOX ########################################################################################################
		    
		    
		    dialogBox.setText("Registrierung");
			dialogBox.setAnimationEnabled(true);
			
			dialogboxVPanel.add(new HTML("<br>Sie wurden erfolgreich registriert!<br>"));
			dialogboxVPanel.add(new HTML("<br>"));
			dialogboxVPanel.add(serverResponseLabel);
			dialogboxVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
			dialogboxVPanel.add(closeButton);
			dialogBox.setWidget(dialogboxVPanel);
		 		
			
	// HANDLER ########################################################################################################
			
			
			closeButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {

					dialogBox.hide();
					createButton.setEnabled(true);
					backButton.setFocus(true);

				}
			});
			
		 	nameTextBox.addKeyUpHandler(new KeyUpHandler() {
				
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) 
						{
							send();
						}
					}
				});		
		 	
		 	emailTextBox.addKeyUpHandler(new KeyUpHandler() {
				
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) 
						{
							send();
						}
					}
				});	

			keywordTextBox.addKeyUpHandler(new KeyUpHandler() {
					
					@Override
					public void onKeyUp(KeyUpEvent event) {
						if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) 
							{
								send();
							}
						}
					});	
			
			createButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					send();
					
				}	
			});
			
			backButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					RootPanel.get("leftside").clear();
					RootPanel.get("leftside").add(new Startside());
					
				}
			});
			
		}

	}
/*
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
		
		RootPanel.get("content").add(CreateUserPanel);

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

}*/
