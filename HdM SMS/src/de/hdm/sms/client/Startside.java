package de.hdm.sms.client;

import de.hdm.sms.shared.FieldVerifier;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Startside extends VerticalPanel {
	
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	private DockPanel dockPanel = new DockPanel();
	private VerticalPanel startsidePanel = new VerticalPanel();
	private VerticalPanel radioButtonPanel = new VerticalPanel();
	private VerticalPanel dialogboxVPanel = new VerticalPanel();
	private FlowPanel buttonPanel = new FlowPanel();
	private Button loginButton = new Button("Anmelden");
	private Button registerButton = new Button("Registrieren");
	private Button closeButton = new Button("Close");
	private TextBox nameTextBox = new TextBox();
	private TextBox keywordTextBox = new TextBox();
	private Label nameLabel = new Label("Benutzername");
	private Label keywordLabel = new Label("Passwort");
	private Label loginLabel = new Label("Login");
	private Label errorLabel = new Label();
	private Label textToServerLabel = new Label();
	private HTML serverResponseLabel = new HTML();
	private RadioButton rb0 = new RadioButton("myRadioGroup", "Client/Viewer");
	private RadioButton rb1 = new RadioButton("myRadioGroup", "Reportgenerator");
	private DialogBox dialogBox = new DialogBox();
	
	public void login() {
		
		errorLabel.setText("");
		String textToServer = nameTextBox.getText();
		if (!FieldVerifier.isValidName(textToServer)) {
			errorLabel.setText("Please enter at least four characters");
			return;
		}

		loginButton.setEnabled(false);
		registerButton.setEnabled(false);
		textToServerLabel.setText("");
		serverResponseLabel.setText("");
		greetingService.getName(textToServer,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(String result) {
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(result);
						dialogBox.center();
						closeButton.setFocus(true);
						RootPanel.get("leftside").clear();
						RootPanel.get("leftside").add(new SearchComponent());
						
					}
				});
	}	
	
	
	public void openReport() {
		
	}	
	
	public void createUser() {
		RootPanel.get("leftside").clear();
		RootPanel.get("leftside").add(new CreateUser());
	}
	
    // ONLOAD ########################################################################################################	
	
	
	public void onLoad() {
			
		loginButton.setPixelSize(180, 30);
		registerButton.setPixelSize(180, 30);
		nameTextBox.setText("Benutzername");
		keywordTextBox.setText("********");
		rb0.setChecked(true);
		
		radioButtonPanel.add(rb0);
		radioButtonPanel.add(rb1);
	    
		buttonPanel.add(loginButton);
		buttonPanel.add(registerButton);
	    
		startsidePanel.add(nameLabel);
		startsidePanel.add(nameTextBox);
		startsidePanel.add(keywordLabel);
		startsidePanel.add(keywordTextBox);
		
		dockPanel.add(loginLabel, DockPanel.NORTH); 		//North
		dockPanel.add(startsidePanel, DockPanel.WEST); 		//West
		dockPanel.add(new HTML(" "), DockPanel.EAST); 		//East
		dockPanel.add(new HTML(" "), DockPanel.SOUTH); 		//South
		dockPanel.add(radioButtonPanel, DockPanel.NORTH); 	//Second North
		dockPanel.add(buttonPanel, DockPanel.SOUTH); 		//Second South
		
		dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
		dockPanel.setStyleName("dockpanel");
		dockPanel.setSpacing(5);
		loginLabel.setStyleName("header");
		
	    RootPanel.get("leftside").add(dockPanel);
	    
	    nameTextBox.setFocus(true);
	    nameTextBox.selectAll();
		
	    	    
	 // DIALOGBOX ########################################################################################################
	    
	    
		dialogBox.setText("Login");
		dialogBox.setAnimationEnabled(true);
				
		dialogboxVPanel.add(textToServerLabel);
		dialogboxVPanel.add(new HTML("<br>"));
		dialogboxVPanel.add(serverResponseLabel);
		dialogboxVPanel.add(new HTML("<br>Sie wurden erfolgreich angemeldet!<br>"));
	 	dialogboxVPanel.add(new HTML("<br>"));
		dialogboxVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogboxVPanel.add(closeButton);
		dialogBox.setWidget(dialogboxVPanel);
		
				
	// HANDLER ########################################################################################################
				
		
		keywordTextBox.addKeyUpHandler(new KeyUpHandler() {
		
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) 
					{
						login();
					}
				}
			});		
		
		nameTextBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) 
					{
						login();
					}
				}
			});		
				
		closeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				dialogBox.hide();
				loginButton.setEnabled(true);
				loginButton.setFocus(true);
				registerButton.setEnabled(true);

			}
		});
		
		
		loginButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(rb0.getValue()==true)
				{
					login();
				}
				else 
				{
					openReport();
				}
			}
			
		});
		
		registerButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

					createUser();
				}
			
		});

	}
}
