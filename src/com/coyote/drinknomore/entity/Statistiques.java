package com.coyote.drinknomore.entity;

import org.joda.time.format.ISODateTimeFormat;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;

@Entity
public class Statistiques implements Serializable, Parcelable {

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

	@Column(type = Type.DATETIME)
	private DateTime date;

	@Column(type = Type.INT)
	private Integer nberreurs;

	/**
	 * Default constructor.
	 */
	public Statistiques() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @param value
	 *            the id to set
	 */
	public void setId(final int value) {
		this.id = value;
	}

	/**
	 * @return the date
	 */
	public DateTime getDate() {
		return this.date;
	}

	/**
	 * @param value
	 *            the date to set
	 */
	public void setDate(final DateTime value) {
		this.date = value;
	}

	/**
	 * @return the nberreurs
	 */
	public Integer getNberreurs() {
		return this.nberreurs;
	}

	/**
	 * @param value
	 *            the nberreurs to set
	 */
	public void setNberreurs(final Integer value) {
		this.nberreurs = value;
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
        if (this.getDate() != null) {
            dest.writeInt(1);
            dest.writeString(ISODateTimeFormat.dateTime().print(
                    this.getDate()));
        } else {
            dest.writeInt(0);
        }
        dest.writeInt(this.getNberreurs());
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
        if (parc.readInt() == 1) {
            this.setDate(
                    ISODateTimeFormat.dateTimeParser()
                            .withOffsetParsed().parseDateTime(
                                    parc.readString()));
        }
        this.setNberreurs(parc.readInt());
    }

	/**
	 * Parcel Constructor.
	 * 
	 * @param parc
	 *            The parcel to read from
	 */
	public Statistiques(Parcel parc) {
		// You can chose not to use harmony's generated parcel.
		// To do this, remove this line.
		this.readFromParcel(parc);

		// You can implement your own parcel mechanics here.

	}

	/*
	 * This method is not regenerated. You can implement your own parcel
	 * mechanics here.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// You can chose not to use harmony's generated parcel.
		// To do this, remove this line.
		this.writeToParcelRegen(dest, flags);
		// You can implement your own parcel mechanics here.
	}

	/**
	 * Use this method to write this entity to a parcel from another entity.
	 * (Useful for relations)
	 * 
	 * @param parent
	 *            The entity being parcelled that need to parcel this one
	 * @param dest
	 *            The destination parcel
	 * @param flags
	 *            The flags
	 */
	public synchronized void writeToParcel(List<Parcelable> parents,
			Parcel dest, int flags) {
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
	public static final Parcelable.Creator<Statistiques> CREATOR = new Parcelable.Creator<Statistiques>() {
		public Statistiques createFromParcel(Parcel in) {
			return new Statistiques(in);
		}

		public Statistiques[] newArray(int size) {
			return new Statistiques[size];
		}
	};

}
