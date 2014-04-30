<#assign curr = entities[current_entity] />	/**
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
