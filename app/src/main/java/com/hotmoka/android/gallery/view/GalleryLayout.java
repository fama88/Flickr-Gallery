package com.hotmoka.android.gallery.view;

import android.support.annotation.UiThread;

import com.hotmoka.android.gallery.model.Pictures;
import android.content.Intent;
/**
 * The view of the application. It is the screen that
 * interacts with the user.
 */
public interface GalleryLayout {

    /**
     * Shows the picture at the given position in the model.
     *
     * @param position
     */
    @UiThread
    void showPicture(int position);

    /**
     * Notifies that the model has changed. The view
     * will likely redraw itself
     *
     * @param event the kind of change
     */
    @UiThread
    void onModelChanged(Pictures.Event event);
}