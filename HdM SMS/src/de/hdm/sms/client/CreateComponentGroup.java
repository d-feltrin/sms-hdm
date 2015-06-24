package de.hdm.sms.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.ComponentGroup;

public class CreateComponentGroup extends VerticalPanel {

	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private ArrayList<Component> allComponents = new ArrayList<Component>();
	private ArrayList<ComponentGroup> allComponentGroups = new ArrayList<ComponentGroup>();
	private ComponentGroup newComponentGroup = new ComponentGroup();
	private int ComponentListBoxID = 0; //Variable to determine if part to add is component or componentgroup 

	// GUI Objects
	private final HorizontalPanel NamePanel = new HorizontalPanel();
	private final TextBox NewComponentGroupName = new TextBox();

	private final HorizontalPanel TablePanel = new HorizontalPanel();
	private final FlexTable ComponentGroupParts = new FlexTable();

	private final HorizontalPanel AddPanel = new HorizontalPanel();
	private final ListBox listOfAddableParts = new ListBox();
	private final TextBox textBoxAmount = new TextBox();
	private final Button AddComponentGroupButton = new Button("Bauteil/-gruppe hinzufuegen");

	private final HorizontalPanel SubmitPanel = new HorizontalPanel();
	private final Button SubmitComponentGroupButton = new Button("Baugruppe anlegen");

	public void onLoad() {
		listOfAddableParts.addItem("----");
		loadAllComponents();
		loadAllComponentGroups();
		
		

		RootPanel.get("rightside").add(AddPanel);

		// Panel: Name
		NamePanel.add(new Label("Baugruppenname"));
		NamePanel.add(NewComponentGroupName);
		RootPanel.get("rightside").add(NamePanel);

		// Panel: Table
		ComponentGroupParts.setText(0, 0, "Typ");
		ComponentGroupParts.setText(0, 1, "ID");
		ComponentGroupParts.setText(0, 2, "Name");
		ComponentGroupParts.setText(0, 3, "Anzahl");
		TablePanel.add(ComponentGroupParts);
		RootPanel.get("rightside").add(TablePanel);

		// Panel: Add
		AddComponentGroupButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (listOfAddableParts.getItemText(listOfAddableParts.getSelectedIndex()).equals("----")
						|| listOfAddableParts.getItemText(listOfAddableParts.getSelectedIndex()).equals("Bauteil")
						|| listOfAddableParts.getItemText(listOfAddableParts.getSelectedIndex()).equals("Baugruppe")) {
					Window.alert("Bitte waehlen Sie ein gueltiges Bauteil bzw. Bauelement aus!");

				}
				else if(textBoxAmount.getText().equals("")){
					Window.alert("Bitte die Anzahl des hinzuzufuegenden Bauteils/Baugruppe eintragen!");
				}
				else if(Integer.parseInt(textBoxAmount.getText()) < 1 || Integer.parseInt(textBoxAmount.getText()) > 100 ){
					Window.alert("Anzahl des hinzuzufuegenden Bauteils/Baugruppe darf nicht unter 0 sein!");
				}
				
				
				else {
					int rowToadd = ComponentGroupParts.getRowCount();
					
					//when index of selected item of listOfAddableParts < ComponentListBoxID its a ComponentGroup
					//when its higher its a Component
					if(listOfAddableParts.getSelectedIndex()<ComponentListBoxID){
						ComponentGroupParts.setText(rowToadd, 0, "Baugruppe");
						
					}else{
						ComponentGroupParts.setText(rowToadd, 0, "Bauteil");
						
					}
					
					//get ID by ListBox text
					//Splitt " - 569:Bla" into " - 569", "Bla"
					String[] SplitStepOne = listOfAddableParts.getItemText(listOfAddableParts.getSelectedIndex()).split(":");

					//Splitt " - 569" into " - ", "569"
					String[] SplitStepTwo = SplitStepOne[0].split(" ");
					
					ComponentGroupParts.setText(rowToadd, 1, SplitStepTwo[1]);
					//TODO: ANZAHL IN REIHEN ALS TEXTBOX DAMIT NOCH BEARBEITET WERDEN KANN!!!!!
					ComponentGroupParts.setText(rowToadd, 2, listOfAddableParts.getItemText(listOfAddableParts.getSelectedIndex()).substring(SplitStepOne[0].length()+1));
					ComponentGroupParts.setText(rowToadd, 3, textBoxAmount.getText());
				}
			}
		});

		AddPanel.add(listOfAddableParts);
		AddPanel.add(new Label("Anzahl:"));
		textBoxAmount.addKeyPressHandler(new NumbersOnly());
		AddPanel.add(textBoxAmount);
		AddPanel.add(AddComponentGroupButton);
		RootPanel.get("rightside").add(AddPanel);

		// Panel: Submit
		SubmitComponentGroupButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//CREATE Componentgroup Object
				//add to DB
				//clear fields
			}
		});
		SubmitPanel.add(SubmitComponentGroupButton);
		RootPanel.get("rightside").add(SubmitPanel);
	}

	private void loadAllComponents() {
		asyncObj.loadAllComponents(new AsyncCallback<ArrayList<Component>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<Component> Components) {
				
				ComponentListBoxID = listOfAddableParts.getItemCount();
				//Window.alert(String.valueOf((listOfAddableParts.getItemCount())));
				listOfAddableParts.addItem("Bauteil");
				for (Component component : Components) {
					allComponents.add(component);
					listOfAddableParts.addItem(" - " + component.getId() + ":" + component.getName());
				}
			}
		});
	}

	private void loadAllComponentGroups() {

		asyncObj.loadAllComponentGroups(new AsyncCallback<ArrayList<ComponentGroup>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<ComponentGroup> ComponentGroups) {

				listOfAddableParts.addItem("Baugruppe");
				for (ComponentGroup componentgroup : ComponentGroups) {
					allComponentGroups.add(componentgroup);
					listOfAddableParts.addItem(" - " + componentgroup.getId() + ":" + componentgroup.getName());
				}

			}
		});
	}

	
	class NumbersOnly implements KeyPressHandler {

        @Override
        public void onKeyPress(KeyPressEvent event) {
            if (!Character.isDigit(event.getCharCode()) 
                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB 
                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE){
                ((IntegerBox) event.getSource()).cancelKey();
            }
        }
    }
}
