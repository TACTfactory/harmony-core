package com.tactfactory.tracscan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;

@Entity
public class User  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"User";

	/** serial Version UID. */
	private static final long serialVersionUID = -6626602416969280536L;
	
	@Id
	@Column(type = Type.INT, hidden = true)
	protected int id;

	@Column(type = Type.ENUM)
	protected AccountType type;
	
	@Column(type = Type.LOGIN)
	protected String login;
	
	@Column(type = Type.PASSWORD)
	protected String passwd;
	

	/**
	 * Default constructor.
	 */
	public User() {

	}

	@Override
	public String toString() {
		return this.login;
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
	 * @return the type
	 */
	public AccountType getType() {
	     return this.type;
	}

	/**
	 * @param value the type to set
	 */
	public void setType(final AccountType value) {
	     this.type = value;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
	     return this.login;
	}

	/**
	 * @param value the login to set
	 */
	public void setLogin(final String value) {
	     this.login = value;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
	     return this.passwd;
	}

	/**
	 * @param value the passwd to set
	 */
	public void setPasswd(final String value) {
	     this.passwd = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId());

		if (this.getType() != null) {
			dest.writeInt(1);
			dest.writeString(this.getType().getValue());
		} else {
			dest.writeInt(0);
		}
		dest.writeString(this.getLogin());
		dest.writeString(this.getPasswd());
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

		int typeBool = parc.readInt();
		if (typeBool == 1) {
			this.setType(AccountType.fromValue(parc.readString()));
		}
		this.setLogin(parc.readString());
		this.setPasswd(parc.readString());
	}











	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public User(Parcel parc) {
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
	public static final Parcelable.Creator<User> CREATOR
	    = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
		    return new User(in);
		}
		
		public User[] newArray(int size) {
		    return new User[size];
		}
	};

}
