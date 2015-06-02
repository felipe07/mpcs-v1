package it.polimi.awt.dashboard.ui.login;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import it.polimi.awt.dashboard.ui.gallery.SimpleImageGallery;

public class SimpleLoginMainView extends CustomComponent implements View {

    public static final String NAME = "";

    VerticalLayout generalLayout = new VerticalLayout();

    SimpleImageGallery simpleGallery = new SimpleImageGallery();

    Label text = new Label();

    Button logout = new Button("Logout", new Button.ClickListener() {

        @Override
        public void buttonClick(Button.ClickEvent event) {

            // "Logout" the user
            getSession().setAttribute("user", null);

            // Refresh this view, should redirect to login view
            getUI().getNavigator().navigateTo(NAME);
        }
    });

    public SimpleLoginMainView() {
        generalLayout.setSizeFull();
        generalLayout.setSpacing(true);
        generalLayout.addComponent(text);
        generalLayout.addComponent(simpleGallery);
        generalLayout.addComponent(logout);
        setCompositionRoot(generalLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // Get the user name from the session
        String username = String.valueOf(getSession().getAttribute("user"));
        // And show the username
        text.setValue("Hello " + username);
    }
}