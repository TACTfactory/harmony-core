<#assign curr = entities[current_entity] />
    /**
     * Parcelable creator.
     */
    public static final Parcelable.Creator<${curr.name?cap_first}> CREATOR
        = new Parcelable.Creator<${curr.name?cap_first}>() {
        public ${curr.name?cap_first} createFromParcel(Parcel in) {
            return new ${curr.name?cap_first}(in);
        }
        
        public ${curr.name?cap_first}[] newArray(int size) {
            return new ${curr.name?cap_first}[size];
        }
    };
