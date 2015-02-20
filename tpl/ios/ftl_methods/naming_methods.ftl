<#function alias name>
    <#return "COL_" +name?upper_case />
</#function>

<#function fixtureAlias field>
    <#return field.name?upper_case />
</#function>

<#function fixtureParsedAlias field>
    <#return "parsed${field.name?cap_first}" />
</#function>
