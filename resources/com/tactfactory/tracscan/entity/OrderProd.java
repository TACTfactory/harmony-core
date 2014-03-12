package com.tactfactory.tracscan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.OneToMany;

@Entity
public class OrderProd  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"OrderProd";

	/** Serial Version UID */
	private static final long serialVersionUID = -659234701808707520L;

	
	@Id
	@Column(type = Type.INTEGER, hidden = true)
	protected int id;
	
	@Column
	protected String customer;
	
	@Column(type = Type.ENUM)
	protected ProductType productType;
	
	@Column(type = Type.ENUM)
	protected MaterialType materialType;
	
	@Column
	protected int quantity;
	
	@OneToMany
	@Column(nullable=true)
	protected ArrayList<ItemProd> items;

	/**
	 * Default constructor.
	 */
	public OrderProd() {

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
	 * @return the login
	 */
	public String getCustomer() {
	     return this.customer;
	}

	/**
	 * @param value the login to set
	 */
	public void setCustomer(final String value) {
	     this.customer = value;
	}

	/**
	 * @return the productType
	 */
	public ProductType getProductType() {
	     return this.productType;
	}

	/**
	 * @param value the productType to set
	 */
	public void setProductType(final ProductType value) {
	     this.productType = value;
	}

	/**
	 * @return the materialType
	 */
	public MaterialType getMaterialType() {
	     return this.materialType;
	}

	/**
	 * @param value the materialType to set
	 */
	public void setMaterialType(final MaterialType value) {
	     this.materialType = value;
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
	 * @return the items
	 */
	public ArrayList<ItemProd> getItems() {
	     return this.items;
	}

	/**
	 * @param value the items to set
	 */
	public void setItems(final ArrayList<ItemProd> value) {
	     this.items = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId());
		dest.writeString(this.getCustomer());

		if (this.getProductType() != null) {
			dest.writeInt(1);
			dest.writeString(this.getProductType().getValue());
		} else {
			dest.writeInt(0);
		}

		if (this.getMaterialType() != null) {
			dest.writeInt(1);
			dest.writeString(this.getMaterialType().getValue());
		} else {
			dest.writeInt(0);
		}
		dest.writeInt(this.getQuantity());

		if (this.getItems() != null) {
			dest.writeInt(this.getItems().size());
			for (ItemProd item : this.getItems()) {
				dest.writeParcelable(item, flags);
			}
		} else {
			dest.writeInt(-1);
		}
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
		this.setCustomer(parc.readString());

		int productTypeBool = parc.readInt();
		if (productTypeBool == 1) {
			this.setProductType(ProductType.fromValue(parc.readString()));
		}

		int materialTypeBool = parc.readInt();
		if (materialTypeBool == 1) {
			this.setMaterialType(MaterialType.fromValue(parc.readString()));
		}
		this.setQuantity(parc.readInt());

		int nbItems = parc.readInt();
		if (nbItems > -1) {
			ArrayList<ItemProd> items =
				new ArrayList<ItemProd>();
			for (int i = 0; i < nbItems; i++) {
				items.add((ItemProd) parc.readParcelable(
						ItemProd.class.getClassLoader()));
			}
			this.setItems(items);
		}
	}









	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public OrderProd(Parcel parc) {
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
	public static final Parcelable.Creator<OrderProd> CREATOR
	    = new Parcelable.Creator<OrderProd>() {
		public OrderProd createFromParcel(Parcel in) {
		    return new OrderProd(in);
		}
		
		public OrderProd[] newArray(int size) {
		    return new OrderProd[size];
		}
	};

}
