<#function itemToContentValuesFieldAdapter objectName field indentLevel = 0>
    <#assign t = Utils.getIndentString(indentLevel) />
    <#assign result = "" />
    <#if (!field.internal && field.writable)>
        <#assign fieldNames = ContractUtils.getFieldsNames(field) />
        <#if (!field.relation??)>
            <#if (MetadataUtils.isPrimitive(field))>
                <#assign result = result + "${t}result.put(${fieldNames[0]},\n" />
                <#if (FieldsUtils.getJavaType(field)?lower_case == "boolean")>
                    <#assign result = result + "${t}    ${FieldsUtils.generateStringGetter(\"item\", field)} ? 1 : 0);\n\n"/>
                <#else>
                    <#assign result = result + "${t}    ${FieldsUtils.generateStringGetter(\"item\", field)});\n\n"/>
                </#if>
            <#else>
                <#assign result = result + "${t}if (${FieldsUtils.generateCompleteGetter(\"item\", field)} != null) {\n" />
                <#assign result = result + "${t}    result.put(${fieldNames[0]},\n"/>
                <#if (FieldsUtils.getJavaType(field)?lower_case == "boolean")>
                    <#assign result = result + "${t}    ${FieldsUtils.generateStringGetter(\"item\", field)} ? 1 : 0);\n\n"/>
                <#else>
                    <#assign result = result + "${t}        ${FieldsUtils.generateStringGetter(\"item\", field)});\n"/>
                </#if>
                <#if (field.nullable)>
                    <#assign result = result + "${t}} else {\n"/>
                    <#assign result = result + "${t}    result.put(${fieldNames[0]}, (String) null);\n"/>
                </#if>
                <#assign result = result + "${t}}\n\n"/>
            </#if>
        <#else>
            <#assign targetEntity = entities[field.relation.targetEntity] />
            <#if ((field.relation.type=="OneToOne" && !field.relation.mappedBy??) | field.relation.type=="ManyToOne")>
                <#assign result = result + "${t}if (${FieldsUtils.generateCompleteGetter(\"item\", field)} != null) {\n"/>
                <#list targetEntity.ids as id>
                    <#assign result = result + "${t}    result.put(${fieldNames[id_index]},\n"/>
                    <#assign result = result + "${t}        item.get${field.name?cap_first}().get${id.name?cap_first}());\n"/>
                </#list>
                <#if (field.nullable)>
                    <#assign result = result + "${t}} else {\n"/>
                    <#list targetEntity.ids as id>
                        <#assign result = result + "${t}    result.put(${fieldNames[id_index]}, (String) null);\n"/>
                    </#list>
                </#if>
                <#assign result = result + "${t}}\n\n"/>
            </#if>
        </#if>
    </#if>
    <#return result />
</#function>

<#function fixtureConstantsFieldAdapter field indentLevel = 0>
    <#assign tab = Utils.getIndentString(indentLevel) />
    <#assign result = "" />
    <#if (!field.internal)>
        <#assign result = result + "${tab}/** Constant field for ${field.name}. */\n"/>
        <#assign result = result + "${tab}private static final String ${NamingUtils.fixtureAlias(field)} = \"${field.name?uncap_first}\";\n"/>
    </#if>
    <#return result />
</#function>


<#function cursorToItemFieldAdapter objectName field indentLevel = 0>
    <#assign tab = Utils.getIndentString(indentLevel) />
    <#assign result = "" />
    <#assign fieldNames = ContractUtils.getFieldsNames(field) />
    <#if (!field.internal && !(field.relation?? && (field.relation.type=="ManyToMany" || field.relation.type=="OneToMany")))>
        <#assign localTab="" />
        <#assign otherTab="" />
        <#if (field.harmony_type?lower_case != "relation")>
            <#assign result = result + "${tab}index = cursor.getColumnIndex(${fieldNames[0]});\n\n"/>
            <#assign result = result + "${tab}if (index > -1) {\n"/>
            <#if (field.nullable)>
                <#assign result = result + "${tab}if (!cursor.isNull(index)) {\n"/><#assign localTab="    " />
            </#if>
            <#switch FieldsUtils.getJavaType(field)?lower_case>
                <#case "datetime">
                    <#assign result = result + "${tab}${localTab}    final DateTime dt${field.name?cap_first} =\n"/>
                    <#assign result = result + "${tab}            DateUtils.formatISOStringToDateTime(cursor.getString(index));\n"/>
                    <#assign result = result + "${tab}${localTab}    if (dt${field.name?cap_first} != null) {\n"/>
                    <#assign result = result + "${tab}${localTab}            result.set${field.name?cap_first}(dt${field.name?cap_first});\n"/>
                    <#assign result = result + "${tab}${localTab}    } else {\n"/>
                    <#assign result = result + "${tab}${localTab}        result.set${field.name?cap_first}(new DateTime());\n"/>
                    <#assign result = result + "${tab}${localTab}    }\n"/>
                    <#break />
                <#case "boolean">
                    <#assign result = result + "${tab}${localTab}    result.set${field.name?cap_first}(cursor.getInt(index) == 1);\n"/>
                    <#break />
                <#case "int">
                <#case "integer">
                    <#assign result = result + "${tab}${localTab}    result.set${field.name?cap_first}(cursor.getInt(index));\n"/>
                    <#break />
                <#case "float">
                    <#assign result = result + "${tab}${localTab}    result.set${field.name?cap_first}(cursor.getFloat(index));\n"/>
                    <#break />
                <#case "double">
                    <#assign result = result + "${tab}${localTab}    result.set${field.name?cap_first}(cursor.getDouble(index));\n"/>
                    <#break />
                <#case "long">
                    <#assign result = result + "${tab}${localTab}    result.set${field.name?cap_first}(cursor.getLong(index));\n"/>
                    <#break />
                <#case "short">
                    <#assign result = result + "${tab}${localTab}    result.set${field.name?cap_first}(cursor.getShort(index));\n"/>
                    <#break />
                <#case "char">
                <#case "character">
                    <#assign result = result + "${tab}    String ${field.name?uncap_first}DB = cursor.getString(index);\n"/>
                    <#assign result = result + "${tab}    if (${field.name?uncap_first}DB != null\n"/>
                    <#assign result = result + "${tab}        && ${field.name?uncap_first}DB.length() > 0) {\n"/>
                    <#assign result = result + "${tab}        ${localTab}result.set${field.name?cap_first}(${field.name?uncap_first}DB.charAt(0));\n"/>
                    <#assign result = result + "${tab}    }\n"/>
                    <#break />
                <#case "byte">
                    <#assign result = result + "${tab}${localTab}    result.set${field.name?cap_first}(Byte.valueOf(cursor.getString(index)));\n"/>
                    <#break />
                <#case "string">
                    <#assign result = result + "${tab}${localTab}    result.set${field.name?cap_first}(cursor.getString(index));\n"/>
                    <#break />
                <#case "enum">
                    <#assign enumType = enums[field.enum.targetEnum] />
                    <#if enumType.id??>
                        <#assign idEnumType = FieldsUtils.getJavaType(enumType.fields[enumType.id])?lower_case />
                        <#if (idEnumType == "int" || idEnumType == "integer") >
                            <#assign result = result + "${tab}${localTab}    result.set${field.name?cap_first}(${enumType.name}.fromValue(cursor.getInt(index)));\n"/>
                        <#else>
                            <#assign result = result + "${tab}${localTab}    result.set${field.name?cap_first}(${enumType.name}.fromValue(cursor.getString(index)));\n"/>
                        </#if>
                    <#else>
                        <#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
                        <#assign result = result + "${tab}    ${enumType.name}.valueOf(cursor.getString(index)));\n"/>

                    </#if>
                    <#break />
                <#default>
                        <#assign result = result + "${tab}${localTab}//TODO : Handle type / ${FieldsUtils.getJavaType(field)}"/>
            </#switch>
            <#assign result = result + "${tab}}\n"/>
            <#if (field.nullable?? && field.nullable)>
                <#assign result = result + "${tab}}\n"/>
            </#if>
        <#elseif ((field.relation.type=="OneToOne" && !field.relation.mappedBy??) | field.relation.type=="ManyToOne")>
            <#assign result = result + "${tab}if (result.get${field.name?cap_first}() == null) {\n" />
            <#assign result = result + "${tab}${localTab}    final ${field.relation.targetEntity} ${field.name} = new ${field.relation.targetEntity}();\n"/>
                <#list entities[field.relation.targetEntity].ids as id>
                    <#assign result = result + "${tab}${localTab}    index = cursor.getColumnIndex(${fieldNames[id_index]});\n\n"/>
                    <#assign result = result + "${tab}${localTab}    if (index > -1) {\n"/>
                    <#if (field.nullable)>
                        <#assign result = result + "${tab}${localTab}        if (!cursor.isNull(index)) {\n"/><#assign otherTab="    "/>
                    </#if>
                    <#assign result = result + "${tab}${localTab}${otherTab}        ${field.name}.set${id.name?cap_first}(${AdapterUtils.getCursorGet(id)}index));\n"/>
                    <#assign result = result + "${tab}${localTab}${otherTab}        result.set${field.name?cap_first}(${field.name});\n"/>
                    <#if (field.nullable?? && field.nullable)>
                        <#assign result = result + "${tab}${localTab}        }\n"/>
                    </#if>
                    <#assign result = result + "${tab}${localTab}    }\n\n"/>
                </#list>
            <#assign result = result + "${tab}}\n" />
        </#if>
    </#if>
    <#return result />
</#function>

<#function xmlExtractFieldAdapter objectName field curr indentLevel = 0>
    <#assign result = "" />
    <#assign tab = "\n" + Utils.getIndentString(indentLevel) />
    <#if (!field.internal)>
        <#assign result = result + "${tab}this.currentFieldName = ${NamingUtils.fixtureAlias(field)};" />
        <#assign result = result + "${tab}String ${NamingUtils.fixtureParsedAlias(field)} = element.getChildText(${NamingUtils.fixtureAlias(field)});" />
        <#assign result = result + "${tab}if (${NamingUtils.fixtureParsedAlias(field)} != null) {" />
        <#if field.harmony_type?lower_case != "relation">
            <#switch FieldsUtils.getJavaType(field)?lower_case>
                <#case "int">
                <#case "integer">
                    <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(Integer.parseInt(${NamingUtils.fixtureParsedAlias(field)}));"/>
                    <#break />
                <#case "float">
                    <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(Float.parseFloat(${NamingUtils.fixtureParsedAlias(field)}));"/>
                    <#break />
                <#case "double">
                    <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(Double.parseDouble(${NamingUtils.fixtureParsedAlias(field)}));"/>
                    <#break />
                <#case "datetime">
                    <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(" />
                    <#assign result = result + "${tab}        DateUtils.formatXMLStringToDateTime(" />
                    <#assign result = result + "${tab}                ${NamingUtils.fixtureParsedAlias(field)}));" />
                    <#break />
                <#case "boolean">
                    <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(" />
                    <#assign result = result + "${tab}        Boolean.parseBoolean(${NamingUtils.fixtureParsedAlias(field)}));" />
                    <#break />
                <#case "string">
                    <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(${NamingUtils.fixtureParsedAlias(field)});" />
                    <#break />
                <#case "short">
                    <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(" />
                    <#assign result = result + "${tab}        Integer.valueOf(${NamingUtils.fixtureParsedAlias(field)}).shortValue());" />
                    <#break />
                <#case "char">
                <#case "character">
                    <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(${NamingUtils.fixtureParsedAlias(field)}.charAt(0));" />
                    <#break />
                <#case "byte">
                    <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(" />
                    <#assign result = result + "${tab}        Integer.valueOf(${NamingUtils.fixtureParsedAlias(field)}).byteValue());" />
                    <#break />
                <#case "enum">
                    <#assign enumType = enums[field.enum.targetEnum] />
                    <#if (enumType.id??)>
                        <#assign idEnumType = FieldsUtils.getJavaType(enumType.fields[enumType.id])?lower_case />
                        <#if (idEnumType == "int" || idEnumType == "integer") >
                            <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(${enumType.name}.fromValue(" />
                            <#assign result = result + "${tab}                Integer.parseInt(${NamingUtils.fixtureParsedAlias(field)})));" />
                        <#else>
                            <#assign result = result + "${tab}${objectName}.set${field.name?cap_first}(${enumType.name}.fromValue(" />
                            <#assign result = result + "${tab}                ${NamingUtils.fixtureParsedAlias(field)}));" />
                        </#if>
                    <#else>
                        <#assign result = result + "${tab}${objectName}.set${field.name?cap_first}(${enumType.name}.valueOf(${NamingUtils.fixtureParsedAlias(field)}));" />
                    </#if>
                    <#break />
                <#default>
                        <#assign result = result + "${tab}${localTab}//TODO : Handle type ${field.type} / ${FieldsUtils.getJavaType(field)}"/>
            </#switch>
        <#else>
            <#if (field.relation.type=="OneToOne")>
                <#assign result = result + "${tab}    ${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?uncap_first} = "/>
                <#assign result = result + "${tab}        ${field.relation.targetEntity?cap_first}DataLoader.getInstance(" />
                <#assign result = result + "${tab}                this.ctx).getModelFixture(" />
                <#assign result = result + "${tab}                        ${NamingUtils.fixtureParsedAlias(field)}); "/>
                <#assign result = result + "${tab}if (${field.relation.targetEntity?uncap_first} != null) { "/>
                <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(" />
                <#assign result = result + "${tab}                    ${field.relation.targetEntity?uncap_first});" />
                <#if field.relation.inversedBy??>
                    <#assign invField = MetadataUtils.getInversingField(field) />
                    <#assign result = result + "${tab}    ${field.relation.targetEntity?uncap_first}.set${invField.name?cap_first}(" />
                    <#assign result = result + "${tab}                    ${objectName});" />
                </#if>
            <#assign result = result + "${tab}}" />
            <#elseif (field.relation.type=="ManyToOne")>
                <#assign result = result + "${tab}    ${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?uncap_first} = ${field.relation.targetEntity?cap_first}DataLoader.getInstance(this.ctx)" />
                <#assign result = result + "${tab}                .getModelFixture(${NamingUtils.fixtureParsedAlias(field)});" />
                <#assign result = result + "${tab}    if (${field.relation.targetEntity?uncap_first} != null) {" />
                <#assign result = result + "${tab}        ${objectName}.set${field.name?cap_first}(${field.relation.targetEntity?uncap_first});" />
                <#if field.relation.inversedBy??>
                    <#assign invField = MetadataUtils.getInversingField(field) />
                    <#assign result = result + "${tab}        ArrayList<${curr.name?cap_first}> ${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s =" />
                    <#assign result = result + "${tab}            ${field.relation.targetEntity?uncap_first}.get${invField.name?cap_first}();" />
                    <#assign result = result + "${tab}        if (${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s == null) {" />
                    <#assign result = result + "${tab}            ${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s =" />
                    <#assign result = result + "${tab}                new ArrayList<${curr.name?cap_first}>();" />
                    <#assign result = result + "${tab}        }" />
                    <#assign result = result + "${tab}        ${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s.add(${objectName});" />
                    <#assign result = result + "${tab}        ${field.relation.targetEntity?uncap_first}.set${invField.name?cap_first}(${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s);" />
                </#if>
            <#assign result = result + "${tab}    }" />
            <#else>
                <#assign result = result + "${tab}    ArrayList<${field.relation.targetEntity?cap_first}> ${field.relation.targetEntity?uncap_first}s =" />
                <#assign result = result + "${tab}        new ArrayList<${field.relation.targetEntity?cap_first}>();" />
                <#assign result = result + "${tab}    List<Element> ${field.relation.targetEntity?uncap_first}sMap =" />
                <#assign result = result + "${tab}        element.getChild(${NamingUtils.fixtureAlias(field)}).getChildren();" />
                <#assign result = result + "${tab}    ${field.relation.targetEntity?cap_first}DataLoader ${field.relation.targetEntity?uncap_first}DataLoader = " />
                <#assign result = result + "${tab}            ${field.relation.targetEntity?cap_first}DataLoader.getInstance(this.ctx);\n" />
                <#assign result = result + "${tab}    for (Element ${field.relation.targetEntity?uncap_first}Name : ${field.relation.targetEntity?uncap_first}sMap) {" />
                <#assign result = result + "${tab}        if (${field.relation.targetEntity?uncap_first}DataLoader.items.containsKey(" />
                <#assign result = result + "${tab}                ${field.relation.targetEntity?uncap_first}Name.getText())) {" />
                <#assign result = result + "${tab}            ${field.relation.targetEntity?uncap_first}s.add(" />
                <#assign result = result + "${tab}                ${field.relation.targetEntity?uncap_first}DataLoader.getModelFixture(" />
                <#assign result = result + "${tab}                                ${field.relation.targetEntity?uncap_first}Name.getText()));" />
                <#assign result = result + "${tab}        }" />
                <#assign result = result + "${tab}    }" />
                <#assign result = result + "${tab}    ${objectName}.set${field.name?cap_first}(${field.relation.targetEntity?uncap_first}s);" />
                <#if field.relation.inversedBy??>
                    <#assign invField = MetadataUtils.getInversingField(field) />
                    <#assign result = result + "${tab}    for (${field.relation.targetEntity} ${field.relation.targetEntity?uncap_first} : ${field.relation.targetEntity?uncap_first}s) {" />
                    <#assign result = result + "${tab}        ArrayList<${curr.name?cap_first}> ${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s =" />
                    <#assign result = result + "${tab}            ${field.relation.targetEntity?uncap_first}.get${invField.name?cap_first}();" />
                    <#assign result = result + "${tab}        if (${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s == null) {" />
                    <#assign result = result + "${tab}            ${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s =" />
                    <#assign result = result + "${tab}                new ArrayList<${curr.name?cap_first}>();" />
                    <#assign result = result + "${tab}        }" />
                    <#assign result = result + "${tab}        ${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s.add(${objectName});" />
                    <#assign result = result + "${tab}        ${field.relation.targetEntity?uncap_first}.set${invField.name?cap_first}(${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s);" />
                    <#assign result = result + "${tab}    }" />
                </#if>
            </#if>
        </#if>
        <#assign result = result + "${tab}}\n" />
    </#if>
    <#return result />
</#function>

<#function validateDataFieldAdapter field indentLevel = 0>
    <#assign result = "" />
    <#assign tab = "\n" + Utils.getIndentString(indentLevel) />
    <#if !field.internal
            && !field.hidden
            && FieldsUtils.getJavaType(field)?lower_case != "boolean"
            && field.harmony_type?lower_case != "enum"
            && field.writable>
        <#if !field.nullable>
            <#if !field.relation??>
                <#if FieldsUtils.getJavaType(field)?lower_case == "datetime">
                    <#if (field.harmony_type) == "datetime">
                        <#assign result = result + "${tab}if (this.${field.name}View.getDateTime() == null) {" />
                    <#else>
                        <#assign result = result + "${tab}if (this.${field.name}View.get${field.harmony_type?cap_first}() == null) {" />
                    </#if>
                <#else>
                        <#assign result = result + "${tab}if (Strings.isNullOrEmpty(" />
                        <#assign result = result + "${tab}            this.${field.name}View.getText().toString().trim())) {" />
                </#if>
            <#else>
                <#if ((field.relation.type == "ManyToOne") || (field.relation.type == "OneToOne"))>
                    <#assign result = result + "${tab}if (this.${field.name}Adapter.getSelectedItem() == null) {" />
                <#else>
                    <#assign result = result + "${tab}if (this.${field.name}Adapter.getCheckedItems().isEmpty()) {" />
                </#if>
            </#if>
            <#assign result = result + "${tab}    error = R.string.${field.owner?lower_case}_${field.name?lower_case}_invalid_field_error;" />
            <#assign result = result + "${tab}}" />
        </#if>
    </#if>
    <#return result />
</#function>

<#function saveDataFieldAdapter field indentLevel = 0>
    <#assign result = "" />
    <#assign tab = "\n" + Utils.getIndentString(indentLevel) />
    <#if !field.internal && !field.hidden && field.writable>
        <#if !field.relation??>
            <#assign result = result + "${tab}${ViewUtils.setSaver(field)}\n" />
        <#elseif field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
            <#assign result = result + "${tab}this.model.set${field.name?cap_first}(this.${field.name}Adapter.getSelectedItem());\n" />
        <#else>
            <#assign result = result + "${tab}this.model.set${field.name?cap_first}(this.${field.name}Adapter.getCheckedItems());\n" />
        </#if>
    </#if>
    <#return result/>
</#function>

<#function loadDataCreateFieldAdapter field indentLevel = 0>
    <#assign result = "" />
    <#assign tab = "\n" + Utils.getIndentString(indentLevel) />
    <#if !field.internal && !field.hidden && field.writable>
            <#if !field.relation??>
                <#if !MetadataUtils.isPrimitive(field) >
                    <#if (FieldsUtils.getJavaType(field)?lower_case == "boolean")>
        <#assign result = result + "${tab}if (this.model.is${field.name?cap_first}() != null) {" />
                    <#else>
        <#assign result = result + "${tab}if (this.model.get${field.name?cap_first}() != null) {" />
                    </#if>
                    <#if FieldsUtils.getJavaType(field)?lower_case=="datetime">
                        <#if field.harmony_type=="datetime">
        <#assign result = result + "${tab}    this.${field.name}View.setDateTime(this.model.get${field.name?cap_first}());" />
                        <#elseif (field.harmony_type=="date")>
        <#assign result = result + "${tab}    this.${field.name}View.setDate(this.model.get${field.name?cap_first}());" />
                        <#elseif (field.harmony_type=="time")>
        <#assign result = result + "${tab}    this.${field.name}View.setTime(this.model.get${field.name?cap_first}());" />
                        </#if>
                    <#else>
        <#assign result = result + "${tab}    ${ViewUtils.setLoader(field)}" />
                    </#if>
        <#assign result = result + "${tab}}" />
                <#else>
        <#assign result = result + "${tab}${ViewUtils.setLoader(field)}" />
                </#if>
            </#if>
        </#if>
    <#return result/>
</#function>

<#function loadDataShowFieldAdapter field indentLevel = 0>
    <#assign result = "" />
    <#assign tab = "\n" + Utils.getIndentString(indentLevel) />
    <#if (!field.internal && !field.hidden)>
            <#if (field.harmony_type?lower_case != "relation")>
                <#if (!MetadataUtils.isPrimitive(field))>
                    <#if (FieldsUtils.getJavaType(field)?lower_case == "boolean")>
        <#assign result = result + "${tab}if (this.model.is${field.name?cap_first}() != null) {" />
                    <#else>
        <#assign result = result + "${tab}if (this.model.get${field.name?cap_first}() != null) {" />
                    </#if>
                    <#if (FieldsUtils.getJavaType(field)?lower_case == "datetime")>
                        <#if (field.harmony_type?lower_case == "datetime")>
        <#assign result = result + "${tab}    this.${field.name}View.setText(" />
        <#assign result = result + "${tab}            DateUtils.formatDateTimeToString(" />
        <#assign result = result + "${tab}                    this.model.get${field.name?cap_first}()));" />
                        </#if>
                        <#if (field.harmony_type?lower_case == "date")>
        <#assign result = result + "${tab}    this.${field.name}View.setText(" />
        <#assign result = result + "${tab}            DateUtils.formatDateToString(" />
        <#assign result = result + "${tab}                    this.model.get${field.name?cap_first}()));" />
                        </#if>
                        <#if (field.harmony_type?lower_case == "time")>
        <#assign result = result + "${tab}    this.${field.name}View.setText(" />
        <#assign result = result + "${tab}            DateUtils.formatTimeToString(" />
        <#assign result = result + "${tab}                    this.model.get${field.name?cap_first}()));" />
                        </#if>
                    <#elseif (field.harmony_type?lower_case == "enum")>
        <#assign result = result + "${tab}    this.${field.name}View.setText(this.model.get${field.name?cap_first}().toString());" />
                    <#else>
        <#assign result = result + "${tab}    ${ViewUtils.setLoader(field)}" />
                    </#if>
        <#assign result = result + "${tab}}" />
                <#else>
        <#assign result = result + "${tab}${ViewUtils.setLoader(field)}" />
                </#if>
            <#else>
        <#assign result = result + "${tab}if (this.model.get${field.name?cap_first}() != null) {" />
                <#if (field.relation.type=="OneToOne" || field.relation.type=="ManyToOne")>
        <#assign result = result + "${tab}    this.${field.name}View.setText(" />
        <#assign result = result + "${tab}            String.valueOf(this.model.get${field.name?cap_first}().get${entities[field.relation.targetEntity].ids[0].name?cap_first}()));" />
                <#else>
        <#assign result = result + "${tab}    String ${field.name}Value = \"\";" />
        <#assign result = result + "${tab}    for (${field.relation.targetEntity} item : this.model.get${field.name?cap_first}()) {" />
        <#assign result = result + "${tab}        ${field.name}Value += item.get${entities[field.relation.targetEntity].ids[0].name?cap_first}() + \",\";" />
        <#assign result = result + "${tab}    }" />
        <#assign result = result + "${tab}    this.${field.name}View.setText(${field.name}Value);" />
                </#if>
        <#assign result = result + "${tab}}" />
            </#if>
        </#if>
    <#return result/>
</#function>

<#function populateViewHolderFieldAdapter field indentLevel = 0>
    <#assign result = "" />
    <#assign tab = "\n" + Utils.getIndentString(indentLevel) />
    <#if (!field.internal && !field.hidden)>
        <#if (!field.relation??)>
            <#if !MetadataUtils.isPrimitive(field)>
                <#if (FieldsUtils.getJavaType(field)?lower_case == "boolean")>
                    <#assign result = result + "${tab}if (model.is${field.name?cap_first}() != null) {" />
                <#else>
                    <#assign result = result + "${tab}if (model.get${field.name?cap_first}() != null) {" />
                </#if>
                <#assign result = result + "${tab}    ${ViewUtils.setAdapterLoader(field)}" />
                <#assign result = result + "${tab}}" />
            <#else>
                <#assign result = result + "${tab}${ViewUtils.setAdapterLoader(field)}" />
            </#if>
        <#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
            <#assign result = result + "${tab}if (model.get${field.name?cap_first}() != null) {" />
            <#assign result = result + "${tab}    ${field.name}View.setText(" />
            <#assign result = result + "${tab}            String.valueOf(model.get${field.name?cap_first}().get${entities[field.relation.targetEntity].ids[0].name?cap_first}()));" />
            <#assign result = result + "${tab}}" />
        </#if>
    </#if>
    <#return result/>
</#function>

<#function getCursorGet field>
    <#if (FieldsUtils.getJavaType(field)?lower_case == "integer" || FieldsUtils.getJavaType(field)?lower_case == "int")>
        <#assign result = "cursor.getInt(" />
    <#else>
        <#assign result = "cursor.getString(" />
    </#if>
    <#return result />
</#function>
