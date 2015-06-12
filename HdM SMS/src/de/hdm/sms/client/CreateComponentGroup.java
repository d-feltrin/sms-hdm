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

	private final AServiceAsync AsyncObj = GWT.create(AService.class);
	private Component c = new Component();
	private final VerticalPanel CreateComponentGroupPanel = new VerticalPanel();
	private final HorizontalPanel CreateComponentGroupPanel2 = new HorizontalPanel();
	private final HorizontalPanel AddComponentOrComponentGroupPanel = new HorizontalPanel();
	private final HorizontalPanel AddComponentGroupPanel = new HorizontalPanel();
	private final ListBox ListOfComponents = new ListBox();
	private final ListBox ListOfComponentGroups = new ListBox();
	private final Label NameOfComponentGroupLabel = new Label("Baugruppenname");
	private final TextBox NameOfComponentGroupTextBox = new TextBox();
	private final FlexTable ComponentTable = new FlexTable();
	private final Label NameOfComponentLabel = new Label("Bauteile");
	private final Button AddComponentButton = new Button("Bauteil hinzufügen");
	private final Button AddComponentGroupButton = new Button("Baugruppe hinzufügen");
	private final Button CreateComponentGroupButton = new Button("Baugruppe anlegen");
	private ComponentGroup cg = new ComponentGroup();

	public CreateComponentGroup() {

	}

	private void LoadAllComponents() {

		ListOfComponents.setSize("180px", "35px");
		// ListOfComponents.addStyleName("mainmenu-dropdown");
		ListOfComponents.addItem("---");

		AsyncObj.loadAllComponents(new AsyncCallback<ArrayList<Component>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<Component> result) {
				for (int i = 0; i < result.size(); i++) {

					ListOfComponents.addItem(result.get(i).getName());

				}

			}
		});
		
		
	}
	private void LoadAllComponentGroups() {

		ListOfComponentGroups.setSize("180px", "35px");
		ListOfComponentGroups.addItem("---");

		AsyncObj.loadAllComponentGroups(new AsyncCallback<ArrayList<ComponentGroup>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<ComponentGroup> result) {
				for (int i = 0; i < result.size(); i++) {

					ListOfComponentGroups.addItem(result.get(i).getName());

				}

			}
		});
		RootPanel.get("rightside").add(ListOfComponentGroups);
	}
	public void onLoad() {
		LoadAllComponents();
		LoadAllComponentGroups();
		//ListOfComponentGroups.setSize("180px", "35px");
		CreateComponentGroupPanel.add(NameOfComponentGroupLabel);
		CreateComponentGroupPanel2.add(NameOfComponentGroupTextBox);
		CreateComponentGroupPanel2.add(CreateComponentGroupButton);
		
		AddComponentOrComponentGroupPanel.add(ListOfComponents);
		AddComponentOrComponentGroupPanel.add(AddComponentButton);
		AddComponentOrComponentGroupPanel.add(ListOfComponentGroups);
		AddComponentOrComponentGroupPanel.add(AddComponentGroupButton);
		RootPanel.get("rightside").add(CreateComponentGroupPanel);
		RootPanel.get("rightside").add(CreateComponentGroupPanel2);
		RootPanel.get("rightside").add(AddComponentOrComponentGroupPanel);
		RootPanel.get("rightside").add(AddComponentGroupPanel);
		RootPanel.get("rightside").add(NameOfComponentLabel);
		
		
		
		//RootPanel.get("rightside").add(CreateComponentGroupPanel);
		
		ComponentTable.setText(0, 0, "ID");
		ComponentTable.setText(0, 1, "Name");
		ComponentTable.setText(0, 2, "Anzahl");
		RootPanel.get("rightside").add(ComponentTable);
		
		CreateComponentGroupButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (NameOfComponentGroupTextBox.getValue().isEmpty()) {
					Window.alert("Bitte alle Felder befüllen");
				} else {
					
					c.setName(NameOfComponentGroupTextBox.getValue());
					
					AsyncObj.insertComponentGroup(cg, new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							Window.alert("Baugruppe " + cg.getName()
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