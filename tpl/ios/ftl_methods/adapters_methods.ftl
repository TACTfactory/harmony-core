<<#function itemToContentValuesFieldAdapter objectName field indentLevel = 0>
    <#assign t = Utils.getIndentString(indentLevel) />
    <#assign result = "" />
    <#if (!field.internal && field.writable)>
        <#assign fieldNames = ContractUtils.getFieldsNames(field) />
        <#if (!field.relation??)>
            <#if (FieldsUtils.getObjectiveType(field)?lower_case == "byte")>
                <#assign result = result + "    [result setObject:[NSNumber numberWithUnsignedChar:item.${field.name}]\n" />
                <#assign result = result + "${t}       forKey:${fieldNames[0]}]; \n" />
            <#elseif (MetadataUtils.isPrimitive(field))>
                <#assign result = result + "    [result setObject:${FieldsUtils.generateFieldContentType(\"item\", field)}item.${field.name}]\n" />
                <#assign result = result + "${t}       forKey:${fieldNames[0]}]; \n" />
            <#elseif (FieldsUtils.getObjectiveType(field)?lower_case == "int")>
                <#assign result = result + "    [result setObject:[NSNumber numberWithInt:item.${field.name}]\n" />
                <#assign result = result + "${t}       forKey:${fieldNames[0]}]; \n" />
            <#else>
                <#assign result = result + "    if (item.${field.name} != nil) {\n" />
                <#if field.name != "serverId">
                    <#assign converter = FieldsUtils.generateFieldContentType("item", field) />
                <#else>
                    <#assign converter = "" />
                </#if>
                <#assign result = result + "        [result setObject:${converter}item.${field.name}"/>
                <#if converter == "">
                    <#assign result = result + "\n"/>
                <#else>
                    <#assign result = result + "]\n"/>
                </#if>
                <#assign result = result + "${t}           forKey:${fieldNames[0]}]; \n"/>
                <#assign result = result + "    }\n"/>
            </#if>
        <#else>
            <#assign targetEntity = entities[field.relation.targetEntity] />
            <#if (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
                <#assign result = result + "    if (item.${field.name} != nil) {\n"/>
                <#list targetEntity.ids as id>
                    <#if FieldsUtils.getObjectiveType(id)?lower_case=="string">
                    <#assign result = result + "        [result setObject:${FieldsUtils.generateFieldContentType(\"item\", id)}item.${field.name?uncap_first}.${id.name?uncap_first}\n"/>
                    <#assign result = result + "${t}           forKey:${fieldNames[id_index]}]; \n"/>
                    <#else>
                    <#assign result = result + "        [result setObject:${FieldsUtils.generateFieldContentType(\"item\", id)}item.${field.name?uncap_first}.${id.name?uncap_first}]\n"/>
                    <#assign result = result + "${t}           forKey:${fieldNames[id_index]}]; \n"/>
                    </#if>
                </#list>
                <#assign result = result + "    }\n"/>
            </#if>
        </#if>
    </#if>
    <#return result />
</#function>

<#function cursorToItemFieldAdapter objectName field indentLevel = 0>
    <#assign tab = "    " />
    <#assign result = "" />
    <#assign fieldNames = ContractUtils.getFieldsNames(field) />
    <#if (!field.internal && !(field.relation?? && (field.relation.type=="ManyToMany" || field.relation.type=="OneToMany")))>
        <#assign localTab="" />
        <#if (field.harmony_type?lower_case != "relation")>
        <#assign result = result + "${localTab}index = [cursor columnIndexForName:${fieldNames[0]}];\n"/>
            <#if (field.nullable)>
            <#assign result = result + "\n${tab}${localTab}if (![cursor columnIndexIsNull:index]) {\n"/><#assign localTab="    " />
            </#if>
            <#switch FieldsUtils.getObjectiveType(field)?lower_case>
                <#case "datetime">
                    <#assign result = result + "${tab}${localTab}NSDate* dt${field.name?cap_first} ="/>
                    <#assign result = result + " [DateUtils isoStringToDate:[cursor stringForColumnIndex:index]];\n\n"/>
                    <#assign result = result + "${tab}${localTab}if (dt${field.name?cap_first} != nil) {\n"/>
                    <#assign result = result + "${tab}${localTab}${tab}[result set${field.name?cap_first}:dt${field.name?cap_first}];\n"/>
                    <#assign result = result + "${tab}${localTab}} else {\n"/>
                    <#assign result = result + "${tab}${localTab}${tab}[result set${field.name?cap_first}:[NSDate date]];\n"/>
                    <#assign result = result + "${tab}${localTab}}\n"/>
                    <#break />
                <#case "boolean">
                    <#assign result = result + "${tab}${localTab}[result set${field.name?cap_first}:[cursor intForColumnIndex:index] == 1];\n"/>
                    <#break />
                <#case "byte">
                <#case "int">
                <#case "integer">
                    <#assign result = result + "${tab}${localTab}[result set${field.name?cap_first}:[cursor intForColumnIndex:index]];\n"/>
                    <#break />
                <#case "short">
                    <#assign result = result + "${tab}${localTab}[result set${field.name?cap_first}:(short)[cursor intForColumnIndex:index]];\n"/>
                    <#break />
                <#case "nsnumber">
                    <#assign result = result + "${tab}${localTab}[result set${field.name?cap_first}:[NSNumber numberWithInt:[cursor intForColumnIndex:index]]];\n"/>
                    <#break />
                <#case "relation">
                    <#assign result = result + "${tab}${localTab}[${field.name} set${id.name?cap_first}:[cursor intForColumnIndex:index]];\n"/>
                    <#break />
                <#case "float">
                    <#assign result = result + "${tab}${localTab}[result set${field.name?cap_first}:[cursor longlongForColumnIndex:index]];\n"/>
                    <#break />
                <#case "double">
                    <#assign result = result + "${tab}${localTab}[result set${field.name?cap_first}:[cursor doubleForColumnIndex:index]];\n"/>
                    <#break />
                <#case "long">
                    <#assign result = result + "${tab}${localTab}[result set${field.name?cap_first}:[cursor longForColumnIndex:index]];\n"/>
                    <#break />
                <#case "char">
                    <#assign result = result + "${tab}${localTab}[result set${field.name?cap_first}:[[cursor stringForColumnIndex:index] UTF8String][0]];\n"/>
                    <#break />
                <#case "character">
                    <#assign result = result + "${tab}${localTab}[result set${field.name?cap_first}:(const unsigned char *)[[cursor stringForColumnIndex:index] UTF8String][0]];\n"/>
                    <#break />
                <#case "string">
                    <#assign result = result + "${tab}${localTab}[result set${field.name?cap_first}:[cursor stringForColumnIndex:index]];\n"/>
                    <#break />
                <#default>
                        <#assign result = result + "${tab}${localTab}//TODO : Handle type / ${FieldsUtils.getObjectiveType(field)} \n"/>
            </#switch>
            <#if (field.nullable?? && field.nullable)>
                <#assign result = result + "${tab}}\n"/>
            </#if>
        <#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
            <#assign result = result + "if (result.${field.name?uncap_first} == nil) {\n" />
            <#assign result = result + "${tab}${localTab}    ${field.relation.targetEntity}* ${field.name} = [[${field.relation.targetEntity} alloc] init];\n"/>
                <#list entities[field.relation.targetEntity].ids as id>
                    <#assign result = result + "${tab}${localTab}index = [cursor columnIndexForName:${fieldNames[id_index]}];\n\n"/>
                    <#if (field.nullable)>
                        <#assign result = result + "${tab}${tab}if (![cursor columnIndexIsNull:index]) {\n"/><#assign localTab="        " />
                    </#if>
                    <#switch FieldsUtils.getObjectiveType(id)?lower_case>
                    <#case "boolean">
                        <#assign result = result + "${tab}${localTab}[${field.name} set${id.name?cap_first}:[cursor intForColumnIndex:index] == 1};\n"/>
                        <#break />
                    <#case "relation">
                        <#assign result = result + "${tab}${localTab}[${field.name} set${id.name?cap_first}:[cursor intForColumnIndex:index]];\n"/>
                        <#break />
                    <#case "byte">
                    <#case "int">
                    <#case "integer">
                    <#assign result = result + "${tab}${localTab}[${field.name} set${id.name?cap_first}:[cursor intForColumnIndex:index]];\n"/>
                        <#break />
                    <#case "float">
                        <#assign result = result + "${tab}${localTab}[${field.name} set${id.name?cap_first}:[cursor longlongForColumnIndex:index]];\n"/>
                        <#break />
                    <#case "double">
                        <#assign result = result + "${tab}${localTab}[${field.name} set${id.name?cap_first}:[cursor doubleForColumnIndex:index]];\n"/>
                        <#break />
                    <#case "long">
                        <#assign result = result + "${tab}${localTab}[${field.name} set${id.name?cap_first}:[cursor longForColumnIndex:index]];\n"/>
                        <#break />
                    <#case "char">
                    <#case "string">
                        <#assign result = result + "${tab}${localTab}[${field.name} set${id.name?cap_first}:[cursor stringForColumnIndex:index]];\n"/>
                        <#break />
                    <#default>
                            <#assign result = result + "${tab}${localTab}//TODO : Handle type / ${FieldsUtils.getObjectiveType(field)} \n"/>
                </#switch>
                    <#assign result = result + "${tab}${localTab}    [result set${field.name?cap_first}:${field.name}];\n"/>
                    <#if (field.nullable?? && field.nullable)>
                        <#assign result = result + "${tab}${localTab}}\n"/>
                    </#if>
                </#list>
            <#assign result = result + "${tab}}\n" />
        </#if>
    </#if>
    <#if (result?length > 0)><#assign result = result + "\n" /></#if>
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
            <#switch FieldsUtils.getObjectiveType(field)?lower_case>
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
                        <#assign idEnumType = FieldsUtils.getObjectiveType(enumType.fields[enumType.id])?lower_case />
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
                        <#assign result = result + "${tab}${localTab}//TODO : Handle type ${field.type} / ${FieldsUtils.getObjectiveType(field)}"/>
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
            && FieldsUtils.getObjectiveType(field)?lower_case != "boolean"
            && field.harmony_type?lower_case != "enum"
            && field.writable>
        <#if !field.nullable>
            <#assign result = result + "${tab}    if (self.${field.name}TextField.text.length < 1) {" />
            <#assign result = result + "${tab}      error = @\"Error in ${field.name}.\";" />
            <#assign result = result + "${tab}    }\n" />
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
    <#if (!field.internal && !field.hidden)>
            <#if (field.harmony_type?lower_case != "relation")>
                <#if (!MetadataUtils.isPrimitive(field))>
                    <#if (FieldsUtils.getObjectiveType(field)?lower_case == "datetime")>
        <#assign result = result + "${tab}self.${field.name}TextField.text = [DateUtils dateToISOString:self.model.${field.name}];" />
                    <#elseif (field.harmony_type?lower_case == "enum")>
        <#assign result = result + "${tab}self.${field.name}TextField.text = [NSString stringWithFormat:@\"%lu\", (unsigned long)self.model.${field.name}];" />
                    <#elseif (field.harmony_type?lower_case == "char") || (field.harmony_type?lower_case == "character")>
        <#assign result = result + "${tab}self.${field.name}TextField.text = [NSString stringWithFormat:@\"%c\", self.model.${field.name}];" />
                    <#else>
        <#assign result = result + "${tab}self.${field.name}TextField.text = self.model.${field.name};" />
                    </#if>
                <#elseif (field.harmony_type?lower_case == "int") || (field.harmony_type?lower_case == "zipcode") || (field.harmony_type?lower_case == "integer") || (field.harmony_type?lower_case == "short") || (field.harmony_type?lower_case == "byte")>
        <#assign result = result + "${tab}self.${field.name}TextField.text = [NSString stringWithFormat:@\"%d\", self.model.${field.name}];" />
                <#elseif (field.harmony_type?lower_case == "char" || (field.harmony_type?lower_case == "character"))>
        <#assign result = result + "${tab}self.${field.name}TextField.text = [NSString stringWithFormat:@\"%c\", self.model.${field.name}];" />
                <#else>
        <#assign result = result + "${tab}${ViewUtils.setLoader(field)}" />
                </#if>
            <#else>
        <#assign result = result + "${tab}if (self.model.${field.name} != nil) {" />
                <#if (field.relation.type=="OneToOne" || field.relation.type=="ManyToOne")>
        <#assign result = result + "${tab}    self.${field.name}TextField.text = [NSString stringWithFormat:@\"%d\", self.model.${field.name}.${entities[field.relation.targetEntity].ids[0].name}];" />
                <#else>
        <#assign result = result + "${tab}    NSMutableArray *${field.name} = [NSMutableArray new];" />
        <#assign result = result + "${tab}    for (${entities[field.relation.targetEntity].name?cap_first} *${entities[field.relation.targetEntity].name?lower_case} in self.model.${field.name}) {"/>
        <#assign result = result + "${tab}        [${field.name} addObject:[NSNumber numberWithInt:${entities[field.relation.targetEntity].name?lower_case}.id]];" />
        <#assign result = result + "${tab}    }\n" />
        <#assign result = result + "${tab}    self.${field.name}TextField.text = [${field.name} componentsJoinedByString:@\",\"];" />
                </#if>
        <#assign result = result + "${tab}}" />
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
                    <#if (FieldsUtils.getObjectiveType(field)?lower_case == "datetime")>
        <#assign result = result + "${tab}self.${field.name}Label.text = [DateUtils dateToISOString:self.model.${field.name}];" />
                    <#elseif (field.harmony_type?lower_case == "enum")>
        <#assign result = result + "${tab}self.${field.name}Label.text = [NSString stringWithFormat:@\"%lu\", (unsigned long)self.model.${field.name}];" />
                <#elseif (field.harmony_type?lower_case == "char") || (field.harmony_type?lower_case == "character")>
        <#assign result = result + "${tab}self.${field.name}Label.text = [NSString stringWithFormat:@\"%c\", self.model.${field.name}];" />
                    <#else>
        <#assign result = result + "${tab}self.${field.name}Label.text = self.model.${field.name};" />
                    </#if>
                <#elseif (field.harmony_type?lower_case == "int") || (field.harmony_type?lower_case == "zipcode") || (field.harmony_type?lower_case == "integer") || (field.harmony_type?lower_case == "short") || (field.harmony_type?lower_case == "byte")>
        <#assign result = result + "${tab}self.${field.name}Label.text = [NSString stringWithFormat:@\"%d\", self.model.${field.name}];" />
                <#elseif (field.harmony_type?lower_case == "char") || (field.harmony_type?lower_case == "character")>
        <#assign result = result + "${tab}self.${field.name}Label.text = [NSString stringWithFormat:@\"%c\", self.model.${field.name}];" />
                <#elseif (field.harmony_type?lower_case == "nsnumber")>
        <#assign result = result + "${tab}self.${field.name}Label.text = [NSString stringWithFormat:@\"%@\", self.model.${field.name}];" />
                <#else>
        <#assign result = result + "${tab}${ViewUtils.setLoader(field)}" />
                </#if>
            <#else>
        <#assign result = result + "${tab}if (self.model.${field.name} != nil) {" />
                <#if (field.relation.type=="OneToOne" || field.relation.type=="ManyToOne")>
        <#assign result = result + "${tab}    self.${field.name}Label.text = [NSString stringWithFormat:@\"%d\", self.model.${field.name}.${entities[field.relation.targetEntity].ids[0].name}];" />
                <#else>
        <#assign result = result + "${tab}    NSMutableArray *${field.name} = [NSMutableArray new];" />
        <#assign result = result + "${tab}    for (${entities[field.relation.targetEntity].name?cap_first} *${entities[field.relation.targetEntity].name?lower_case} in self.model.${field.name}) {"/>
        <#assign result = result + "${tab}        [${field.name} addObject:[NSNumber numberWithInt:${entities[field.relation.targetEntity].name?lower_case}.id]];" />
        <#assign result = result + "${tab}    }\n" />
        <#assign result = result + "${tab}    self.${field.name}Label.text = [${field.name} componentsJoinedByString:@\",\"];" />
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
                <#if (FieldsUtils.getObjectiveType(field)?lower_case == "boolean")>
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
    <#if (FieldsUtils.getObjectiveType(field)?lower_case == "integer" || FieldsUtils.getObjectiveType(field)?lower_case == "int")>
        <#assign result = "cursor.getInt(" />
    <#else>
        <#assign result = "cursor.getString(" />
    </#if>
    <#return result />
</#function>
