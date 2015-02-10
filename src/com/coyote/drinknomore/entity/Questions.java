package com.coyote.drinknomore.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import com.google.common.base.Function;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.JoinColumn;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;

@Entity
public class Questions implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	@Column(type = Type.INT, hidden = true)
	private int id;

	@Column(type = Type.STRING)
	private String enigme;

	@Column(type = Type.STRING)
	private String arguments;

	@OneToMany(mappedBy = "question", targetEntity = "Reponses")
	private ArrayList<Reponses> reponse = new ArrayList<Reponses>();
	

    /**
     * Default constructor.
     */
    public Questions() {

    }

    /**
     * @return the id
     */
    public int getId() {
         return this.id;
    }

    /**
     * @param value the id to set
     */
    public void setId(final int value) {
         this.id = value;
    }

    /**
     * @return the enigme
     */
    public String getEnigme() {
         return this.enigme;
    }

    /**
     * @param value the enigme to set
     */
    public void setEnigme(final String value) {
         this.enigme = value;
    }

    /**
     * @return the arguments
     */
    public String getArguments() {
         return this.arguments;
    }

    /**
     * @param value the arguments to set
     */
    public void setArguments(final String value) {
         this.arguments = value;
    }

    /**
     * @return the reponse
     */
    public ArrayList<Reponses> getReponse() {
         return this.reponse;
    }

    /**
     * @param value the reponse to set
     */
    public void setReponse(final ArrayList<Reponses> value) {
         this.reponse = value;
    }

    /**
     * This stub of code is regenerated. DO NOT MODIFY.
     * 
     * @param dest Destination parcel
     * @param flags flags
     */
    public void writeToParcelRegen(Parcel dest, int flags) {
        if (this.parcelableParents == null) {
            this.parcelableParents = new ArrayList<Parcelable>();
        }
        if (!this.parcelableParents.contains(this)) {
            this.parcelableParents.add(this);
        }
        dest.writeInt(this.getId());
        dest.writeString(this.getEnigme());
        dest.writeString(this.getArguments());

        if (this.getReponse() != null) {
            dest.writeInt(this.getReponse().size());
            for (Reponses item : this.getReponse()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }
        this.parcelableParents = null;    
    }

    /**
     * Regenerated Parcel Constructor. 
     *
     * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
     *
     * @param parc The parcel to read from
     */
    public void readFromParcel(Parcel parc) {
        this.setId(parc.readInt());
        this.setEnigme(parc.readString());
        this.setArguments(parc.readString());

        int nbReponse = parc.readInt();
        if (nbReponse > -1) {
            ArrayList<Reponses> items =
                new ArrayList<Reponses>();
            for (int i = 0; i < nbReponse; i++) {
                items.add((Reponses) parc.readParcelable(
                        Reponses.class.getClassLoader()));
            }
            this.setReponse(items);
        }
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Questions(Parcel parc) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.readFromParcel(parc);

        // You can  implement your own parcel mechanics here.

    }

    /* This method is not regenerated. You can implement your own parcel mechanics here. */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.writeToParcelRegen(dest, flags);
        // You can  implement your own parcel mechanics here.
    }

    /**
     * Use this method to write this entity to a parcel from another entity.
     * (Useful for relations)
     *
     * @param parent The entity being parcelled that need to parcel this one
     * @param dest The destination parcel
     * @param flags The flags
     */
    public synchronized void writeToParcel(List<Parcelable> parents, Parcel dest, int flags) {
        this.parcelableParents = new ArrayList<Parcelable>(parents);
        dest.writeParcelable(this, flags);
        this.parcelableParents = null;
    }

    @Override
    public int describeContents() {
        // This should return 0 
        // or CONTENTS_FILE_DESCRIPTOR if your entity is a FileDescriptor.
        return 0;
    }

    /**
     * Parcelable creator.
     */
    public static final Parcelable.Creator<Questions> CREATOR
        = new Parcelable.Creator<Questions>() {
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }
        
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };
    
    @Override
	public String toString() {
		return this.enigme;
	}

}
