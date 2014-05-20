<#assign curr = entities[current_entity] />    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public ${curr.name}(Parcel parc) {
        <#if curr.inheritance?? && curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??>
        super(parc);
        <#else>
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.readFromParcel(parc);
        </#if>

        // You can  implement your own parcel mechanics here.

    }
