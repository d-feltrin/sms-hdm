package de.hdm.sms.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

//Test-Commit
//Test Commit

public class HdM_SMS implements EntryPoint {
	private Button CreateComponentButton = new Button("Bauteil bearbeiten");
	private Button CreateUserButton = new Button("Benutzer anlegen");

	public HdM_SMS() {

	}

	public void onModuleLoad() {
		RootPanel.get("content").add(CreateComponentButton);
		RootPanel.get("content").add(CreateUserButton);
		CreateComponentButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				RootPanel.get("content").clear();
				RootPanel.get("content").add(new EditComponent());
				RootPanel.get("content").add(new CreateComponent());

			}
		});
		CreateUserButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(new EditUser());
				RootPanel.get("content").add(new CreateUser());

			}
		});
	}
}
