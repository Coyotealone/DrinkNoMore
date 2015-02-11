/**************************************************************************
 * StatistiquesListAdapter.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.view.statistiques;

import java.util.List;

import com.coyote.drinknomore.R;


import android.util.AttributeSet;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.coyote.drinknomore.harmony.util.DateUtils;
import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;
import com.google.android.pinnedheader.SelectionItemView;
import com.google.android.pinnedheader.headerlist.HeaderAdapter;
import com.google.android.pinnedheader.headerlist.HeaderSectionIndexer;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView.PinnedHeaderAdapter;
import com.coyote.drinknomore.entity.Statistiques;

/**
 * List adapter for Statistiques entity.
 */
public class StatistiquesListAdapter
        extends HeaderAdapter<Statistiques>
        implements PinnedHeaderAdapter {
    /**
     * Constructor.
     * @param ctx context
     */
    public StatistiquesListAdapter(android.content.Context ctx) {
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
    public StatistiquesListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId,
            List<Statistiques> objects) {
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
    public StatistiquesListAdapter(android.content.Context context,
            int resource,
            int textViewResourceId,
            Statistiques[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     */
    public StatistiquesListAdapter(android.content.Context context,
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
    public StatistiquesListAdapter(android.content.Context context,
            int textViewResourceId,
            List<Statistiques> objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public StatistiquesListAdapter(android.content.Context context,
            int textViewResourceId,
            Statistiques[] objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     */
    public StatistiquesListAdapter(android.content.Context context,
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
            super(context, attrs, R.layout.row_statistiques);
        }

        /** Populate row with a Statistiques.
         *
         * @param model Statistiques data
         */
        public void populate(final Statistiques model) {
            View convertView = this.getInnerLayout();
            TextView dateView =
                (TextView) convertView.findViewById(
                        R.id.row_statistiques_date);
            TextView nberreursView =
                (TextView) convertView.findViewById(
                        R.id.row_statistiques_nberreurs);


            if (model.getDate() != null) {
                dateView.setText(DateUtils.formatDateTimeToString(model.getDate()));
            }
            if (model.getNberreurs() != null) {
                nberreursView.setText(String.valueOf(model.getNberreurs()));
            }
        }
    }

    /** Section indexer for this entity's list. */
    public static class StatistiquesSectionIndexer
                    extends HeaderSectionIndexer<Statistiques>
                    implements SectionIndexer {

        /**
         * Constructor.
         * @param items The items of the indexer
         */
        public StatistiquesSectionIndexer(List<Statistiques> items) {
            super(items);
        }
        
        @Override
        protected String getHeaderText(Statistiques item) {
            return "Your entity's header name here";
        }
    }

    @Override
    protected View bindView(View itemView,
                int partition,
                Statistiques item,
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
    public int getPosition(Statistiques item) {
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
