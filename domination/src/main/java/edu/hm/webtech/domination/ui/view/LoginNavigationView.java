package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.service.Position;
import com.vaadin.addon.touchkit.service.PositionCallback;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.TextField;

import edu.hm.webtech.domination.MyVaadinApplication;
import edu.hm.webtech.domination.manager.session.SessionManager;
import edu.hm.webtech.domination.util.Logger;

/**
 * This {@link NavigationView} provides a user interface for login.
 *
 * @author Marco Wolff, Daniel Brielbeck
 */
@SuppressWarnings("serial")
public class LoginNavigationView extends AbstractNavigationView implements PositionCallback {

    private MainTabView mainTabView;
    
    private Logger logger;

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
    	
    	this.setWidth(100,UNITS_PERCENTAGE);
    	
    	logger = new Logger(this.getClass().getSimpleName());
    	
		MyVaadinApplication.getApp().getMainWindow().detectCurrentPosition(this);

		VerticalComponentGroup componentGroup = new VerticalComponentGroup("Character");
		
		Resource image = new ThemeResource("images/logo_trans_small.png");
		
        if (image != null) {
            CssLayout imageLayout = new CssLayout();
            imageLayout.setMargin(true, false, false, false);
            Embedded embedded = new Embedded(null, image);
            embedded.setWidth("100%");
            embedded.setHeight(200,UNITS_PIXELS);
            imageLayout.addComponent(embedded);
            componentGroup.addComponent(imageLayout);
        }
		
        Label welcomeLabel = new Label(
                "<h1 align=\"center\">Welcome! Please choose a character name!</h1>",
                Label.CONTENT_XHTML);

        componentGroup.addComponent(welcomeLabel);

        Form form = new Form();
        final Field username = new TextField("Character name");
        Button login = new Button("Login");
        login.setWidth(100, UNITS_PIXELS);
        form.addField(username,username);
        form.addField(login, login);
        componentGroup.addComponent(form);
        componentGroup.setWidth(100, UNITS_PERCENTAGE);
        login.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(!username.getValue().equals("")){
					if(MyVaadinApplication.getSm().createAndRegisterPlayer((String)username.getValue())){
	    				logger.errorLog("Create User "+(String)username.getValue()+" Successful");
	    				MyVaadinApplication.getApp().getMainWindow().setContent(new LobbyView("Game Lobby"));
	    			}
	    			else{
	    				getWindow().showNotification("Error", "Create User "+(String)username.getValue()+" failed! Already in use!", 2);
	    				logger.errorLog("Create User "+(String)username.getValue()+" failed! Already in use!");
	    			}
				} else {
					getWindow().showNotification("Error", "Please enter a username!", 2);
    				logger.errorLog("No username entered!");
				}
			}
		});
    
        		
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
    
    @Override
	public void onSuccess(Position position) {
    	logger.errorLog("Success! GPS tracking activated!");
    }

    @Override
    public void onFailure(int errorCode) {
    	getWindow().showNotification("Error", "Allow location tracking to play domination!", 2);
    	logger.errorLog("FAIL! GPS tracking deactivated!");
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
