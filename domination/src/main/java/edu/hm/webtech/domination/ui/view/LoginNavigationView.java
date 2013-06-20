package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Panel;

import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.session.SessionManager;

/**
 * This {@link NavigationView} provides a user interface for login.
 *
 * @author Marco Wolff, Daniel Brielbeck
 */
@SuppressWarnings("serial")
public class LoginNavigationView extends AbstractNavigationView {

    private MainTabView mainTabView;

    /**
     * Ctor, intializing this {@link NavigationView} with the given caption.
     *
     * @param caption caption of the {@link NavigationView}.
     */
    public LoginNavigationView(String caption) {
        super(caption);
        init();
    }

    @Override
    protected Component initializeComponent() {
        //VerticalComponentGroup vertCompGroup = new VerticalComponentGroup();

		/*Label welcomeLabel = new Label(
                "<h1 align=\"center\">Welcome! Please enter your username!</h1>",
				Label.CONTENT_XHTML);*/
        Label welcomeLabel = new Label(
                "<h1 align=\"center\">Welcome! Please choose a character!</h1>",
                Label.CONTENT_XHTML);

        VerticalComponentGroup componentGroup = new VerticalComponentGroup("Character");
        componentGroup.addComponent(welcomeLabel);
        //this.mainTabView = new MainTabView();

        
        // Create the form
        LoginForm login = new LoginForm();
        login.setSizeFull();
        componentGroup.addComponent(login);

        // Handle form submits
        login.addListener((SessionManager) MyVaadinApplication.getSm());
				
		
		/*Label usernameLabel = new Label("username:");
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
		vertCompGroup.addComponent(loginButton);*/

        return componentGroup;
    }

    private ClickListener getClickListener() {
        return new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                getWindow().setContent(mainTabView);
            }
        };
    }
}
