<#assign curr = entities[current_entity] />	/**
	 * Use this method to write this entity to a parcel from another entity.
	 * (Useful for relations)
	 *
	 * @param parent The entity being parcelled that need to parcel this one
	 * @param dest The destination parcel
	 * @param flags The flags
	 */
	public synchronized void writeToParcel(Parcelable parent, Parcel dest, int flags) {
		this.parcelableParent = parent;
		dest.writeParcelable(this, flags);
		this.parcelableParent = null;
	}
