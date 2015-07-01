package de.hdm.sms.client.gui;

import de.hdm.sms.client.CreateComponent;
import de.hdm.sms.client.CreateComponentGroup;
import de.hdm.sms.client.CreateProduct;
import de.hdm.sms.client.CreateStocklist;
import de.hdm.sms.client.CreateUser;

import de.hdm.sms.client.EditComponent;
import de.hdm.sms.client.EditComponentGroup;
import de.hdm.sms.client.EditProduct;
import de.hdm.sms.client.EditUser;
import de.hdm.sms.shared.LoginInfo;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class Startside extends VerticalPanel {

	private VerticalPanel menuPanel = new VerticalPanel();

	private LoginInfo loginInfo;

	public void StartSide() {

	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public void onLoad() {

		// Create a menu bar
		MenuBar menu = new MenuBar(true);
		menu.setAutoOpen(true);
		menu.setWidth("150px");
		menu.setAnimationEnabled(true);

		// Create the file menu
		MenuBar userMenu = new MenuBar(true);
		MenuBar componentMenu = new MenuBar(true);
		MenuBar componentGroupMenu = new MenuBar(true);
		MenuBar productMenu = new MenuBar(true);
		MenuBar stocklistMenu = new MenuBar(true);
		
		userMenu.addItem("Create", new Command() {
			@Override
			public void execute() {

				RootPanel.get("rightside").clear();
				CreateUser cR = new CreateUser();
				cR.setLoginInfo(loginInfo);
				RootPanel.get("rightside").add(cR);
			}
		});
		userMenu.addItem("Edit", new Command() {
			@Override
			public void execute() {

				RootPanel.get("rightside").clear();
				RootPanel.get("rightside").add(new EditUser());
			}
		});
		componentMenu.addItem("Create", new Command() {
			@Override
			public void execute() {

				RootPanel.get("rightside").clear();
				CreateComponent cc = new CreateComponent();
				cc.setLoginInfo(loginInfo);
				RootPanel.get("rightside").add(cc);
			}
		});
		componentMenu.addItem("Edit", new Command() {
			@Override
			public void execute() {

				RootPanel.get("rightside").clear();
				EditComponent ec = new EditComponent();
				ec.setLoginInfo(loginInfo);
				RootPanel.get("rightside").add(ec);
			}
		});

		componentGroupMenu.addItem("Create", new Command() {
			@Override
			public void execute() {

				RootPanel.get("rightside").clear();
				CreateComponentGroup ccG = new CreateComponentGroup();
				ccG.setLoginInfo(loginInfo);
				RootPanel.get("rightside").add(ccG);
			}
		});

		componentGroupMenu.addItem("Edit", new Command() {
			@Override
			public void execute() {

				RootPanel.get("rightside").clear();
				EditComponentGroup ECG = new EditComponentGroup();
				ECG.setLoginInfo(loginInfo);
				RootPanel.get("rightside").add(ECG);
			}
		});

		productMenu.addItem("Create", new Command() {

			@Override
			public void execute() {
				RootPanel.get("rightside").clear();
				CreateProduct cP = new CreateProduct();
				cP.setLoginInfo(loginInfo);
				RootPanel.get("rightside").add(cP);

			}
		});

		productMenu.addItem("Edit", new Command() {

			@Override
			public void execute() {
				RootPanel.get("rightside").clear();
				EditProduct eP = new EditProduct();
				eP.setLoginInfo(loginInfo);
				RootPanel.get("rightside").add(eP);

			}
		});
		stocklistMenu.addItem("Create", new Command() {

			@Override
			public void execute() {
				RootPanel.get("rightside").clear();
				CreateStocklist cS = new CreateStocklist();
				cS.setLoginInfo(loginInfo);
				RootPanel.get("rightside").add(cS);

			}
		});
		menu.addItem(new MenuItem("User", userMenu));
		menu.addItem(new MenuItem("Component", componentMenu));
		menu.addItem(new MenuItem("Componentgroup", componentGroupMenu));
		menu.addItem(new MenuItem("Product", productMenu));
		menu.addItem(new MenuItem("Stocklist", stocklistMenu));

		menuPanel.add(menu);
		RootPanel.get("leftside").add(menuPanel);
	}
}
