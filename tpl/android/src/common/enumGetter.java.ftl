<#assign curr = enums[current_entity] />
${indentLevel}/**
${indentLevel} * @return the value
${indentLevel} */
${indentLevel}public ${curr.fields[curr.id].type} getValue() {
${indentLevel}     return this.${curr.id};
${indentLevel}}
