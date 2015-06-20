package de.hdm.sms.client;

import java.util.ArrayList;

import de.hdm.sms.client.gui.Startside;
import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.ComponentGroup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateComponentGroup extends VerticalPanel {

	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private Component c = new Component();
	private final VerticalPanel createComponentGroupPanel = new VerticalPanel();
	private final HorizontalPanel createComponentGroupPanel2 = new HorizontalPanel();
	private final HorizontalPanel addComponentOrComponentGroupPanel = new HorizontalPanel();
	private final HorizontalPanel addComponentGroupPanel = new HorizontalPanel();
	private final ListBox listOfComponents = new ListBox();
	private final ListBox listOfComponentGroups = new ListBox();
	private final Label nameOfComponentGroupLabel = new Label("Baugruppenname");
	private final TextBox nameOfComponentGroupTextBox = new TextBox();
	private final FlexTable componentTable = new FlexTable();
	private final Label nameOfComponentLabel = new Label("Bauteile");
	private final Button addComponentButton = new Button("Bauteil hinzufügen");
	private final Button addComponentGroupButton = new Button("Baugruppe hinzufügen");
	private final Button createComponentGroupButton = new Button("Baugruppe anlegen");
	private ComponentGroup cg = new ComponentGroup();

	public CreateComponentGroup() {

	}

	private void loadAllComponents() {

		listOfComponents.setSize("180px", "35px");
		// ListOfComponents.addStyleName("mainmenu-dropdown");
		listOfComponents.addItem("---");

		asyncObj.loadAllComponents(new AsyncCallback<ArrayList<Component>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<Component> result) {
				for (int i = 0; i < result.size(); i++) {

					listOfComponents.addItem(result.get(i).getName());

				}

			}
		});
		
		
	}
	private void loadAllComponentGroups() {

		listOfComponentGroups.setSize("180px", "35px");
		listOfComponentGroups.addItem("---");

		asyncObj.loadAllComponentGroups(new AsyncCallback<ArrayList<ComponentGroup>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<ComponentGroup> result) {
				for (int i = 0; i < result.size(); i++) {

					listOfComponentGroups.addItem(result.get(i).getName());

				}

			}
		});
		RootPanel.get("rightside").add(listOfComponentGroups);
	}
	public void onLoad() {
		loadAllComponents();
		loadAllComponentGroups();
		//ListOfComponentGroups.setSize("180px", "35px");
		createComponentGroupPanel.add(nameOfComponentGroupLabel);
		createComponentGroupPanel2.add(nameOfComponentGroupTextBox);
		createComponentGroupPanel2.add(createComponentGroupButton);
		
		addComponentOrComponentGroupPanel.add(listOfComponents);
		addComponentOrComponentGroupPanel.add(addComponentButton);
		addComponentOrComponentGroupPanel.add(listOfComponentGroups);
		addComponentOrComponentGroupPanel.add(addComponentGroupButton);
		RootPanel.get("rightside").add(createComponentGroupPanel);
		RootPanel.get("rightside").add(createComponentGroupPanel2);
		RootPanel.get("rightside").add(addComponentOrComponentGroupPanel);
		RootPanel.get("rightside").add(addComponentGroupPanel);
		RootPanel.get("rightside").add(nameOfComponentLabel);
		
		
		
		//RootPanel.get("rightside").add(CreateComponentGroupPanel);
		
		componentTable.setText(0, 0, "ID");
		componentTable.setText(0, 1, "Name");
		componentTable.setText(0, 2, "Anzahl");
		RootPanel.get("rightside").add(componentTable);
		
		createComponentGroupButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (nameOfComponentGroupTextBox.getValue().isEmpty()) {
					Window.alert("Bitte alle Felder befüllen");
				} else {
					
					cg.setName(nameOfComponentGroupTextBox.getValue());
					
					asyncObj.insertComponentGroup(cg, new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							Window.alert("Baugruppe " + cg.getName()
									+ " erfolgreich angelegt.");
							RootPanel.get("rightside").clear();
							//Startside sS = new Startside();
							//RootPanel.get().add(sS);

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