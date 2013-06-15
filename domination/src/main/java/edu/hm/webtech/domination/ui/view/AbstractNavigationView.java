package edu.hm.webtech.domination.ui.view;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * Abstract class for {@link NavigationView}s using a {@link CssLayout} as base
 * for the visual components.
 * 
 * @author Marco Wolff
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractNavigationView extends NavigationView {

	/**
	 * Constructor, initializing this {@link NavigationView}.
     * aufruf von init() bitte in der subclass!! zu wenig Kontrolle sonst. Probleme mit initialisierung der Manager.
	 * 
	 * @param caption
	 *            caption of the {@link NavigationView}.
	 */
	public AbstractNavigationView(String caption) {
		super(caption);
	}

	/**
	 * Initializes the {@link NavigationView} setting a {@link CssLayout} as
	 * content for its components.
	 */
	protected void init() {
		CssLayout content = new CssLayout();
		setContent(content);

		Component component = initializeComponent();

		content.addComponent(component);
	}

	/**
	 * Initializes the {@link Component}s for this {@link NavigationView}.
	 * 
	 * @return {@link Component} containing all visual content.
	 */
	protected abstract Component initializeComponent();
}
