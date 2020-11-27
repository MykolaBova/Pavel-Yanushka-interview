package org.pavel.yanushka.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.pavel.yanushka.client.controller.WebAppController;
import org.pavel.yanushka.client.model.ModelHandler;
import org.pavel.yanushka.client.resource.ApplicationResources;
import org.pavel.yanushka.client.resource.Messages;
import org.pavel.yanushka.client.ui.MainPanel;

@GinModules(GwtWebAppGinModule.class)
public interface GwtWebAppGinjector extends Ginjector {

    SimpleEventBus getEventBus();

    ApplicationResources getApplicationResources();

    Messages getMessages();

    WebAppController getWebAppController();

    ModelHandler getModelHandler();

    MainPanel getMainPanel();
}
