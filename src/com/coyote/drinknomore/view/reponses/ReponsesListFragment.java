/**************************************************************************
 * ReponsesListFragment.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 26, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.reponses;

import java.util.ArrayList;

import com.coyote.drinknomore.criterias.base.CriteriaExpression;
import com.coyote.drinknomore.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.coyote.drinknomore.provider.ReponsesProviderAdapter;
import com.coyote.drinknomore.provider.contract.ReponsesContract;
import com.coyote.drinknomore.harmony.view.HarmonyListFragment;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.coyote.drinknomore.R;
import com.coyote.drinknomore.entity.Reponses;

/** Reponses list fragment.
 *
 * This fragment gives you an interface to list all your Reponsess.
 *
 * @see android.app.Fragment
 */
public class ReponsesListFragment
        extends HarmonyListFragment<Reponses>
        implements CrudCreateMenuInterface {

    /** The adapter which handles list population. */
    protected ReponsesListAdapter mAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        final View view =
                inflater.inflate(R.layout.fragment_reponses_list,
                        null);

        this.initializeHackCustomList(view,
                R.id.reponsesProgressLayout,
                R.id.reponsesListContainer);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
        this.setEmptyText(
                getString(
                        R.string.reponses_empty_list));

        // Create an empty adapter we will use to display the loaded data.
        ((PinnedHeaderListView) this.getListView())
                    .setPinnedHeaderEnabled(false);
        this.mAdapter = new ReponsesListAdapter(this.getActivity());

        // Start out with a progress indicator.
        this.setListShown(false);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        /* Do click action inside your fragment here. */
    }

    @Override
    public Loader<android.database.Cursor> onCreateLoader(int id, Bundle bundle) {
        Loader<android.database.Cursor> result = null;
        CriteriaExpression crit = null;
        if (bundle != null) {
            crit = (CriteriaExpression) bundle.get(
                        CriteriaExpression.PARCELABLE);
        }

        if (crit != null) {
            result = new ReponsesListLoader(this.getActivity(),
                ReponsesProviderAdapter.REPONSES_URI,
                ReponsesContract.ALIASED_COLS,
                crit,
                null);
        } else {
            result = new ReponsesListLoader(this.getActivity(),
                ReponsesProviderAdapter.REPONSES_URI,
                ReponsesContract.ALIASED_COLS,
                null,
                null,
                null);
        }
        return result;
    }

    @Override
    public void onLoadFinished(
            Loader<android.database.Cursor> loader,
            android.database.Cursor data) {

        // Set the new data in the adapter.
        data.setNotificationUri(this.getActivity().getContentResolver(),
                ReponsesProviderAdapter.REPONSES_URI);

        ArrayList<Reponses> users = ReponsesContract.cursorToItems(data);
        this.mAdapter.setNotifyOnChange(false);
        this.mAdapter.setData(
                new ReponsesListAdapter
                    .ReponsesSectionIndexer(users));
        this.mAdapter.setNotifyOnChange(true);
        this.mAdapter.notifyDataSetChanged();
        this.mAdapter.setPinnedPartitionHeadersEnabled(false);
        this.mAdapter.setSectionHeaderDisplayEnabled(false);

        if (this.getListAdapter() == null) {
            this.setListAdapter(this.mAdapter);
        }

        // The list should now be shown.
        if (this.isResumed()) {
            this.setListShown(true);
        } else {
            this.setListShownNoAnimation(true);
        }

        super.onLoadFinished(loader, data);
    }

    @Override
    public void onLoaderReset(Loader<android.database.Cursor> loader) {
        // Clear the data in the adapter.
        this.mAdapter.clear();
    }
    @Override
    public void onClickAdd() {
        Intent intent = new Intent(this.getActivity(),
                    ReponsesCreateActivity.class);
        this.startActivity(intent);
    }

}
