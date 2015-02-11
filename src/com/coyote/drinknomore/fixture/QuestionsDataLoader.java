/**************************************************************************
 * QuestionsDataLoader.java, drinknomore Android
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





import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;


/**
 * QuestionsDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class QuestionsDataLoader
extends FixtureBase<Questions> {
	/** QuestionsDataLoader name. */
	private static final String FILE_NAME = "Questions";

	/** Constant field for id. */
	private static final String ID = "id";
	/** Constant field for enigme. */
	private static final String ENIGME = "enigme";
	/** Constant field for arguments. */
	private static final String ARGUMENTS = "arguments";
	/** Constant field for reponse. */
	private static final String REPONSE = "reponse";


	/** QuestionsDataLoader instance (Singleton). */
	private static QuestionsDataLoader instance;

	/**
	 * Get the QuestionsDataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
	 */
	public static QuestionsDataLoader getInstance(
			final android.content.Context ctx) {
		if (instance == null) {
			instance = new QuestionsDataLoader(ctx);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private QuestionsDataLoader(final android.content.Context ctx) {
		super(ctx);
	}


	@Override
	protected Questions extractItem(final Map<?, ?> columns) {
		final Questions questions =
				new Questions();

		return this.extractItem(columns, questions);
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param questions Entity to extract
	 * @return A Questions entity
	 */
	protected Questions extractItem(final Map<?, ?> columns,
			Questions questions) {
		questions.setId(this.parseIntField(columns, ID));
		questions.setEnigme(this.parseField(columns, ENIGME, String.class));
		questions.setArguments(this.parseField(columns, ARGUMENTS, String.class));
		questions.setReponse(this.parseMultiRelationField(columns, REPONSE, ReponsesDataLoader.getInstance(this.ctx)));
		if (questions.getReponse() != null) {
			for (Reponses related : questions.getReponse()) {
				related.setQuestion(questions);
			}
		}

		return questions;
	}
	/**
	 * Loads Questionss into the DataManager.
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager dataManager) {
		for (final Questions questions : this.items.values()) {
			int id = dataManager.persist(questions);
			questions.setId(id);

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
	protected Questions get(final String key) {
		final Questions result;
		if (this.items.containsKey(key)) {
			result = this.items.get(key);
		}
		else {
			result = null;
		}
		return result;
	}
}
