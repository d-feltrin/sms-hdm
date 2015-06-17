package de.hdm.sms.client;

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

import de.hdm.sms.client.gui.Startside;
import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.bo.Component;

public class EditComponent extends VerticalPanel {
	private final ListBox listOfComponents = new ListBox();
	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private String selectedComponent;
	private TextBox nameOfComponent = new TextBox();
	private final Label labelOfNameComponentTextBox = new Label("Name");
	private final Label labelOfDescriptionComponentTextBox = new Label(
			"Beschreibung");
	private final Label labelOfMaterialDescriptionComponentTextBox = new Label(
			"Materialbeschreibung");
	private final Label labelOfIdComponentTextBox = new Label("Id");
	private TextBox descriptionOfComponent = new TextBox();
	private TextBox materialDescriptionOfComponent = new TextBox();
	private TextBox idOfComponent = new TextBox();
	private String idOfComponentString;
	private VerticalPanel componentItemPanel = new VerticalPanel();
	private Button deleteComponentButton = new Button("Bauteil löschen");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button editComponentButton = new Button("Bauteil editieren");

	public EditComponent() {

	}

	private void updateComponent(Component c) {
		asyncObj.updateComponentById(c, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Das Bauteil wurde editiert");
				RootPanel.get("rightside").clear();
				Startside sS = new Startside();
				RootPanel.get().add(sS);
			}
		});
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
		RootPanel.get("rightside").add(listOfComponents);
	}

	private void deleteComponent(int DeleteComponentId) {
		asyncObj.deleteComponentById(DeleteComponentId,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Bauteil erfolgreich gelöscht");
						RootPanel.get("rightside").clear();
						Startside sS = new Startside();
						RootPanel.get().add(sS);

					}
				});
	}

	public void onLoad() {
		buttonPanel.add(editComponentButton);
		buttonPanel.add(deleteComponentButton);
		loadAllComponents();
		listOfComponents.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				RootPanel.get("rightside").clear();
				selectedComponent = listOfComponents
						.getItemText(listOfComponents.getSelectedIndex());

				asyncObj.getOneComponentIdByName(selectedComponent,
						new AsyncCallback<Component>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Component result) {
								idOfComponentString = String.valueOf(result
										.getId());
								idOfComponent.setText(idOfComponentString);
								idOfComponent.setEnabled(false);
								nameOfComponent.setText(result.getName());
								descriptionOfComponent.setText(result
										.getDescription());
								materialDescriptionOfComponent.setText(result
										.getMaterialDescription());
								componentItemPanel
										.add(labelOfIdComponentTextBox);
								componentItemPanel.add(idOfComponent);
								componentItemPanel
										.add(labelOfNameComponentTextBox);
								componentItemPanel.add(nameOfComponent);
								componentItemPanel
										.add(labelOfDescriptionComponentTextBox);
								componentItemPanel.add(descriptionOfComponent);
								componentItemPanel
										.add(labelOfMaterialDescriptionComponentTextBox);
								componentItemPanel
										.add(materialDescriptionOfComponent);
								componentItemPanel.add(buttonPanel);
								RootPanel.get("rightside").clear();
								RootPanel.get("rightside").add(
										componentItemPanel);
								deleteComponentButton
										.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												deleteComponent(Integer
														.parseInt(idOfComponentString));

											}
										});
								editComponentButton
										.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												if (nameOfComponent.getValue()
														.isEmpty()
														|| descriptionOfComponent
																.getValue()
																.isEmpty()
														|| materialDescriptionOfComponent
																.getValue()
																.isEmpty()) {
													Window.alert("Bitte Felder befüllen!");
												} else {
													Component c = new Component();
													c.setId(Integer
															.parseInt(idOfComponentString));
													c.setName(nameOfComponent
															.getText());
													c.setMaterialDescription(materialDescriptionOfComponent
															.getText());
													c.setDescription(descriptionOfComponent
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