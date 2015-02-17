/**************************************************************************
 * StatistiquesDataLoader.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.fixture;

import java.util.Map;





import com.coyote.drinknomore.entity.Statistiques;


/**
 * StatistiquesDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class StatistiquesDataLoader
                        extends FixtureBase<Statistiques> {
    /** StatistiquesDataLoader name. */
    private static final String FILE_NAME = "Statistiques";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for date. */
    private static final String DATE = "date";
    /** Constant field for nberreurs. */
    private static final String NBERREURS = "nberreurs";


    /** StatistiquesDataLoader instance (Singleton). */
    private static StatistiquesDataLoader instance;

    /**
     * Get the StatistiquesDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static StatistiquesDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new StatistiquesDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private StatistiquesDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Statistiques extractItem(final Map<?, ?> columns) {
        final Statistiques statistiques =
                new Statistiques();

        return this.extractItem(columns, statistiques);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param statistiques Entity to extract
     * @return A Statistiques entity
     */
    protected Statistiques extractItem(final Map<?, ?> columns,
                Statistiques statistiques) {
        statistiques.setId(this.parseIntField(columns, ID));
        statistiques.setDate(this.parseDateTimeField(columns, DATE));
        statistiques.setNberreurs(this.parseIntField(columns, NBERREURS));

        return statistiques;
    }
    /**
     * Loads Statistiquess into the DataManager.
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Statistiques statistiques : this.items.values()) {
            int id = dataManager.persist(statistiques);
            statistiques.setId(id);

        }
        dataManager.flush();
    }

    /**
     * Give priority for fixtures insertion in database.
     * 0 is the first.
     * @return The order
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * Get the fixture file name.
     * @return A String representing the file name
     */
    @Override
    public String getFixtureFileName() {
        return FILE_NAME;
    }

    @Override
    protected Statistiques get(final String key) {
        final Statistiques result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
