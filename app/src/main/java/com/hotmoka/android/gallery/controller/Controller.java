package com.hotmoka.android.gallery.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.UiThread;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import com.hotmoka.android.gallery.MVC;

import org.apache.http.HttpStatus;

/**
 * The controller reacts to user events and allows the execution
 * of long-running background tasks.
 */
public class Controller {

    /**
     * A counter of the number of background tasks currently running.
     */
    private final AtomicInteger taskCounter = new AtomicInteger();

    /**
     * Takes note that a picture is needed and must be downloaded
     * from the Internet.
     *
     * @param context the context that requires the picture
     * @param url the address where the picture can be found and downloaded
     */
    public void onPictureRequired(Context context, String url) {
        taskCounter.incrementAndGet();
        ControllerService.fetchPicture(context, url);
    }

    /**
     * Takes note that a picture is needed and must be downloaded
     * from the Internet.
     *
     * @param context the context that requires the picture
     * @param url the address where the picture can be found and downloaded
     * @return true if task can be computed, false if is busy
     */
    public boolean onThumbnailRequired(Context context, String url) {
        if(taskCounter.get() < 4 ) {
            taskCounter.incrementAndGet();
            ControllerService.fetchThumbnail(context, url);
            return true;
        }
        return false;
    }


    public void onSharedClicked(int position) {
        MVC.forEachView(view -> view.shareImage(position));
    }
    /**
     * Takes note that the up to date list of titles is needed.
     * It will download it from the Internet, asking Flickr about the
     * latest titles uploaded to their servers.
     *
     * @param context the context that requires the list of titles
     */
    public void onTitlesReloadRequest(Context context) {
        taskCounter.incrementAndGet();
        ControllerService.fetchListOfPictures(context, 40);
    }

    /**
     * Determines some background task is running.
     *
     * @return true if and only if at least a background task is running
     */
    public boolean isIdle() {
        return taskCounter.get() == 0;
    }

    @UiThread
    public void onTitleSelected(int position) {
        MVC.forEachView(view -> view.showPicture(position));
    }

    /**
     * Takes note that a background task has finished.
     */
    void taskFinished() {
        taskCounter.decrementAndGet();
    }

    /**
     * Resets the number of background tasks currently running.
     * This is called in the unlikely case that the OS destroys
     * the companion service, with all queued tasks.
     */
    void resetTaskCounter() {
        taskCounter.set(0);
    };


}