package de.hdm.sms.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ImageSMS extends VerticalPanel{

	private FlowPanel imagePanel = new FlowPanel();
	private Image toolImg = new Image();
	
	
	// ONLOAD ########################################################################################################
	
	
public void onLoad() {
	
		toolImg.setPixelSize(400, 300);
		toolImg.setUrl("logistic.jpg");
		
		imagePanel.add(toolImg);
		
		RootPanel.get("rightside").add(imagePanel);
			
	}
}
