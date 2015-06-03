package de.hdm.sms.client.gui;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.shared.FieldVerifier;

public class SearchComponent extends VerticalPanel {
	
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	private DockPanel dockPanel = new DockPanel();
	private VerticalPanel buttonPanel = new VerticalPanel();
	private VerticalPanel dialogboxVPanel = new VerticalPanel();
	private Button searchButton = new Button("Suchen");
	private Button createButton = new Button("Anlegen");
	private Button logoutButton = new Button("Logout");
	private Button closeButton = new Button("Close");
	private TextBox searchTextBox = new TextBox();
	private Label searchLabel = new Label("Produktsuche");
	private Label dialogboxLabel = new Label();
	private HTML serverResponseLabel = new HTML();
	private DialogBox dialogBox = new DialogBox();
	
	public void search(){

		String textToServer = searchTextBox.getText();
		
		greetingService.search(textToServer,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {

					}

					public void onSuccess(String result) {
						
						String componentName = searchTextBox.getText();
						
						if (result.equals("j")){
							
							Boolean resultValue = true;
							RootPanel.get("rightside").clear();
							RootPanel.get("rightside").add(new SearchResult(resultValue, componentName));
						}
						else if (result.equals("n"))
						{
							Boolean resultValue = false;
							RootPanel.get("rightside").clear();
							RootPanel.get("rightside").add(new SearchResult(resultValue));
						}
						else{
							System.out.println("Fehler im ASync");
						}
						
					}
				});

	}

	public void logout(){
		
		greetingService.getName(new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(String result) {
						serverResponseLabel.setHTML(result);
						dialogBox.center();
						closeButton.setFocus(true);
						RootPanel.get("leftside").clear();
						RootPanel.get("leftside").add(new Startside());
						
					}
				});
	}
	

	// ONLOAD ########################################################################################################

	
	public void onLoad() {
		
		searchButton.setPixelSize(180, 30);
		createButton.setPixelSize(180, 30);
		logoutButton.setPixelSize(180, 30);
		
		searchTextBox.setText("Produktname/nummer");
		
		buttonPanel.add(searchButton);
		buttonPanel.add(createButton);

		dockPanel.add(searchLabel, DockPanel.NORTH); 		//North
		dockPanel.add(searchTextBox, DockPanel.WEST); 		//West
		dockPanel.add(logoutButton, DockPanel.EAST); 		//East
		dockPanel.add(new HTML(" "), DockPanel.SOUTH); 		//South
		dockPanel.add(buttonPanel, DockPanel.NORTH); 		//Second North
		dockPanel.add(new HTML(" "), DockPanel.SOUTH); 		//Second South
		
		dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
		dockPanel.setStyleName("dockpanel");
		dockPanel.setSpacing(5);
		searchLabel.setStyleName("header");
		
		RootPanel.get("leftside").add(dockPanel);
		
		searchTextBox.setFocus(true);
	    searchTextBox.selectAll();
	    
	    
	 // DIALOGBOX ########################################################################################################
	    
	    
	 	dialogBox.setText("Logout");
	 	dialogBox.setAnimationEnabled(true);
	 		
	 	dialogboxVPanel.add(dialogboxLabel);
	 	dialogboxVPanel.add(new HTML("<br>"));
	 	dialogboxVPanel.add(serverResponseLabel);
	 	dialogboxVPanel.add(new HTML("<br>Sie wurden erfolgreich abgemeldet!<br>"));
	 	dialogboxVPanel.add(new HTML("<br>"));
	 	dialogboxVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
	 	dialogboxVPanel.add(closeButton);
	 	dialogBox.setWidget(dialogboxVPanel);
	 		
		
	// HANDLER ########################################################################################################
		
		
	 	searchTextBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) 
					{
						search();
					}
				}
		});		
				
		closeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				dialogBox.hide();

			}
		});
		
	 	logoutButton.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						logout();
						RootPanel.get("leftside").clear();
						RootPanel.get("leftside").add(new Startside());
						RootPanel.get("rightside").clear();
						RootPanel.get("rightside").add(new ImageSMS());
					}
		});
	 	
	 	createButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				RootPanel.get("leftside").clear();
				RootPanel.get("leftside").add(new CreateComponent());

			}
		});
	 	
	 	searchButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				search();

			}
		});
	 	
	}

}
