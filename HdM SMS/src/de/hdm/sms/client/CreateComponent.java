package de.hdm.sms.client;

import de.hdm.sms.shared.FieldVerifier;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
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
	private VerticalPanel buttonPanel = new VerticalPanel();
	private HorizontalPanel selectElementPanel = new HorizontalPanel();
	private HorizontalPanel selectModulePanel = new HorizontalPanel();
	private FlexTable componentFlexTable = new FlexTable();
	private Button closeButton = new Button("Close");
	private Button createButton = new Button("Anlegen");
	private Button selectModuleButton = new Button("+");
	private Button selectElementButton = new Button("+");
	private Button backButton = new Button("Zur\u00fcck");
	private TextBox nameTextBox = new TextBox();
	private TextArea descriptionTextArea = new TextArea(); 
	private TextBox materialTextBox = new TextBox(); 
	private Label nameLabel = new Label("Komponentenname"); 
	private Label descriptionLabel = new Label("Beschreibung"); 
	private Label materialLabel = new Label("Material"); 
	private Label createComponentLabel = new Label("Neue Komponente anlegen"); 
	private Label errorLabel = new Label();
	private ListBox elementListBox = new ListBox();
	private ListBox moduleListBox = new ListBox();
	private RadioButton rb0 = new RadioButton("myRadioGroup", "Bauteil");
	private RadioButton rb1 = new RadioButton("myRadioGroup", "Baugruppe");
	private CheckBox cb0 = new CheckBox("Endprodukt");
	private HTML serverResponseLabel = new HTML();
	private DialogBox dialogBox = new DialogBox();
	private String[] componentListArray = new String[5];
	
	private void createElement(){
		
		if (nameTextBox.getValue().isEmpty()
				|| materialTextBox.getValue().isEmpty()) {
			Window.alert("Bitte alle Felder bef\u00fcllen!");
		} 
		else {

			errorLabel.setText("");
			String nameToServer = nameTextBox.getText();
			if (!FieldVerifier.isValidName(nameToServer)) {
				errorLabel.setText("Please enter at least four characters");
				return;
			}
			
			String materialToServer = materialTextBox.getText();
			String descriptionToServer = descriptionTextArea.getText();
			
			serverResponseLabel.setText("");
			greetingService.createElement(nameToServer, descriptionToServer, materialToServer, 
					new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							
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
	}
	
private void createModule(){
		
		if (nameTextBox.getValue().isEmpty()
				) {
			Window.alert("Bitte alle Felder bef\u00fcllen!");
		} 
		else {

			errorLabel.setText("");
			String nameToServer = nameTextBox.getText();
			if (!FieldVerifier.isValidName(nameToServer)) {
				errorLabel.setText("Please enter at least four characters");
				return;
			}
			
			String component1ToServer = componentListArray[0];
			String component2ToServer = componentListArray[1];
			String component3ToServer = componentListArray[2];
			String component4ToServer = componentListArray[3];
			String component5ToServer = componentListArray[4];
			Boolean endproductToServer = cb0.getValue();
			
			serverResponseLabel.setText("");
			greetingService.createModule(nameToServer, component1ToServer, component2ToServer, component3ToServer, component4ToServer, component5ToServer, endproductToServer,
					new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							
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
	}
	
	// ONLOAD ########################################################################################################

	
		public void onLoad() {
			
			createButton.setPixelSize(180, 30);
			backButton.setPixelSize(180, 30);
			selectElementButton.setPixelSize(30, 30);
			selectModuleButton.setPixelSize(30, 30);
			descriptionTextArea.setPixelSize(180, 60);
			elementListBox.setPixelSize(180, 30);
			moduleListBox.setPixelSize(180, 30);
			nameTextBox.setText("");
			descriptionTextArea.setText("");
			materialTextBox.setText("");
			textBoxPanel.setPixelSize(200, 150);
			fillBoxPanel.setPixelSize(200, 2000);
			elementListBox.setVisibleItemCount(1);
			moduleListBox.setVisibleItemCount(1);
			
			componentFlexTable.setText(0, 0, "Komponente");
			componentFlexTable.setText(0, 1, "Entfernen");
			
			componentListArray[0] = "empty";
			componentListArray[1] = "empty";
			componentListArray[2] = "empty";
			componentListArray[3] = "empty";
			componentListArray[4] = "empty";
			
			elementListBox.addItem("Bauteil1");
			elementListBox.addItem("Bauteil2");
			elementListBox.addItem("Bauteil3");
			elementListBox.addItem("Bauteil4");
			elementListBox.addItem("etc.");
			
			moduleListBox.addItem("Baugruppe1");
			moduleListBox.addItem("Baugruppe2");
			moduleListBox.addItem("Baugruppe3");
			moduleListBox.addItem("Baugruppe4");
			moduleListBox.addItem("etc.");
			
			radioButtonPanel.add(rb0);
			radioButtonPanel.add(rb1);
			
			textBoxPanel.add(nameLabel);
			textBoxPanel.add(nameTextBox);
			textBoxPanel.add(radioButtonPanel);
			textBoxPanel.add(fillBoxPanel);
			
			createElementPanel.add(descriptionLabel);
			createElementPanel.add(descriptionTextArea);
			createElementPanel.add(materialLabel);
			createElementPanel.add(materialTextBox);
			
			selectElementPanel.add(elementListBox);
			selectElementPanel.add(selectElementButton);
			
			selectModulePanel.add(moduleListBox);
			selectModulePanel.add(selectModuleButton);
			
			createModulePanel.add(selectElementPanel);
			createModulePanel.add(selectModulePanel);
			createModulePanel.add(cb0);
			
			createMainPanel.add(createModulePanel);
			createMainPanel.add(createElementPanel);
			
			buttonPanel.add(createButton);
			buttonPanel.add(backButton);
			
			dockPanel.add(createComponentLabel, DockPanel.NORTH); 	//North
			dockPanel.add(textBoxPanel, DockPanel.WEST); 			//West
			dockPanel.add(componentFlexTable, DockPanel.EAST); 		//East
			dockPanel.add(buttonPanel, DockPanel.SOUTH); 			//South
			dockPanel.add(createMainPanel, DockPanel.NORTH); 		//Second North
			dockPanel.add(new HTML(" "), DockPanel.SOUTH); 			//Second South
			
			dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
			dockPanel.setStyleName("dockpanel");
			dockPanel.setSpacing(5);
			createComponentLabel.setStyleName("header");
			createElementPanel.setVisible(false);
			createModulePanel.setVisible(false);
			createButton.setVisible(false);
			componentFlexTable.setVisible(false);
			
		    RootPanel.get("leftside").add(dockPanel);
		    
		    nameTextBox.setFocus(true);
		    nameTextBox.selectAll();
		    
		    
	// DIALOGBOX ########################################################################################################
		    
		    
		    dialogBox.setText("Komponente - Anlegen");
			dialogBox.setAnimationEnabled(true);
			
			dialogboxVPanel.add(new HTML("Folgende Komponente wurde erfolgreich angelegt:<br><br>"));
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
			
		 	/*nameTextBox.addKeyUpHandler(new KeyUpHandler() {
				
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) 
						{
							send();
						}
					}
				});		*/
			
		 	rb0.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					if(rb0.getValue()==true)
					{
						createModulePanel.setVisible(false);
						componentFlexTable.setVisible(false);
						componentFlexTable.removeAllRows();
						componentFlexTable.setText(0, 0, "Komponente");
						componentFlexTable.setText(0, 1, "Entfernen");
						componentListArray[1] = "empty";
						componentListArray[2] = "empty";
						componentListArray[3] = "empty";
						componentListArray[4] = "empty";
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
		 	
		 	selectModuleButton.addClickHandler(new ClickHandler() {

		 		@Override
				public void onClick(ClickEvent event) {
					
					int index = moduleListBox.getSelectedIndex();
					String element = moduleListBox.getItemText(index);
					
					final Button deleteButton = new Button("x");
					deleteButton.setPixelSize(30, 30);

					deleteButton.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							
							int removedIndex = deleteButton.getTabIndex();
							componentListArray[removedIndex] = "empty";
					        componentFlexTable.setText(removedIndex, 0, null);
							componentFlexTable.setWidget(removedIndex, 1, null);
							
						}	
					});
					
					int i = 0;
					
					while (i < 5) {
						if(componentListArray[i] == "empty"){
							componentListArray[i] = element;
							
							componentFlexTable.setText(i, 0, element);
							componentFlexTable.setWidget(i, 1, deleteButton);
							componentFlexTable.setVisible(true);
							deleteButton.setTabIndex(i);
							break;
						}
						else
							i++;
					}
					
					if(i == 5)
					{
						Window.alert("Die maximale Anzahl an Bauteilen und Baugruppen ist erreicht!");
					}
						
				}	
			});
		 	
		 	selectElementButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					int index = elementListBox.getSelectedIndex();
					String element = elementListBox.getItemText(index);
					
					final Button deleteButton = new Button("x");
					deleteButton.setPixelSize(30, 30);

					deleteButton.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							
							int removedIndex = deleteButton.getTabIndex();
							componentListArray[removedIndex] = "empty";
					        componentFlexTable.setText(removedIndex, 0, null);
							componentFlexTable.setWidget(removedIndex, 1, null);
							
						}	
					});
					
					int i = 0;
					
					while (i < 5) {
						if(componentListArray[i] == "empty"){
							componentListArray[i] = element;
							
							componentFlexTable.setText(i, 0, element);
							componentFlexTable.setWidget(i, 1, deleteButton);
							componentFlexTable.setVisible(true);
							deleteButton.setTabIndex(i);
							break;
						}
						else
							i++;
					}
					
					if(i == 5)
					{
						Window.alert("Die maximale Anzahl an Bauteilen und Baugruppen ist erreicht!");
					}
						
				}	
			});
		 	
			createButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					if(rb0.getValue()==true)
					{
						createElement();
					}
					else
					{
						createModule();
					}
					
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