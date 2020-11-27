package org.pavel.yanushka.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import org.pavel.yanushka.client.controller.WebAppController;
import org.pavel.yanushka.client.resource.ApplicationResources;
import org.pavel.yanushka.client.ui.MainPanel;

public class GwtWebApp implements EntryPoint {

    private final GwtWebAppGinjector injector = GWT.create(GwtWebAppGinjector.class);

    public void onModuleLoad() {
        ApplicationResources.INSTANCE.style().ensureInjected();
        WebAppController controller = injector.getWebAppController();
        controller.bindHandlers();
        MainPanel mainPanel = injector.getMainPanel();
        RootLayoutPanel.get().add(mainPanel);
    }
}
