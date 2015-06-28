package de.hdm.sms.client.gui;

import java.sql.Timestamp;
import java.text.Format;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdm.sms.client.CreateComponent;
import de.hdm.sms.client.CreateComponentGroup;
import de.hdm.sms.client.CreateComponentGroup_depr;
import de.hdm.sms.client.CreateProduct;
import de.hdm.sms.client.CreateUser;
import de.hdm.sms.client.DeleteComponent;
import de.hdm.sms.client.EditComponent;
import de.hdm.sms.client.EditComponentGroup;
import de.hdm.sms.client.EditProduct;
import de.hdm.sms.client.EditUser;
import de.hdm.sms.shared.LoginInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;

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
		userMenu.setAnimationEnabled(false);

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
				RootPanel.get("rightside").add(new CreateComponentGroup());
			}
		});
		componentGroupMenu.addItem("Create_depr", new Command() {
			@Override
			public void execute() {

				RootPanel.get("rightside").clear();
				RootPanel.get("rightside").add(new CreateComponentGroup_depr());
			}
		});
		componentGroupMenu.addItem("Edit", new Command() {
			@Override
			public void execute() {

				RootPanel.get("rightside").clear();
				RootPanel.get("rightside").add(new EditComponentGroup());
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
		menu.addItem(new MenuItem("User", userMenu));
		menu.addItem(new MenuItem("Component", componentMenu));
		menu.addItem(new MenuItem("Componentgroup", componentGroupMenu));
		menu.addItem(new MenuItem("Product", productMenu));

		menuPanel.add(menu);
		RootPanel.get("leftside").add(menuPanel);
	}
}
