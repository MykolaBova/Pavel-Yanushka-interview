package org.pavel.yanushka.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface ApplicationResources extends ClientBundle {
    ApplicationResources INSTANCE = GWT.create(ApplicationResources.class);

    @Source("org/pavel/yanushka/client/resource/GwtWebAppStyles.css")
    GwtWebAppStyles style();

    @Source("add.png")
    @ImageOptions(repeatStyle = RepeatStyle.Both)
    ImageResource addIcon();

    @Source("loading.gif")
    @ImageOptions(repeatStyle = RepeatStyle.Both)
    ImageResource loading();
}
