package de.hdm.sms.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.client.gui.ImageSMS;
import de.hdm.sms.client.gui.Impressum;
import de.hdm.sms.client.gui.Startside;

public class HdM_SMS implements EntryPoint {
	
	private VerticalPanel bottomPanel = new VerticalPanel();
	private Label aboutLabel = new Label("Impressum");
	
	
	// ONLOAD ########################################################################################################
	
	
	public void onModuleLoad() {
		
		aboutLabel.addStyleName("impressum");
		bottomPanel.add(aboutLabel);
		
		RootPanel.get("leftside").add(new Startside());
		RootPanel.get("rightside").add(new ImageSMS());
		RootPanel.get("bottom").add(aboutLabel);
		
		aboutLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				RootPanel.get("rightside").clear();
				RootPanel.get("rightside").add(new Impressum());
				
			}
		});
	}
}
