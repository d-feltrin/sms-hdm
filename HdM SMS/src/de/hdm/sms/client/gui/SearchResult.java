package de.hdm.sms.client.gui;

import java.util.ArrayList;

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
import de.hdm.sms.shared.bo.Module;

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
	private Label nameLabel = new Label(); 
	private Label descriptionLabel = new Label("Beschreibung:"); 
	private Label materialLabel = new Label("Material:");
	private Label endproductLabel = new Label();
	private Label elementComponentLabel = new Label();
	private Label moduleComponentLabel = new Label();
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
			
			modulePanel.add(nameLabel);
			modulePanel.add(elementComponentLabel);
			modulePanel.add(moduleComponentLabel);
			modulePanel.add(endproductLabel);
			
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
				final String name = nameLabel.getText();
				
				greetingService.getTypeOfComponent(name,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {

							}

							public void onSuccess(String result) {
								
								if (result.equals("module")){
									
									Component c1 = new Component("Brett","Aus feinem Edelholz mit schn&oumlrkel","Holz");
									Component c2 = new Component("Nagel","Spitz und lang","Stahl");
									Component c3 = new Component("Holzplatte","Aus feinem Edelholz mit schn&oumlrkel","Holz");
									
									Module m1 = new Module("Tischbein", c1, c2, false);
									Module m2 = new Module("Tisch", m1, c3, true); //ALLES TEST
									
									Boolean endproductState = m2.getEndproductState();
									
									if (endproductState){
										
										endproductLabel.setText("Als Endprodukt gekennzeichnet");
										
										System.out.println(true);
										}
									
										else{
										
										endproductLabel.setText("Als kein Endprodukt gekennzeichnet");
										
										System.out.println(false);
									}
									
									ArrayList<Component> component = new ArrayList<Component>();
									component = m2.getComponent();
									
									System.out.println("Array auf component übertragen: " + component.isEmpty());
									
									ArrayList<Module> module = new ArrayList<Module>();
									module = m1.getModule();
									
									System.out.println("Array auf module übertragen: " + component.isEmpty());
									
									/*Component rc1 = component.get(0);
									Component rc2 = component.get(1);
									Component rc3 = component.get(2);

									Module rm1 = module.get(0);
									Module rm2 = module.get(1);
									Module rm3 = module.get(2);*/
									
									String elementResult = "";
									String moduleResult = "";
									
									System.out.println("onSuccess - vor if für String");
									/*
										if(rm1.getComponentName(1).equals("")){
											
											System.out.println("is equal");
										}
										else{
											elementResult = elementResult + ", " + rc1.getName() ;
											System.out.println("added result 1");
											System.out.println(rm1.getComponentName(1).equals(""));
										}
										
										System.out.println(m1.getComponentName(1));
										
										if(rm2.getName().equals("")){
											System.out.println("is equal");
										}
										else{
											elementResult = elementResult + ", " + rc2.getName() ;
											System.out.println("added result 2");
										}
										
										if(rc3.getName().equals("")){
											System.out.println("is equal");
										}
										else{
											elementResult = elementResult + ", " + rc3.getName() ;
											System.out.println("added result 3");
										}
										
										System.out.println("finished with rc3");
										
										elementComponentLabel.setText("Bauteil(e): " + elementResult);
										
										System.out.println("start with rm1");
										
										if(rm1.getName().equals("")){
										}
										else{
											moduleResult = moduleResult + ", " + rm1.getName() ;
										}
										
										if(rm2.getName().equals("")){
										}
										else{
											moduleResult = moduleResult + ", " + rm2.getName() ;
										}
										
										if(rm3.getName().equals("")){
										}
										else{
											moduleResult = moduleResult + ", " + rm3.getName() ;
										}
									*/
										System.out.println("onSuccess - nach if - vor modulecomponentlabel");
										
									moduleComponentLabel.setText("Baugruppe(n): " + elementResult);
									
									modulePanel.setVisible(true);
									errorLabel.setVisible(false);
									
								}
								
								else if (result.equals("component")){
									
									System.out.println("onSuccess - element");
									
									Component element = new Component(name,"Aus feinem Edelholz mit schn&oumlrkel","Holz"); // Testzweck
									
									descriptionLabel.setText("Beschreibung: " + element.getDescription());
									materialLabel.setText("Material: " + element.getMaterial());
									elementPanel.setVisible(true);
									errorLabel.setVisible(false);
									
									System.out.println("Bauteil:" + name + element.getDescription() + element.getMaterial() );
								}
								else{
									System.out.println("Fehler im ASync");
								}
							
							}
				});
				
			}
			else{
				
				System.out.println("Result = false");
				
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