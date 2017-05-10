package com.hotmoka.android.gallery.view.two;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.hotmoka.android.gallery.MVC;
import com.hotmoka.android.gallery.R;
import com.hotmoka.android.gallery.view.GalleryActivity;

/**
 * The titles fragment for a two panes layout. It modifies the standard
 * behavior by making the selected item remain highlighted.
 */
public class TitlesFragment extends com.hotmoka.android.gallery.view.TitlesFragment {
    private static boolean shareVisibility = false;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Make the clicked item remain visually highlighted
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Keep the selected item checked also after click
        getListView().setItemChecked(position, true);
    }

    public void setShareButtonVisibility(boolean visible) {
        shareVisibility = visible;
        ((GalleryActivity) getActivity()).invalidateOptionsMenu();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu_item_share).setVisible(shareVisibility);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_share) {
            MVC.controller.onSharedClicked(getListView().getCheckedItemPosition(), getActivity());
            return true;
        }

        return false;
    }
}
