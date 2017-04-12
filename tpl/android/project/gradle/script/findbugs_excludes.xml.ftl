<?xml version="1.0" encoding="UTF-8"?>
<FindBugsFilter>
  <Match>
    <Class name="~.*\.R\$.*"/>
  </Match>
  <Match>
    <Or>
    <Package name="~${project_namespace?replace(".", "[.]")}[.]criterias[.]base([.].*)?" />
    <Package name="~${project_namespace?replace(".", "[.]")}[.]data[.]base([.].*)?" />
    <Package name="~${project_namespace?replace(".", "[.]")}[.]harmony([.].*)?" />
    <Package name="~${project_namespace?replace(".", "[.]")}[.]provider[.]base([.].*)?" />
    <Package name="~${project_namespace?replace(".", "[.]")}[.]provider[.]utils[.]base([.].*)?" />
    <Class name="~${project_namespace?replace(".", "[.]")}[.]${project_name?cap_first}ApplicationBase.java"/>
    </Or>
  </Match>
</FindBugsFilter>
