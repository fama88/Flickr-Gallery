package com.hotmoka.android.gallery.view.single;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hotmoka.android.gallery.R;

/**
 * The titles fragment for a single pane layout.
 */
public class TitlesFragment extends com.hotmoka.android.gallery.view.TitlesFragment {

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.menu_item_share);
        item.setVisible(false);
    }
}
