package de.hdm.sms.client.gui;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.bo.Component;

public class EditComponent extends VerticalPanel {
	private final ListBox ListOfComponents = new ListBox();
	private final AServiceAsync AsyncObj = GWT.create(AService.class);
	private String selectedComponent;
	private TextBox NameOfComponent = new TextBox();
	private final Label LabelOfNameComponentTextBox = new Label("Name");
	private final Label LabelOfDescriptionComponentTextBox = new Label(
			"Beschreibung");
	private final Label LabelOfMaterialDescriptionComponentTextBox = new Label(
			"Materialbeschreibung");
	private final Label LabelOfIdComponentTextBox = new Label("Id");
	private TextBox DescriptionOfComponent = new TextBox();
	private TextBox MaterialDescriptionOfComponent = new TextBox();
	private TextBox IdOfComponent = new TextBox();
	private String IdOfComponentString;
	private VerticalPanel ComponentItemPanel = new VerticalPanel();
	private Button DeleteComponentButton = new Button("Bauteil löschen");
	private HorizontalPanel ButtonPanel = new HorizontalPanel();
	private Button EditComponentButton = new Button("Bauteil editieren");

	public EditComponent() {

	}
private void updateComponent(Component c) {
	AsyncObj.updateComponentById(c, new AsyncCallback<Void>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Das Bauteil wurde editiert");
			
		}
	});
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
		RootPanel.get("content").add(ListOfComponents);
	}

	private void DeleteComponent(int DeleteComponentId) {
		AsyncObj.deleteComponentById(DeleteComponentId,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Bauteil erfolgreich gelöscht");
						RootPanel.get("content").clear();
						RootPanel.get("content").add(new CreateComponent());
						ListOfComponents.clear();
						LoadAllComponents();

					}
				});
	}

	public void onLoad() {
		ButtonPanel.add(EditComponentButton);
		ButtonPanel.add(DeleteComponentButton);
		LoadAllComponents();
		ListOfComponents.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				RootPanel.get("content").clear();
				selectedComponent = ListOfComponents
						.getItemText(ListOfComponents.getSelectedIndex());

				AsyncObj.getOneComponentIdByName(selectedComponent,
						new AsyncCallback<Component>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Component result) {
								IdOfComponentString = String.valueOf(result
										.getId());
								IdOfComponent.setText(IdOfComponentString);
								IdOfComponent.setEnabled(false);
								NameOfComponent.setText(result.getName());
								DescriptionOfComponent.setText(result
										.getDescription());
								MaterialDescriptionOfComponent.setText(result
										.getMaterial());
								ComponentItemPanel
										.add(LabelOfIdComponentTextBox);
								ComponentItemPanel.add(IdOfComponent);
								ComponentItemPanel
										.add(LabelOfNameComponentTextBox);
								ComponentItemPanel.add(NameOfComponent);
								ComponentItemPanel
										.add(LabelOfDescriptionComponentTextBox);
								ComponentItemPanel.add(DescriptionOfComponent);
								ComponentItemPanel
										.add(LabelOfMaterialDescriptionComponentTextBox);
								ComponentItemPanel
										.add(MaterialDescriptionOfComponent);
								ComponentItemPanel.add(ButtonPanel);
								RootPanel.get("content").clear();
								RootPanel.get("content")
										.add(ComponentItemPanel);
								DeleteComponentButton
										.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												DeleteComponent(Integer
														.parseInt(IdOfComponentString));
												RootPanel.get("content")
														.clear();
												RootPanel.get("content").add(
														ListOfComponents);
												RootPanel.get("content").add(
														ComponentItemPanel);
											}
										});
								EditComponentButton
										.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												if (NameOfComponent.getValue()
														.isEmpty()
														|| DescriptionOfComponent
																.getValue()
																.isEmpty()
														|| MaterialDescriptionOfComponent
																.getValue()
																.isEmpty()) {
													Window.alert("Bitte Felder befüllen!");
												} else {
													Component c = new Component();
													c.setId(Integer
															.parseInt(IdOfComponentString));
													c.setName(NameOfComponent
															.getText());
													c.setMaterial(MaterialDescriptionOfComponent
															.getText());
													c.setDescription(DescriptionOfComponent
															.getText());
													updateComponent(c);
												}

											}
										});

							}
						});

			}
		});
	}
}