/**************************************************************************
 * ReponsesDataLoader.java, drinknomore Android
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





import com.coyote.drinknomore.entity.Reponses;


/**
 * ReponsesDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ReponsesDataLoader
                        extends FixtureBase<Reponses> {
    /** ReponsesDataLoader name. */
    private static final String FILE_NAME = "Reponses";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for solution. */
    private static final String SOLUTION = "solution";
    /** Constant field for arguments. */
    private static final String ARGUMENTS = "arguments";
    /** Constant field for question. */
    private static final String QUESTION = "question";


    /** ReponsesDataLoader instance (Singleton). */
    private static ReponsesDataLoader instance;

    /**
     * Get the ReponsesDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static ReponsesDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new ReponsesDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private ReponsesDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Reponses extractItem(final Map<?, ?> columns) {
        final Reponses reponses =
                new Reponses();

        return this.extractItem(columns, reponses);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param reponses Entity to extract
     * @return A Reponses entity
     */
    protected Reponses extractItem(final Map<?, ?> columns,
                Reponses reponses) {
        reponses.setId(this.parseIntField(columns, ID));
        reponses.setSolution(this.parseField(columns, SOLUTION, String.class));
        reponses.setArguments(this.parseField(columns, ARGUMENTS, String.class));
        reponses.setQuestion(this.parseSimpleRelationField(columns, QUESTION, QuestionsDataLoader.getInstance(this.ctx)));
        if (reponses.getQuestion() != null) {
            reponses.getQuestion().getReponse().add(reponses);
        }

        return reponses;
    }
    /**
     * Loads Reponsess into the DataManager.
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Reponses reponses : this.items.values()) {
            int id = dataManager.persist(reponses);
            reponses.setId(id);

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
    protected Reponses get(final String key) {
        final Reponses result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
