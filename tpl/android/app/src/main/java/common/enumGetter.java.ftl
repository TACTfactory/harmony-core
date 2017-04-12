<#include utilityPath + "all_imports.ftl" />
<#assign curr = enums[current_entity] />
${indentLevel}/**
${indentLevel} * @return the value
${indentLevel} */
${indentLevel}public ${FieldsUtils.getJavaType(curr.fields[curr.id])} getValue() {
${indentLevel}     return this.${curr.id};
${indentLevel}}
