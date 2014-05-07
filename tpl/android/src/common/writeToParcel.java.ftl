<#assign curr = entities[current_entity] />	/* This method is not regenerated. You can implement your own parcel mechanics here. */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		<#if curr.inheritance?? && curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??>
		super.writeToParcel(dest, flags);
		<#else>
		// You can chose not to use harmony's generated parcel.
		// To do this, remove this line.
		this.writeToParcelRegen(dest, flags);
		</#if>
		// You can  implement your own parcel mechanics here.
	}
