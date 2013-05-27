package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

/**
 * This {@link NavigationView} provides a user interface for login.
 * 
 * @author Marco Wolff
 * 
 */
@SuppressWarnings("serial")
public class LoginNavigationView extends AbstractNavigationView {

	/**
	 * Ctor, intializing this {@link NavigationView} with the given caption.
	 * 
	 * @param caption
	 *            caption of the {@link NavigationView}.
	 */
	public LoginNavigationView(String caption) {
		super(caption);
	}

	@Override
	protected Component initializeComponent() {
		VerticalComponentGroup vertCompGroup = new VerticalComponentGroup();

		Label welcomeLabel = new Label(
				"<h1 align=\"center\">welcome! please enter your username!</h1>",
				Label.CONTENT_XHTML);

		Label usernameLabel = new Label("username:");
		TextField usernameTextField = new TextField();
		Button loginButton = new Button("login");
		loginButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO IMPLEMENT ME, BIATCH!
			}
		});

		vertCompGroup.addComponent(welcomeLabel);
		vertCompGroup.addComponent(usernameLabel);
		vertCompGroup.addComponent(usernameTextField);
		vertCompGroup.addComponent(loginButton);

		return vertCompGroup;
	}

}
