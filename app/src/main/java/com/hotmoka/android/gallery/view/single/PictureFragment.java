package com.hotmoka.android.gallery.view.single;

import android.support.annotation.UiThread;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.hotmoka.android.gallery.MVC;
import com.hotmoka.android.gallery.R;
import com.hotmoka.android.gallery.model.Pictures;
import com.hotmoka.android.gallery.view.GalleryActivity;

/**
 * The picture fragment for a single pane layout.
 * It adds to ability to create the fragment programmatically
 * and redefines the behavior at picture show up by also
 * reporting the title of the picture below it.
 */
public class PictureFragment extends com.hotmoka.android.gallery.view.PictureFragment {
    private ShareActionProvider mShareActionProvider;

    /**
     * Convenience method to create a fragment that shows the picture
     * for the title corresponding to the given position.
     *
     * @param position
     * @return the fragment that has been created
     */
    @UiThread
    public static PictureFragment mkInstance(int position) {
        PictureFragment fragment = new PictureFragment();
        fragment.init(position);

        return fragment;
    }


    @Override @UiThread
    protected boolean showBitmapIfDownloaded(int position) {
        boolean shown = super.showBitmapIfDownloaded(position);
        // If the picture has been shown, report its title below it
        if (shown)
            ((TextView) getView().findViewById(R.id.picture_title))
                    .setText(MVC.model.getTitles()[position]);

        return shown;
    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //  Log.v("AA",menu.toString());
        inflater.inflate(R.menu.fragment_titles, menu);

        MenuItem item;

        item = menu.findItem(R.id.menu_item_load);
        item.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_share) {
            MVC.controller.onSharedClicked(getArguments().getInt(ARG_POSITION), getActivity());
            return true;
        }

        return false;
    }

}
