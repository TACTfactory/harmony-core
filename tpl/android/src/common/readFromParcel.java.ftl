<#include utilityPath + "all_imports.ftl" />    /**
     * Regenerated Parcel Constructor. 
     *
     * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
     *
     * @param parc The parcel to read from
     */
    public void readFromParcel(Parcel parc) {
        <#if curr.inheritance?? && curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??>
        super.readFromParcel(parc);
        </#if>
        <#list curr.fields?values as field>
            <#if !field.internal>
                <#if field.harmony_type?lower_case != "relation">
                    <#switch FieldsUtils.getJavaType(field)?lower_case>
                        <#case "int">                    
        this.set${field.name?cap_first}(parc.readInt());
                            <#break />
                        <#case "boolean">
        this.set${field.name?cap_first}(parc.readInt() == 1);
                            <#break />
                        <#case "string">
        this.set${field.name?cap_first}(parc.readString());
                            <#break />
                        <#case "byte">
        this.set${field.name?cap_first}(parc.readByte());
                            <#break />
                        <#case "char">
                            <#if field.primitive>
        this.set${field.name?cap_first}(parc.readString().charAt(0));
                            <#else>
        int ${field.name}Bool = parc.readInt();
        if (${field.name}Bool == 1) {
            this.set${field.name?cap_first}(parc.readString().charAt(0));
        }
                            </#if>
                            <#break />
                        <#case "short">
        this.set${field.name?cap_first}((short) parc.readInt());
                            <#break />
                        <#case "datetime">
        if (parc.readInt() == 1) {
            this.set${field.name?cap_first}(
                    ISODateTimeFormat.dateTimeParser()
                            .withOffsetParsed().parseDateTime(
                                    parc.readString()));
        }
                            <#break />
                        <#case "enum">
        int ${field.name}Bool = parc.readInt();
        if (${field.name}Bool == 1) {
                        <#assign enumType = enums[field.enum.targetEnum] />
                        <#if enumType.id??>
                            <#assign idEnumType = enumType.fields[enumType.id].harmony_type?lower_case />
                            <#if (idEnumType == "int") >
            this.set${field.name?cap_first}(${enumType.name}.fromValue(parc.readInt()));
                            <#else>
            this.set${field.name?cap_first}(${enumType.name}.fromValue(parc.readString()));
                            </#if>
                        <#else>
            this.set${field.name?cap_first}(${enumType.name}.valueOf(parc.readString()));
                        </#if>
        }
                            <#break />
                    </#switch>    
                <#else>
                    <#if field.relation.type == "OneToOne" || field.relation.type == "ManyToOne">
        this.set${field.name?cap_first}((${field.relation.targetEntity}) parc.readParcelable(${field.relation.targetEntity}.class.getClassLoader()));
                    <#else>

        int nb${field.name?cap_first} = parc.readInt();
        if (nb${field.name?cap_first} > -1) {
            ArrayList<${field.relation.targetEntity}> items =
                new ArrayList<${field.relation.targetEntity}>();
            for (int i = 0; i < nb${field.name?cap_first}; i++) {
                items.add((${field.relation.targetEntity}) parc.readParcelable(
                        ${field.relation.targetEntity}.class.getClassLoader()));
            }
            this.set${field.name?cap_first}(items);
        }
                    </#if>
                </#if>
            </#if>
        </#list>
    }
