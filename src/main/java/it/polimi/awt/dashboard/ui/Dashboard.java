package it.polimi.awt.dashboard.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import it.polimi.awt.dashboard.ui.login.SimpleLoginMainView;
import it.polimi.awt.dashboard.ui.login.SimpleLoginView;
import org.springframework.stereotype.Controller;

@Controller
@Title("Dashboard MPCS")
@Theme("chameleon")
public class Dashboard extends UI {

    @Override
    protected void init(VaadinRequest request) {

        new Navigator(this, this);

        getNavigator().addView(SimpleLoginView.NAME, SimpleLoginView.class);//
        getNavigator().addView(SimpleLoginMainView.NAME, SimpleLoginMainView.class);
        getNavigator().addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {

                // Check if a user has logged in
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof SimpleLoginView;

                if (!isLoggedIn && !isLoginView) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                    getNavigator().navigateTo(SimpleLoginView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    return false;
                }

                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {

            }
        });
    }
}