package de.hdm.sms.client;

import java.util.ArrayList;

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
	private final HorizontalPanel AddComponentPanel = new HorizontalPanel();
	private final ListBox ListOfComponents = new ListBox();
	private final Label NameOfComponentGroupLabel = new Label("Baugruppenname");
	private final TextBox NameOfComponentTextBox = new TextBox();
	private final FlexTable ComponentTable = new FlexTable();
	private final Label NameOfComponentLabel = new Label("Bauteile");
	private final Button AddComponentButton = new Button("Bauteil hinzufügen");
	

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

	public void onLoad() {
		LoadAllComponents();	
		
		CreateComponentGroupPanel.add(NameOfComponentGroupLabel);
		CreateComponentGroupPanel.add(NameOfComponentTextBox);
		
		AddComponentPanel.add(ListOfComponents);
		AddComponentPanel.add(AddComponentButton);
		
		RootPanel.get("leftside").add(AddComponentPanel);
		RootPanel.get("leftside").add(NameOfComponentLabel);
		RootPanel.get("leftside").add(CreateComponentGroupPanel);
		
		RootPanel.get("leftside").add(CreateComponentGroupPanel);
		
		ComponentTable.setText(0, 0, "ID");
		ComponentTable.setText(0, 1, "Name");
		ComponentTable.setText(0, 2, "Anzahl");
		RootPanel.get("leftside").add(ComponentTable);
		
		
		
		

	}
}