package com.hotmoka.android.gallery.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.hotmoka.android.gallery.MVC;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by refax on 28/03/2017.
 */

public class ThumbnailFetcher extends BitmapFetcher{

    protected void SetBitmapOnModel(String url, Bitmap bitmap)
    {
        if (bitmap != null && url != null)
            MVC.model.setThumbnail(url, bitmap);
    }

    @WorkerThread
    ThumbnailFetcher(String url) {
        super(url);
    }
}
