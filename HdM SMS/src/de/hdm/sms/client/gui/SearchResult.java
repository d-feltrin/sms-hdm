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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.Module;

public class SearchResult extends VerticalPanel {
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	private DockPanel dockPanel = new DockPanel();
	private VerticalPanel buttonPanel = new VerticalPanel();
	private VerticalPanel modulePanel = new VerticalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel editMainPanel = new VerticalPanel();
	private VerticalPanel editElementPanel = new VerticalPanel();
	private VerticalPanel editModulePanel = new VerticalPanel();
	private VerticalPanel dialogboxVPanel = new VerticalPanel();
	private HorizontalPanel selectElementPanel = new HorizontalPanel();
	private HorizontalPanel selectModulePanel = new HorizontalPanel();
	private HTML moduleTohtml = new HTML();
	private HTML elementTohtml = new HTML();
	private HTML convTohtml = new HTML();
	private Button deleteButton = new Button("L&oumlschen");
	private Button editButton = new Button("Bearbeiten");
	private Button closeButton = new Button("Close");
	private Button acceptButton = new Button("Akzeptieren");
	private Button selectModuleButton = new Button("+");
	private Button selectElementButton = new Button("+");
	private ListBox moduleListBox = new ListBox();
	private ListBox elementListBox = new ListBox();
	private Label searchResultLabel = new Label("Suchergebnis");
	private Label errorLabel = new Label("Die Komponente konnte nicht gefunden werden!");
	private Label endproductLabel = new Label();
	private Label editNameLabel = new Label("Neuer Name");
	private Label editDescriptionLabel = new Label("Neue Beschreibung");
	private Label editMaterialLabel = new Label("Neues Material");
	private TextBox editNameTextBox = new TextBox();
	private TextBox editDescriptionTextBox = new TextBox();
	private TextBox editMaterialTextBox = new TextBox();
	private HTML serverResponseLabel = new HTML();
	private CheckBox cb0 = new CheckBox("Endprodukt");
	private String name;
	private Module module;
	private DialogBox dialogBox = new DialogBox();
	private Boolean result;
	
	public SearchResult(Boolean result){
		this.result = result;
		
	}
	
	public SearchResult(Boolean result, String nameLabel){
		this.result = result;
		this.name = nameLabel;
		
	}
	
	public void show(){
		
		greetingService.getTypeOfComponent(name,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {

					}

					public void onSuccess(String result) {
						
						if (result.equals("module")){
							
							Component c1 = new Component("Brett","Aus feinem Edelholz mit schnörkel","Holz");
							Component c2 = new Component("Nagel","Spitz und lang","Stahl");
							Component c3 = new Component("Holzplatte","Aus feinem Edelholz mit schnörkel","Holz");
							
							Module m1 = new Module("Tischbein", c1, c2, false);
							Module m2 = new Module("Tisch", m1, m1, c3, true); //ALLES TEST
							
							module = m2;
							
							Boolean endproductState = m2.getEndproductState();
							
							if (endproductState){
								
								endproductLabel.setText("Als Endprodukt gekennzeichnet");
								}
							
								else{
								
								endproductLabel.setText("Als kein Endprodukt gekennzeichnet.");
							}
							
							String elementResult = "";
							
							ArrayList<Component> component = new ArrayList<Component>();
							component = m2.getComponent();
							
							for(int i=0; i<component.size(); i++){
								Component com = component.get(i);
								elementResult = elementResult + com.getName() + "<br>";
							}
							
							elementTohtml.setHTML("<b>Bauteil(e):</b> <br>" + elementResult);
							
							String moduleResult = "";
							
							ArrayList<Module> module = new ArrayList<Module>();
							module = m2.getModule();
							
							for(int i=0; i<module.size(); i++){
								Module mod = module.get(i);
								moduleResult = moduleResult + mod.getName() + "<br>";
							}
							
							moduleTohtml.setHTML("<b>Baugruppe(n):</b> <br>" + moduleResult);
							
							modulePanel.setVisible(true);
							errorLabel.setVisible(false);
							
						}
						
						else if (result.equals("component")){
							
							Component element = new Component("Brett","Aus feinem Edelholz mit schn&oumlrkel","Holz"); // Testzweck
							
							String conv = "<b>Name:</b> <br>" + element.getName() +"<br><br><b>Beschreibung:</b> <br>" + element.getDescription() +"<br><br><b>Material:</b> <br>" + element.getMaterial();
							convTohtml.setHTML(conv);
							convTohtml.setVisible(true);
							errorLabel.setVisible(false);
						}
						else{
							System.out.println("Fehler im Asyn getTypeofContent");
						}
					
					}
		});
		
	}
		
	private void edit(){
		
		greetingService.getTypeOfComponent(name,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
							
					}

					public void onSuccess(String result) {
						
						if (result.equals("module")){
							editElementPanel.setVisible(false);
							editModulePanel.setVisible(true);
							acceptButton.setVisible(false);
						}
						
						else if (result.equals("component")){
							editModulePanel.setVisible(false);
							editElementPanel.setVisible(true);
							acceptButton.setVisible(false);
						}
						
						else{
							System.out.println("Fehler beim Bearbeiten der Komponente");
						}						
					}
				});
	}
	
	private void accept(){
		editElementPanel.setVisible(false);
		String description = editDescriptionTextBox.getText();
		String material = editMaterialTextBox.getText();
		greetingService.edit(name, description, material,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
							
					}

					public void onSuccess(String result) {
						
						dialogBox.setText("Komponente - Bearbeiten");
						serverResponseLabel.setHTML(result);
						dialogBox.center();
						closeButton.setFocus(true);
						
					}
				});
	}
	
	private void delete(){
		
		greetingService.delete(name,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
							
					}

					public void onSuccess(String result) {
						
						dialogBox.setText("Komponente - Entfernen");
						serverResponseLabel.setHTML(result);
						dialogBox.center();
						closeButton.setFocus(true);
						
					}
				});
		
	}
	
	
	// ONLOAD ########################################################################################################
	
	
		public void onLoad() {
			
			editButton.setPixelSize(180, 30);
			deleteButton.setPixelSize(180, 30);
			acceptButton.setPixelSize(180, 30);
			selectElementButton.setPixelSize(30, 30);
			selectModuleButton.setPixelSize(30, 30);
			elementListBox.setPixelSize(180, 30);
			moduleListBox.setPixelSize(180, 30);
			
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
			
			buttonPanel.add(editButton);
			buttonPanel.add(deleteButton);
			
			modulePanel.add(new HTML("<b>Name:</b> <br>" + name +"<br><br>"));
			modulePanel.add(moduleTohtml);
			modulePanel.add(elementTohtml);
			modulePanel.add(endproductLabel);
			
			mainPanel.add(modulePanel);
			mainPanel.add(convTohtml);
			
			editElementPanel.add(editNameLabel);
			editElementPanel.add(editNameTextBox);
			editElementPanel.add(editDescriptionLabel);
			editElementPanel.add(editDescriptionTextBox);
			editElementPanel.add(editMaterialLabel);
			editElementPanel.add(editMaterialTextBox);
			
			selectElementPanel.add(elementListBox);
			selectElementPanel.add(selectElementButton);
			
			selectModulePanel.add(moduleListBox);
			selectModulePanel.add(selectModuleButton);
			
			editModulePanel.add(selectElementPanel);
			editModulePanel.add(selectModulePanel);
			editModulePanel.add(cb0);
			
			editMainPanel.add(editElementPanel);
			editMainPanel.add(editModulePanel);
			editMainPanel.add(acceptButton);
			
			dockPanel.add(searchResultLabel, DockPanel.NORTH); 		//North
			dockPanel.add(mainPanel, DockPanel.WEST); 				//West
			dockPanel.add(editMainPanel, DockPanel.EAST); 		//East
			dockPanel.add(errorLabel, DockPanel.SOUTH); 			//South
			dockPanel.add(buttonPanel, DockPanel.NORTH); 			//Second North
			dockPanel.add(new HTML(" "), DockPanel.SOUTH); 			//Second South
			
			dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
			dockPanel.setStyleName("dockpanel");
			dockPanel.setSpacing(5);
			searchResultLabel.setStyleName("header");
			
			editElementPanel.setVisible(false);
			editModulePanel.setVisible(false);
			convTohtml.setVisible(false);
			modulePanel.setVisible(false);
			errorLabel.setVisible(false);
			acceptButton.setVisible(false);
			
			if(result){
				
				show();
				
			}
			else{
				
				System.out.println("error");
				convTohtml.setVisible(false);
				buttonPanel.setVisible(false);
				errorLabel.setVisible(true);
				errorLabel.addStyleName("serverResponseLabelError");
			}
			
		    RootPanel.get("rightside").add(dockPanel);
		    
	// DIALOGBOX ########################################################################################################
		    
		    
			dialogBox.setAnimationEnabled(true);
			
			dialogboxVPanel.add(new HTML("<br><br>"));
			dialogboxVPanel.add(serverResponseLabel);
			dialogboxVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
			dialogboxVPanel.add(closeButton);
			dialogBox.setWidget(dialogboxVPanel);
		 	
			
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
			
			closeButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {

					dialogBox.hide();

				}
			});
					
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
		 	
		 	acceptButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {

					accept();

				}
			});
			
		}

	}