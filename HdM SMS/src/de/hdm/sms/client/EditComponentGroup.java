package de.hdm.sms.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.client.gui.Startside;
import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.ComponentGroup;

public class EditComponentGroup extends VerticalPanel {

	private final ListBox ListOfComponentGroups = new ListBox();
	private final AServiceAsync AsyncObj = GWT.create(AService.class);
	private HorizontalPanel ButtonPanel = new HorizontalPanel();
	private Button EditComponentGroupButton = new Button("Baugruppe editieren");
	
	public EditComponentGroup() {

	}
	
	private void updateComponentGroup(ComponentGroup cg) {
		AsyncObj.updateComponentGroupById(cg, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Die Baugruppe wurde editiert");
				RootPanel.get("rightside").clear();
				Startside sS = new Startside();
				RootPanel.get().add(sS);
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
		ButtonPanel.add(EditComponentGroupButton);
		LoadAllComponentGroups();
		
	}
}
