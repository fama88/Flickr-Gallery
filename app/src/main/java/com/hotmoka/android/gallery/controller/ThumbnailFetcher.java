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

public class ThumbnailFetcher {
    private final static String TAG = ThumbnailFetcher.class.getSimpleName();

    @WorkerThread
    ThumbnailFetcher(String url) {
        Bitmap bitmap = null;

        try {
            byte[] bitmapBytes = getUrlBytes(url);
            bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
        }
        catch (IOException e) {
            Log.e(TAG, "Error downloading image", e);
        }
        finally {
            MVC.controller.taskFinished();
        }

        if (bitmap != null)
            MVC.model.setThumbnail(url, bitmap);
    }

    private byte[] getUrlBytes(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        ByteArrayOutputStream out = null;

        try {
            out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                return new byte[0];

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0)
                out.write(buffer, 0, bytesRead);

            return out.toByteArray();
        }
        finally {
            if (out != null)
                out.close();

            connection.disconnect();
        }
    }
}
