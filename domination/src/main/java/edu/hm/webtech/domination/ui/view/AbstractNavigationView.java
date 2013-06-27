package edu.hm.webtech.domination.ui.view;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import edu.hm.webtech.domination.manager.game.RefreshManager;

/**
 * Abstract class for {@link NavigationView}s using a {@link CssLayout} as base
 * for the visual components.
 * 
 * @author Marco Wolff
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractNavigationView extends NavigationView {

    protected final Refresher refresher;

	/**
	 * Constructor, initializing this {@link NavigationView}.
     * aufruf von init() bitte in der subclass!! zu wenig Kontrolle sonst. Probleme mit initialisierung der Manager.
	 * 
	 * @param caption
	 *            caption of the {@link NavigationView}.
	 */
	public AbstractNavigationView(String caption) {
		super(caption);
        refresher = new RefreshManager().getRefresher();
	}

	/**
	 * Initializes the {@link NavigationView} setting a {@link CssLayout} as
	 * content for its components.
	 */
	protected void init() {
		CssLayout content = new CssLayout();
		setContent(content);

		Component component = initializeComponent();

        content.addComponent(refresher);

		content.addComponent(component);
	}


	/**
	 * Initializes the {@link Component}s for this {@link NavigationView}.
	 * 
	 * @return {@link Component} containing all visual content.
	 */
	protected abstract Component initializeComponent();
}
