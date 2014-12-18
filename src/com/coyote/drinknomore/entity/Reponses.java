package com.coyote.drinknomore.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.OneToMany;

@Entity
public class Reponses  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=Strategy.MODE_IDENTITY)
    @Column(type = Type.INT, hidden = true)
    private int id;

	@Column(type = Type.STRING)
    private String reponse;
	
	@Column(type = Type.STRING)
    private String arguments;
	
	@ManyToOne(targetEntity="Questions", inversedBy="reponse")
	private Questions questions;


    /**
     * Default constructor.
     */
    public Reponses() {

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
     * @return the reponse
     */
    public String getReponse() {
         return this.reponse;
    }

    /**
     * @param value the reponse to set
     */
    public void setReponse(final String value) {
         this.reponse = value;
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
     * @return the questions
     */
    public Questions getQuestions() {
         return this.questions;
    }

    /**
     * @param value the questions to set
     */
    public void setQuestions(final Questions value) {
         this.questions = value;
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
        dest.writeString(this.getReponse());
        dest.writeString(this.getArguments());
        if (this.getQuestions() != null
                    && !this.parcelableParents.contains(this.getQuestions())) {
            this.getQuestions().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
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
        this.setReponse(parc.readString());
        this.setArguments(parc.readString());
        this.setQuestions((Questions) parc.readParcelable(Questions.class.getClassLoader()));
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Reponses(Parcel parc) {
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
    public static final Parcelable.Creator<Reponses> CREATOR
        = new Parcelable.Creator<Reponses>() {
        public Reponses createFromParcel(Parcel in) {
            return new Reponses(in);
        }
        
        public Reponses[] newArray(int size) {
            return new Reponses[size];
        }
    };

}
