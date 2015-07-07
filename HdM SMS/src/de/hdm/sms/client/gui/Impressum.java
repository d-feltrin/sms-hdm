package de.hdm.sms.client.gui;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Impressum extends VerticalPanel {

	private VerticalPanel impressumPanel = new VerticalPanel();
	private HTML impressumHtml = new HTML();
	private String name = "IT-Projekt SS 2015<br>"
			+ "Stuecklistenmanagementsystem<br>"
			+ "Hochschule der Medien Stuttgart<br>"
			+ "Gruppe 06<br>"
			+ "Denis Feltrin (24919)<br> Erich Meisner (25307)";

	// Load
	public void onLoad() {

		impressumPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
		impressumHtml.setHTML(name);
		impressumPanel.add(impressumHtml);
		impressumPanel.addStyleName("impressum");

		RootPanel.get("rightside").add(impressumPanel);

	}
}
