package org.pavel.yanushka.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import org.pavel.yanushka.client.controller.WebAppController;
import org.pavel.yanushka.client.model.ModelHandler;
import org.pavel.yanushka.client.resource.ApplicationResources;
import org.pavel.yanushka.client.resource.Messages;
import org.pavel.yanushka.client.ui.MainPanel;

public class GwtWebAppGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(Messages.class).in(Singleton.class);
        bind(ApplicationResources.class).in(Singleton.class);
        bind(SimpleEventBus.class).in(Singleton.class);
        bind(WebAppController.class).in(Singleton.class);

        bind(ModelHandler.class).in(Singleton.class);
        bind(MainPanel.class).in(Singleton.class);
    }

}
