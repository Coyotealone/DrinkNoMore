/**************************************************************************
 * QuestionsListFragment.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 26, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.questions;

import java.util.ArrayList;

import com.coyote.drinknomore.criterias.base.CriteriaExpression;
import com.coyote.drinknomore.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.coyote.drinknomore.provider.QuestionsProviderAdapter;
import com.coyote.drinknomore.provider.contract.QuestionsContract;
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
import com.coyote.drinknomore.entity.Questions;

/** Questions list fragment.
 *
 * This fragment gives you an interface to list all your Questionss.
 *
 * @see android.app.Fragment
 */
public class QuestionsListFragment
        extends HarmonyListFragment<Questions>
        implements CrudCreateMenuInterface {

    /** The adapter which handles list population. */
    protected QuestionsListAdapter mAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        final View view =
                inflater.inflate(R.layout.fragment_questions_list,
                        null);

        this.initializeHackCustomList(view,
                R.id.questionsProgressLayout,
                R.id.questionsListContainer);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
        this.setEmptyText(
                getString(
                        R.string.questions_empty_list));

        // Create an empty adapter we will use to display the loaded data.
        ((PinnedHeaderListView) this.getListView())
                    .setPinnedHeaderEnabled(false);
        this.mAdapter = new QuestionsListAdapter(this.getActivity());

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
            result = new QuestionsListLoader(this.getActivity(),
                QuestionsProviderAdapter.QUESTIONS_URI,
                QuestionsContract.ALIASED_COLS,
                crit,
                null);
        } else {
            result = new QuestionsListLoader(this.getActivity(),
                QuestionsProviderAdapter.QUESTIONS_URI,
                QuestionsContract.ALIASED_COLS,
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
                QuestionsProviderAdapter.QUESTIONS_URI);

        ArrayList<Questions> users = QuestionsContract.cursorToItems(data);
        this.mAdapter.setNotifyOnChange(false);
        this.mAdapter.setData(
                new QuestionsListAdapter
                    .QuestionsSectionIndexer(users));
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
                    QuestionsCreateActivity.class);
        this.startActivity(intent);
    }

}
