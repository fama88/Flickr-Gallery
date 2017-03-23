package com.hotmoka.android.gallery.view;

import android.support.annotation.UiThread;

import com.hotmoka.android.gallery.model.Pictures;

public interface GalleryFragment {

    @UiThread
    void onModelChanged(Pictures.Event event);

    @UiThread
    void startShareActivity(int position);
}
