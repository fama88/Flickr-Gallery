package com.hotmoka.android.gallery.controller;

import android.net.Uri;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.hotmoka.android.gallery.MVC;
import com.hotmoka.android.gallery.model.Picture;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An object that fetches the latest titles uploaded
 * into Flickr's servers.
 */
class ListOfPicturesFetcher {
    private final static String TAG = ListOfPicturesFetcher.class.getSimpleName();
    private final static String ENDPOINT = "https://api.flickr.com/services/rest/";
    private final static int MAX_TITLE_LENGTH = 40;

    @WorkerThread
    ListOfPicturesFetcher(int howMany, String APIKey) {
        List<Picture> items = fetchItems(howMany, APIKey);
        MVC.controller.taskFinished();
        MVC.model.setPictures(items);
    }

    private List<Picture> fetchItems(int howMany, String APIKey) {
        try {
            // Build a query to Flickr's webservice
            String url = Uri.parse(ENDPOINT).buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", APIKey)
                    .appendQueryParameter("extras", "url_z, url_t")
                    .appendQueryParameter("per_page", String.valueOf(howMany))
                    .build().toString();

            Log.i(TAG, "Sent query: " + url);
            String xmlString = getFrom(url);
            Log.i(TAG, "Received XML: " + xmlString);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlString));
            return parseItems(parser);
        }
        catch (IOException | XmlPullParserException e) {
            Log.e(TAG, "Failed to fetch items", e);
            return Collections.<Picture> emptyList();
        }
    }

    private String getFrom(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        ByteArrayOutputStream out = null;

        try {
            out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new IOException();

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0)
                out.write(buffer, 0, bytesRead);

            return new String(out.toByteArray());
        }
        finally {
            if (out != null)
                out.close();

            connection.disconnect();
        }
    }

    private List<Picture> parseItems(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Picture> items = new ArrayList<>();

        for (int eventType = parser.next(); eventType != XmlPullParser.END_DOCUMENT; eventType = parser.next())
            if (eventType == XmlPullParser.START_TAG && "photo".equals(parser.getName())) {
                String caption = parser.getAttributeValue(null, "title");
                String url = parser.getAttributeValue(null, "url_z");
                String thumbnailUrl = parser.getAttributeValue(null, "url_t");
                if (caption == null || caption.isEmpty() || url == null)
                    // The picture might be missing or not have this size
                    continue;

                if (caption.length() > MAX_TITLE_LENGTH)
                    caption = caption.substring(0, MAX_TITLE_LENGTH - 3) + "...";

                items.add(new Picture(caption, url, thumbnailUrl));
            }

        return items;
    }
}