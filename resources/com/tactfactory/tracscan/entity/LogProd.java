package com.tactfactory.tracscan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.Column.Type;

@Entity
public class LogProd  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"LogProd";

	/** Serial Version UID */
	private static final long serialVersionUID = 321829571509107215L;

	@Id
	@Column(type = Type.INTEGER, hidden = true)
	protected int id;
	
	@Column(type = Type.DATETIME)
	protected DateTime createDate;
	
	@Column(type = Type.ENUM)
	protected ItemState stateAction;
	
	@ManyToOne
	protected Zone zoneLogged;
	
	@ManyToOne
	protected User userLogged;
	
	@ManyToOne
	protected ItemProd itemLogged;

	/**
	 * Default constructor.
	 */
	public LogProd() {

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
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId());
		if (this.getCreateDate() != null) {
			dest.writeInt(1);
			dest.writeString(this.getCreateDate().toString());
		} else {
			dest.writeInt(0);
		}

		if (this.getStateAction() != null) {
			dest.writeInt(1);
			dest.writeString(this.getStateAction().getValue());
		} else {
			dest.writeInt(0);
		}

		dest.writeParcelable(this.getZoneLogged(), flags);

		dest.writeParcelable(this.getUserLogged(), flags);

		dest.writeParcelable(this.getItemLogged(), flags);
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
			this.setCreateDate(new DateTime(parc.readString()));
		}

		int stateActionBool = parc.readInt();
		if (stateActionBool == 1) {
			this.setStateAction(ItemState.fromValue(parc.readString()));
		}

		this.setZoneLogged((Zone) parc.readParcelable(Zone.class.getClassLoader()));

		this.setUserLogged((User) parc.readParcelable(User.class.getClassLoader()));

		this.setItemLogged((ItemProd) parc.readParcelable(ItemProd.class.getClassLoader()));
	}













	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public LogProd(Parcel parc) {
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
	public static final Parcelable.Creator<LogProd> CREATOR
	    = new Parcelable.Creator<LogProd>() {
		public LogProd createFromParcel(Parcel in) {
		    return new LogProd(in);
		}
		
		public LogProd[] newArray(int size) {
		    return new LogProd[size];
		}
	};

	/**
	 * @return the createDate
	 */
	public DateTime getCreateDate() {
	     return this.createDate;
	}

	/**
	 * @param value the createDate to set
	 */
	public void setCreateDate(final DateTime value) {
	     this.createDate = value;
	}

	/**
	 * @return the stateAction
	 */
	public ItemState getStateAction() {
	     return this.stateAction;
	}

	/**
	 * @param value the stateAction to set
	 */
	public void setStateAction(final ItemState value) {
	     this.stateAction = value;
	}

	/**
	 * @return the zoneLogged
	 */
	public Zone getZoneLogged() {
	     return this.zoneLogged;
	}

	/**
	 * @param value the zoneLogged to set
	 */
	public void setZoneLogged(final Zone value) {
	     this.zoneLogged = value;
	}

	/**
	 * @return the userLogged
	 */
	public User getUserLogged() {
	     return this.userLogged;
	}

	/**
	 * @param value the userLogged to set
	 */
	public void setUserLogged(final User value) {
	     this.userLogged = value;
	}

	/**
	 * @return the itemLogged
	 */
	public ItemProd getItemLogged() {
	     return this.itemLogged;
	}

	/**
	 * @param value the itemLogged to set
	 */
	public void setItemLogged(final ItemProd value) {
	     this.itemLogged = value;
	}

}
