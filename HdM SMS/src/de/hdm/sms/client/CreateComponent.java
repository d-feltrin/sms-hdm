package de.hdm.sms.client;

import de.hdm.sms.client.gui.Startside;
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
	private VerticalPanel createComponentPanel = new VerticalPanel();
	private Label nameLabel = new Label("Name des Bauteils");
	private Label descriptionLabel = new Label("Beschreibung");
	private Label materialDescriptionLabel = new Label("Materialbezeichnung");
	private TextBox nameTextbox = new TextBox();
	private TextBox descriptionTextbox = new TextBox();
	private TextBox materialDescriptionTextbox = new TextBox();
	private Button createComponentButton = new Button("Bauteil anlegen");
	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private Component c = new Component();

	public CreateComponent() {

	}

	public void onLoad() {
		createComponentPanel.add(nameLabel);
		createComponentPanel.add(nameTextbox);
		createComponentPanel.add(descriptionLabel);
		createComponentPanel.add(descriptionTextbox);
		createComponentPanel.add(materialDescriptionLabel);
		createComponentPanel.add(materialDescriptionTextbox);
		createComponentPanel.add(createComponentButton);
		RootPanel.get("rightside").add(createComponentPanel);

		createComponentButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (nameTextbox.getValue().isEmpty()
						|| descriptionTextbox.getValue().isEmpty()
						|| materialDescriptionTextbox.getValue().isEmpty()) {
					Window.alert("Bitte alle Felder befüllen");
				} else {
					c.setDescription(descriptionTextbox.getValue());
					c.setName(nameTextbox.getValue());
					c.setMaterialDescription(materialDescriptionTextbox
							.getValue());
					asyncObj.insertComponent(c, new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							Window.alert("Bauteil " + c.getName()
									+ " erfolgreich angelegt.");
							RootPanel.get("rightside").clear();
							Startside sS = new Startside();
							RootPanel.get().add(sS);

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

}