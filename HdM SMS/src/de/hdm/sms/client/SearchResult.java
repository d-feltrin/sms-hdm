package de.hdm.sms.client;

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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

import de.hdm.sms.shared.bo.Component;

public class SearchResult extends VerticalPanel {
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	private DockPanel dockPanel = new DockPanel();
	private VerticalPanel buttonPanel = new VerticalPanel();
	private VerticalPanel elementPanel = new VerticalPanel();
	private VerticalPanel modulePanel = new VerticalPanel();
	private HTML samplehtml = new HTML();
	private Button deleteButton = new Button("L&oumlschen");
	private Button editButton = new Button("Bearbeiten");
	private Label searchResultLabel = new Label("Suchergebnis");
	private Label errorLabel = new Label("Die Komponente konnte nicht gefunden werden!");
	private Label nameLabel = new Label("Komponentenname:"); 
	private Label descriptionLabel = new Label("Beschreibung:"); 
	private Label materialLabel = new Label("Material:");
	private DialogBox dialogBox = new DialogBox();
	private Boolean result;
	
	public SearchResult(Boolean result){
		this.result = result;
	}
	
	public SearchResult(Boolean result, String nameLabel){
		this.result = result;
		this.nameLabel.setText("Name: " + nameLabel);
	}
	
	private void edit(){
		
	}
	
	private void delete(){
		
	}
	
	
	// ONLOAD ########################################################################################################

	
		public void onLoad() {
			
			editButton.setPixelSize(180, 30);
			deleteButton.setPixelSize(180, 30);
			
			buttonPanel.add(editButton);
			buttonPanel.add(deleteButton);
			
			elementPanel.add(nameLabel);
			elementPanel.add(descriptionLabel);
			elementPanel.add(materialLabel);
			
			dockPanel.add(searchResultLabel, DockPanel.NORTH); 		//North
			dockPanel.add(elementPanel, DockPanel.WEST); 		//West
			dockPanel.add(new HTML(" "), DockPanel.EAST); 		//East
			dockPanel.add(errorLabel, DockPanel.SOUTH); 		//South
			dockPanel.add(buttonPanel, DockPanel.NORTH); 		//Second North
			dockPanel.add(new HTML(" "), DockPanel.SOUTH); 		//Second South
			
			dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
			dockPanel.setStyleName("dockpanel");
			dockPanel.setSpacing(5);
			searchResultLabel.setStyleName("header");
			
			if(result){
				elementPanel.setVisible(true);
				errorLabel.setVisible(false);
				String name = nameLabel.getText();

				Component component = new Component(name,"Aus feinem Edelholz mit schn&oumlrkel","Holz"); // Testzweck
				
				descriptionLabel.setText("Beschreibung: " + component.getDescription());
				materialLabel.setText("Material: " + component.getMaterial());
				
			}
			else{
				elementPanel.setVisible(false);
				buttonPanel.setVisible(false);
				errorLabel.setVisible(true);
			}
						
			errorLabel.addStyleName("serverResponseLabelError");
			
		    RootPanel.get("rightside").add(dockPanel);
		    
	// DIALOGBOX ########################################################################################################
		    
		    
		    /*dialogBox.setText("Komponente - Anlegen");
			dialogBox.setAnimationEnabled(true);
			
			dialogboxVPanel.add(new HTML("Folgende Komponente wurde erfolgreich angelegt:<br><br>"));
			dialogboxVPanel.add(serverResponseLabel);
			dialogboxVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
			dialogboxVPanel.add(closeButton);
			dialogBox.setWidget(dialogboxVPanel)*/
		 		
			
	// HANDLER ########################################################################################################
			
			
		 	/*deleteButton.addKeyUpHandler(new KeyUpHandler() {
				
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) 
						{
							delete();
						}
					}
				});	*/	
					
		 	deleteButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {

					delete();

				}
			});
		 	
		 	editButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {

					edit();

				}
			});
			
		}

	}