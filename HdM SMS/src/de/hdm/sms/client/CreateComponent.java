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
	private VerticalPanel CreateComponentPanel = new VerticalPanel();
	private Label NameLabel = new Label("Name des Bauteils");
	private Label DescriptionLabel = new Label("Beschreibung");
	private Label MaterialDescriptionLabel = new Label("Materialbezeichnung");
	private TextBox NameTextbox = new TextBox();
	private TextBox DescriptionTextbox = new TextBox();
	private TextBox MaterialDescriptionTextbox = new TextBox();
	private Button CreateComponentButton = new Button("Bauteil anlegen");
	private final AServiceAsync AsyncObj = GWT.create(AService.class);
	private Component c = new Component();

	public CreateComponent() {

	}

	public void onLoad() {
		CreateComponentPanel.add(NameLabel);
		CreateComponentPanel.add(NameTextbox);
		CreateComponentPanel.add(DescriptionLabel);
		CreateComponentPanel.add(DescriptionTextbox);
		CreateComponentPanel.add(MaterialDescriptionLabel);
		CreateComponentPanel.add(MaterialDescriptionTextbox);
		CreateComponentPanel.add(CreateComponentButton);
		RootPanel.get("rightside").add(CreateComponentPanel);

		CreateComponentButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (NameTextbox.getValue().isEmpty()
						|| DescriptionTextbox.getValue().isEmpty()
						|| MaterialDescriptionTextbox.getValue().isEmpty()) {
					Window.alert("Bitte alle Felder befüllen");
				} else {
					c.setDescription(DescriptionTextbox.getValue());
					c.setName(NameTextbox.getValue());
					c.setMaterialDescription(MaterialDescriptionTextbox
							.getValue());
					AsyncObj.insertComponent(c, new AsyncCallback<Void>() {

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