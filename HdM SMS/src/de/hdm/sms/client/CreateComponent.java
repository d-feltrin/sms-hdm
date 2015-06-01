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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateComponent extends VerticalPanel {
	
private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	private DockPanel dockPanel = new DockPanel();
	private VerticalPanel textBoxPanel = new VerticalPanel();
	private VerticalPanel fillBoxPanel = new VerticalPanel();
	private VerticalPanel createElementPanel = new VerticalPanel();
	private VerticalPanel createModulePanel = new VerticalPanel();
	private VerticalPanel createMainPanel = new VerticalPanel();
	private VerticalPanel radioButtonPanel = new VerticalPanel();
	private VerticalPanel dialogboxVPanel = new VerticalPanel();
	private HorizontalPanel endproductPanel = new HorizontalPanel();
	private Button closeButton = new Button("Close");
	private Button createButton = new Button("Anlegen");
	private Button backButton = new Button("Zur\u00fcck");
	private TextBox nameTextBox = new TextBox();
	private TextBox descriptionTextBox = new TextBox(); 
	private TextBox materialTextBox = new TextBox(); 
	private Label nameLabel = new Label("Komponentenname"); 
	private Label descriptionLabel = new Label("Beschreibung"); 
	private Label materialLabel = new Label("Material"); 
	private Label createcomponentLabel = new Label("Neue Komponente anlegen"); 
	private Label endproductLabel = new Label("Enderzeugnis");
	private Label errorLabel = new Label();
	private RadioButton rb0 = new RadioButton("myRadioGroup", "Bauteil");
	private RadioButton rb1 = new RadioButton("myRadioGroup", "Baugruppe");
	private CheckBox cb0 = new CheckBox("Endprodukt");
	private HTML serverResponseLabel = new HTML();
	private DialogBox dialogBox = new DialogBox();
	
	private void send(){
		
		/*if (nameTextBox.getValue().isEmpty()
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
		}*/

		
	}
	
	
	// ONLOAD ########################################################################################################

	
		public void onLoad() {
			
			createButton.setPixelSize(180, 30);
			backButton.setPixelSize(180, 30);
			nameTextBox.setText("");
			descriptionTextBox.setText("");
			materialTextBox.setText("");
			textBoxPanel.setPixelSize(200, 150);
			fillBoxPanel.setPixelSize(200, 2000);
			
			radioButtonPanel.add(rb0);
			radioButtonPanel.add(rb1);
			
			textBoxPanel.add(nameTextBox);
			textBoxPanel.add(nameLabel);
			textBoxPanel.add(radioButtonPanel);
			textBoxPanel.add(fillBoxPanel);
			
			createElementPanel.add(descriptionTextBox);
			createElementPanel.add(descriptionLabel);
			createElementPanel.add(materialTextBox);
			createElementPanel.add(materialLabel);
			
			endproductPanel.add(endproductLabel);
			endproductPanel.add(cb0);
			endproductPanel.add(endproductLabel);
			
			createModulePanel.add(endproductLabel);
			
			createMainPanel.add(createElementPanel);
			createMainPanel.add(createModulePanel);
			createMainPanel.add(createButton);
		    
			dockPanel.add(createcomponentLabel, DockPanel.NORTH); 		//North
			dockPanel.add(textBoxPanel, DockPanel.WEST); 		//West
			dockPanel.add(new HTML(" "), DockPanel.EAST); 		//East
			dockPanel.add(backButton, DockPanel.SOUTH); 		//South
			dockPanel.add(createMainPanel, DockPanel.NORTH); 	//Second North
			dockPanel.add(new HTML(" "), DockPanel.SOUTH); 		//Second South
			
			dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
			dockPanel.setStyleName("dockpanel");
			dockPanel.setSpacing(5);
			createcomponentLabel.setStyleName("header");
			createElementPanel.setVisible(false);
			createModulePanel.setVisible(false);
			createButton.setVisible(false);
			
		    RootPanel.get("leftside").add(dockPanel);
		    
		    nameTextBox.setFocus(true);
		    nameTextBox.selectAll();
		    
		    
	// DIALOGBOX ########################################################################################################
		    
		    
		    dialogBox.setText("Registrierung");
			dialogBox.setAnimationEnabled(true);
			
			dialogboxVPanel.add(new HTML("Sie wurden erfolgreich registriert!<br><br>"));
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
		 	
		 	/*emailTextBox.addKeyUpHandler(new KeyUpHandler() {
				
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
					});	*/
			
		 	rb0.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					if(rb0.getValue()==true)
					{
						createModulePanel.setVisible(false);
						createElementPanel.setVisible(true);
						createButton.setVisible(true);
					}
					
				}	
			});
		 	
		 	rb1.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					if(rb1.getValue()==true)
					{
						createModulePanel.setVisible(true);
						createElementPanel.setVisible(false);
						createButton.setVisible(true);
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
					RootPanel.get("leftside").add(new SearchComponent());
					
				}
			});
			
		}

	}

/*
import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.bo.Component;

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
	private VerticalPanel CreateComponentPanel = new VerticalPanel();
	private Label NameLabel = new Label("Name des Bauteils");
	private Label DescriptionLabel = new Label("Beschreibung");
	private Label MaterialDescriptionLabel = new Label("Materialbezeichnung");
	private TextBox NameTextbox = new TextBox();
	private TextBox DescriptionTextbox = new TextBox();
	private TextBox MaterialDescriptionTextbox = new TextBox();
	private Button CreateComponentButton = new Button("Bauteil anlegen");
	private final AServiceAsync AsyncObj = GWT.create(AService.class);
	private Component c = new Component();

	public CreateComponent() {

	}

	public void onLoad() {
		CreateComponentPanel.add(NameLabel);
		CreateComponentPanel.add(NameTextbox);
		CreateComponentPanel.add(DescriptionLabel);
		CreateComponentPanel.add(DescriptionTextbox);
		CreateComponentPanel.add(MaterialDescriptionLabel);
		CreateComponentPanel.add(MaterialDescriptionTextbox);
		CreateComponentPanel.add(CreateComponentButton);
		RootPanel.get("content").add(CreateComponentPanel);

		CreateComponentButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (NameTextbox.getValue().isEmpty()
						|| DescriptionTextbox.getValue().isEmpty()
						|| MaterialDescriptionTextbox.getValue().isEmpty()) {
					Window.alert("Bitte alle Felder befüllen");
				} else {
					c.setDescription(DescriptionTextbox.getValue());
					c.setName(NameTextbox.getValue());
					c.setMaterialDescription(MaterialDescriptionTextbox
							.getValue());
					AsyncObj.insertComponent(c, new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							Window.alert("Bauteil " + c.getName()
									+ " erfolgreich angelegt.");

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

}*/
