/**************************************************************************
 * ReponsesListAdapter.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 26, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.reponses;

import java.util.List;

import com.coyote.drinknomore.R;


import android.util.AttributeSet;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;
import com.google.android.pinnedheader.SelectionItemView;
import com.google.android.pinnedheader.headerlist.HeaderAdapter;
import com.google.android.pinnedheader.headerlist.HeaderSectionIndexer;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView.PinnedHeaderAdapter;
import com.coyote.drinknomore.entity.Reponses;

/**
 * List adapter for Reponses entity.
 */
public class ReponsesListAdapter
        extends HeaderAdapter<Reponses>
        implements PinnedHeaderAdapter {
    /**
     * Constructor.
     * @param ctx context
     */
    public ReponsesListAdapter(android.content.Context ctx) {
        super(ctx);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public ReponsesListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId,
            List<Reponses> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Constructor.
     *
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public ReponsesListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId,
            Reponses[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     */
    public ReponsesListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public ReponsesListAdapter(android.content.Context context,
            int textViewResourceId,
            List<Reponses> objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public ReponsesListAdapter(android.content.Context context,
            int textViewResourceId,
            Reponses[] objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     */
    public ReponsesListAdapter(android.content.Context context,
            int textViewResourceId) {
        super(context, textViewResourceId);
    }

    /** Holder row. */
    private static class ViewHolder extends SelectionItemView {

        /**
         * Constructor.
         *
         * @param context The context
         */
        public ViewHolder(android.content.Context context) {
            this(context, null);
        }
        
        /**
         * Constructor.
         *
         * @param context The context
         * @param attrs The attribute set
         */
        public ViewHolder(android.content.Context context, AttributeSet attrs) {
            super(context, attrs, R.layout.row_reponses);
        }

        /** Populate row with a Reponses.
         *
         * @param model Reponses data
         */
        public void populate(final Reponses model) {
            View convertView = this.getInnerLayout();
            TextView solutionView =
                (TextView) convertView.findViewById(
                        R.id.row_reponses_solution);
            TextView argumentsView =
                (TextView) convertView.findViewById(
                        R.id.row_reponses_arguments);
            TextView questionView =
                (TextView) convertView.findViewById(
                        R.id.row_reponses_question);


            if (model.getSolution() != null) {
                solutionView.setText(model.getSolution());
            }
            if (model.getArguments() != null) {
                argumentsView.setText(model.getArguments());
            }
            if (model.getQuestion() != null) {
                questionView.setText(
                        String.valueOf(model.getQuestion().getId()));
            }
        }
    }

    /** Section indexer for this entity's list. */
    public static class ReponsesSectionIndexer
                    extends HeaderSectionIndexer<Reponses>
                    implements SectionIndexer {

        /**
         * Constructor.
         * @param items The items of the indexer
         */
        public ReponsesSectionIndexer(List<Reponses> items) {
            super(items);
        }
        
        @Override
        protected String getHeaderText(Reponses item) {
            return "Your entity's header name here";
        }
    }

    @Override
    protected View bindView(View itemView,
                int partition,
                Reponses item,
                int position) {
        final ViewHolder view;
        
        if (itemView != null) {
            view = (ViewHolder) itemView;
        } else {
            view = new ViewHolder(this.getContext());
        }

        if (!((HarmonyFragmentActivity) this.getContext()).isDualMode()) {
            view.setActivatedStateSupported(false);
        }
        
        view.populate(item);
        this.bindSectionHeaderAndDivider(view, position);
        
        return view;
    }

    @Override
    public int getPosition(Reponses item) {
        int result = -1;
        if (item != null) {
            for (int i = 0; i < this.getCount(); i++) {
                if (item.getId() == this.getItem(i).getId()) {
                    result = i;
                }
            }
        }
        return result;
    }
}
