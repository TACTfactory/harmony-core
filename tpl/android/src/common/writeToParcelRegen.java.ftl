<#include utilityPath + "all_imports.ftl" />    /**
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
        <#if curr.inheritance?? && curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??>
        super.writeToParcelRegen(dest, flags);
        </#if>
        <#list curr.fields?values as field>
            <#if !field.internal>
                <#if field.harmony_type?lower_case != "relation">
                    <#switch FieldsUtils.getJavaType(field)?lower_case>
                        <#case "int">
                            <#if field.primitive>
        dest.writeInt(this.get${field.name?cap_first}());
                            <#else>
        if (this.get${field.name?cap_first}() != null) {
            dest.writeInt(this.get${field.name?cap_first}());
        }
                            </#if>
                            <#break />
                        <#case "long">
                            <#if field.primitive>
        dest.writeLong(this.get${field.name?cap_first}());
                            <#else>
        if (this.is${field.name?cap_first}() != null) {
            dest.writeLong(this.get${field.name?cap_first}());
        }
                            </#if>
                            <#break />
                        <#case "boolean">
                            <#if field.primitive>
        if (this.is${field.name?cap_first}()) {
            dest.writeInt(1);
        } else {
            dest.writeInt(0);
        }
                            <#else>
        if (this.is${field.name?cap_first}() != null) {
            if (this.is${field.name?cap_first}()) {
                dest.writeInt(1);
            } else {
                dest.writeInt(0);
            }
        }
                            </#if>
                            <#break />
                        <#case "string">
        if (this.get${field.name?cap_first}() != null) {
            dest.writeString(this.get${field.name?cap_first}());
        }
                            <#break />
                        <#case "byte">
                            <#if field.primitive>
        dest.writeByte(this.get${field.name?cap_first}());
                            <#else>
        if (this.get${field.name?cap_first}() != null) {
            dest.writeByte(this.get${field.name?cap_first}());
        }
                            </#if>
                            <#break />
                        <#case "char">
                            <#if field.primitive>
        dest.writeString(String.valueOf(this.get${field.name?cap_first}()));
                            <#else>
        if (this.get${field.name?cap_first}() != null) {
            dest.writeInt(1);
            dest.writeString(String.valueOf(this.get${field.name?cap_first}()));
        } else {
            dest.writeInt(0);
        }
                            </#if>
                            <#break />
                        <#case "short">
                            <#if field.primitive>
        dest.writeInt(this.get${field.name?cap_first}());
                            <#else>
        if (this.get${field.name?cap_first}() != null) {
            dest.writeInt(this.get${field.name?cap_first}());
        }
                            </#if>
                            <#break />
                        <#case "datetime">
        if (this.get${field.name?cap_first}() != null) {
            dest.writeInt(1);
            dest.writeString(ISODateTimeFormat.dateTime().print(
                    this.get${field.name?cap_first}()));
        } else {
            dest.writeInt(0);
        }
                            <#break />
                        <#case "enum">
        if (this.get${field.name?cap_first}() != null) {
            dest.writeInt(1);
                        <#assign enumType = enums[field.enum.targetEnum] />
                        <#if enumType.id??>
                            <#assign idEnumType = FieldsUtils.getJavaType(enumType.fields[enumType.id])?lower_case />
                            <#if (idEnumType == "int") >
            dest.writeInt(this.get${field.name?cap_first}().getValue());
                            <#else>
            dest.writeString(this.get${field.name?cap_first}().getValue());
                            </#if>
                        <#else>
            dest.writeString(this.get${field.name?cap_first}().name());
                        </#if>
        } else {
            dest.writeInt(0);
        }
                            <#break />
                    </#switch>
                <#else>
                    <#if field.relation.type == "OneToOne" || field.relation.type == "ManyToOne">
        if (this.get${field.name?cap_first}() != null
                    && !this.parcelableParents.contains(this.get${field.name?cap_first}())) {
            this.get${field.name?cap_first}().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
                    <#else>

        if (this.get${field.name?cap_first}() != null) {
            dest.writeInt(this.get${field.name?cap_first}().size());
            for (${field.relation.targetEntity?cap_first} item : this.get${field.name?cap_first}()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }
                    </#if>
                </#if>
            </#if>
        </#list>
    }
