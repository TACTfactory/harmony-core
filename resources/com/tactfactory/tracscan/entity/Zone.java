package com.tactfactory.tracscan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;

@Entity
public class Zone  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"Zone";

	/** Serial Version UID */
	private static final long serialVersionUID = 7335864735424803701L;

	
	@Id
	@Column(type = Type.INTEGER, hidden = true)
	protected int id;
	
	@Column
	protected String name;
	
	@Column(type = Type.INTEGER, defaultValue="1")
	protected int quantity;


	/**
	 * Default constructor.
	 */
	public Zone() {

	}

	@Override
	public String toString() {
		return this.name;
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
	 * @return the name
	 */
	public String getName() {
	     return this.name;
	}

	/**
	 * @param value the name to set
	 */
	public void setName(final String value) {
	     this.name = value;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
	     return this.quantity;
	}

	/**
	 * @param value the quantity to set
	 */
	public void setQuantity(final int value) {
	     this.quantity = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId());
		dest.writeString(this.getName());
		dest.writeInt(this.getQuantity());
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
		this.setName(parc.readString());
		this.setQuantity(parc.readInt());
	}











	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public Zone(Parcel parc) {
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

	@Override
	public int describeContents() {
		// This should return 0 
		// or CONTENTS_FILE_DESCRIPTOR if your entity is a FileDescriptor.
		return 0;
	}

	/**
	 * Parcelable creator.
	 */
	public static final Parcelable.Creator<Zone> CREATOR
	    = new Parcelable.Creator<Zone>() {
		public Zone createFromParcel(Parcel in) {
		    return new Zone(in);
		}
		
		public Zone[] newArray(int size) {
		    return new Zone[size];
		}
	};

}
