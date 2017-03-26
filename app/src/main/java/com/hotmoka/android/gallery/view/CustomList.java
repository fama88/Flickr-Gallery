package com.hotmoka.android.gallery.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotmoka.android.gallery.MVC;
import com.hotmoka.android.gallery.R;
import com.hotmoka.android.gallery.controller.Controller;

/**
 * Created by refax on 25/03/2017.
 */

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] titles;
    private final Bitmap[] imageBitmap;


    public CustomList(Activity context,
                      String[] titles, Bitmap[] imageBitmap) {
        super(context, R.layout.table_row_layout, titles);
        this.context = context;
        this.titles = titles;
        this.imageBitmap = imageBitmap;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.table_row_layout, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(titles[position]);

        Bitmap bitmap = MVC.model.getBitmap(position);

        if(bitmap == null) {
            String url = MVC.model.getUrl(position);
            new ImageDownloaderTask(imageView).execute(url);
        }
        else
        {
            imageView.setImageBitmap(bitmap);
        }



        return rowView;
    }

}
