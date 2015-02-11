/**************************************************************************
 * DataManager.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.fixture;


import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

import com.coyote.drinknomore.data.base.SQLiteAdapterBase;
import com.coyote.drinknomore.data.ReponsesSQLiteAdapter;
import com.coyote.drinknomore.entity.Reponses;
import com.coyote.drinknomore.data.QuestionsSQLiteAdapter;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.data.StatistiquesSQLiteAdapter;
import com.coyote.drinknomore.entity.Statistiques;

/**
 * DataManager.
 * 
 * This class is an "orm-like" manager which simplifies insertion in database
 * with sqlite adapters.
 */
public class DataManager {
    /** HashMap to join Entity Name and its SQLiteAdapterBase. */
    protected Map<String, SQLiteAdapterBase<?>> adapters =
            new HashMap<String, SQLiteAdapterBase<?>>();
    /** is successfull. */
    protected boolean isSuccessfull = true;
    /** is in internal transaction. */
    protected boolean isInInternalTransaction = false;
    /** database. */
    protected SQLiteDatabase db;
    /** Reponses name constant. */
    private static final String REPONSES = "Reponses";
    /** Questions name constant. */
    private static final String QUESTIONS = "Questions";
    /** Statistiques name constant. */
    private static final String STATISTIQUES = "Statistiques";
    /**
     * Constructor.
     * @param ctx The context
     * @param db The DB to work in
     */
    public DataManager(final android.content.Context ctx, final SQLiteDatabase db) {
        this.db = db;
        this.adapters.put(REPONSES,
                new ReponsesSQLiteAdapter(ctx));
        this.adapters.get(REPONSES).open(this.db);
        this.adapters.put(QUESTIONS,
                new QuestionsSQLiteAdapter(ctx));
        this.adapters.get(QUESTIONS).open(this.db);
        this.adapters.put(STATISTIQUES,
                new StatistiquesSQLiteAdapter(ctx));
        this.adapters.get(STATISTIQUES).open(this.db);
    }

    /**
     * Tells the ObjectManager to make an instance managed and persistent.
     *
     * The object will be entered into the database as a result of the <br />
     * flush operation.
     *
     * NOTE: The persist operation always considers objects that are not<br />
     * yet known to this ObjectManager as NEW. Do not pass detached <br />
     * objects to the persist operation.
     *
     * @param object $object The instance to make managed and persistent.
     * @return Count of objects entered into the DB
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int persist(final Object object) {
        int result;

        this.beginTransaction();
        try {
            final SQLiteAdapterBase adapter = this.getRepository(object);

            result = (int) adapter.insert(object);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.isSuccessfull = false;
            result = 0;
        }

        return result;
    }

    /**
     * Removes an object instance.
     *
     * A removed object will be removed from the database as a result of <br />
     * the flush operation.
     *
     * @param object $object The object instance to remove.
     */
    public void remove(final Object object) {
        this.beginTransaction();
        try {
            if (object instanceof Reponses) {
                ((ReponsesSQLiteAdapter)
                        this.adapters.get(REPONSES))
                            .remove(((Reponses) object).getId());
            }
            if (object instanceof Questions) {
                ((QuestionsSQLiteAdapter)
                        this.adapters.get(QUESTIONS))
                            .remove(((Questions) object).getId());
            }
            if (object instanceof Statistiques) {
                ((StatistiquesSQLiteAdapter)
                        this.adapters.get(STATISTIQUES))
                            .remove(((Statistiques) object).getId());
            }
        } catch (Exception ex) {
            this.isSuccessfull = false;
        }
    }

//    /**
//     * Merges the state of a detached object into the persistence context
//     * of this ObjectManager and returns the managed copy of the object.
//     * The object passed to merge will not become associated/managed with
//       * this ObjectManager.
//     *
//     * @param object $object
//     */
//    public void merge(Object object) {
//
//    }
//
//    /**
//     * Clears the ObjectManager. All objects that are currently managed
//     * by this ObjectManager become detached.
//     *
//     * @param objectName $objectName if given, only objects of this type will
//     * get detached
//     */
//    public void clear(String objectName) {
//
//    }
//
//    /**
//     * Detaches an object from the ObjectManager, causing a managed object to
//     * become detached. Unflushed changes made to the object if any
//     * (including removal of the object), will not be synchronized to the
//     * database.
//     * Objects which previously referenced the detached object will continue
//     * to reference it.
//     *
//     * @param object $object The object to detach.
//     */
//    public void detach(Object object) {
//
//    }
//
//    /**
//     * Refreshes the persistent state of an object from the database,
//     * overriding any local changes that have not yet been persisted.
//     *
//     * @param object $object The object to refresh.
//     */
//    public void refresh(Object object) {
//
//    }

    /**
     * Flushes all changes to objects that have been queued up to now to <br />
     * the database. This effectively synchronizes the in-memory state of<br />
     * managed objects with the database.
     */
    public void flush() {
        if (this.isInInternalTransaction) {
            if (this.isSuccessfull) {
                this.db.setTransactionSuccessful();
            }
            this.db.endTransaction();
            this.isInInternalTransaction = false;
        }
    }

    /**
     * Gets the repository for a class.
     *
     * @param className $className
     * @return \Doctrine\Common\Persistence\ObjectRepository
     */
    public SQLiteAdapterBase<?> getRepository(final String className) {
        return this.adapters.get(className);
    }


    /**
     * Gets the repository for a given object.
     *
     * @param o object
     * @return \Doctrine\Common\Persistence\ObjectRepository
     */
    private SQLiteAdapterBase<?> getRepository(final Object o) {
        final String className = o.getClass().getSimpleName();

        return this.getRepository(className);
    }

//    /**
//     * Returns the ClassMetadata descriptor for a class.
//     *
//     * The class name must be the fully-qualified class name without a <br />
//     * leading backslash (as it is returned by get_class($obj)).
//     *
//     * @param className $className
//     * @return \Doctrine\Common\Persistence\Mapping\ClassMetadata
//     */
//    public ClassMetadata getClassMetadata(final String className) {
//        return null;
//    }

    /**
     * Check if the object is part of the current UnitOfWork and therefore
     * managed.
     *
     * @param object $object
     * @return bool
     */
    public boolean contains(final Object object) {
        return false;
    }

    /**
     * Called before any transaction to open the DB.
     */
    private void beginTransaction() {
        // If we are not already in a transaction, begin it
        if (!this.isInInternalTransaction) {
            this.db.beginTransaction();
            this.isSuccessfull = true;
            this.isInInternalTransaction = true;
        }
    }

}
