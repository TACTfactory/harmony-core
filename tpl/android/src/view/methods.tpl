<#function setLoader field>
	<#assign type=field.type?lower_case>
	<#assign ret="this."+field.name+"View">
	<#if type=="boolean">
		<#assign ret=ret+".setChecked(this.model.is"+field.name?cap_first+"());">
	<#else>
		<#assign getter="this.model.get"+field.name?cap_first+"()">
		<#assign ret=ret+".setText(">
		<#if type=="string" || type=="email" || type=="login" || type=="password" || type=="city" || type=="text" || type=="phone" || type=="country">
			<#assign ret=ret+getter>
		<#elseif type == "int" || type=="long" || type=="ean" || type=="zipcode" || type=="float">
			<#assign ret=ret+"String.valueOf("+getter+")"> 
		</#if>
		<#assign ret=ret+");">
	</#if>
	<#return ret>
</#function>

<#function setAdapterLoader field>
	<#assign type=field.type?lower_case>
	<#assign ret="this."+field.name+"View">
	<#if type=="boolean">
		<#assign ret=ret+".setChecked(model.is"+field.name?cap_first+"());">
	<#elseif type=="datetime" || type=="date" || type=="time">
		<#if type=="datetime" || type=="date">
		<#assign ret="this."+field.name+"DateView.setText(DateUtils.formatDateToString(model.get"+field.name?cap_first+"()));">
		</#if>
		<#if type=="datetime" || type=="time">
		<#assign ret="this."+field.name+"TimeView.setText(DateUtils.formatTimeToString(model.get"+field.name?cap_first+"()));">
		</#if>
	<#else>
		<#assign getter="model.get"+field.name?cap_first+"()">
		<#assign ret=ret+".setText(">
		<#if type=="string" || type=="email" || type=="login" || type=="password" || type=="city" || type=="text" || type=="phone" || type=="country">
			<#assign ret=ret+getter>
		<#elseif type == "int" || type=="long" || type=="ean" || type=="zipcode" || type=="float">
			<#assign ret=ret+"String.valueOf("+getter+")">
		</#if>
		<#assign ret=ret+");">
	</#if>
	<#return ret>
</#function>

<#function setSaver field>
	<#assign type=field.type?lower_case>
	<#assign ret="this.model.set"+field.name?cap_first+"(">
	<#if type=="boolean">
		<#assign ret=ret+"this."+field.name+"View.isChecked());">
	<#elseif type=="date">
		<#assign ret=ret+"DateUtils.formatStringToDate(this."+field.name+"DateView.getEditableText().toString()));">
	<#elseif type=="time">
		<#assign ret=ret+"DateUtils.formatStringToTime(this."+field.name+"TimeView.getEditableText().toString()));">
	<#elseif type=="datetime">
		<#assign ret=ret+"DateUtils.formatStringToDateTime(
				this."+field.name+"DateView.getEditableText().toString(),
				this."+field.name+"TimeView.getEditableText().toString()));">
	<#else>
		<#assign getter="this."+field.name+"View.getEditableText().toString()">
		<#if type=="string" || type=="email" || type=="login" || type=="password" || type=="city" || type=="text" || type=="phone" || type=="country">
			<#assign ret=ret+getter>
		<#elseif type == "int" || type=="ean" || type=="zipcode">
			<#assign ret=ret+"Integer.parseInt("+getter+")">
		<#elseif type=="long">
			<#assign ret=ret+"Long.parseLong("+getter+")">
		<#elseif type=="float">
			<#assign ret=ret+"Float.parseFloat("+getter+")">
		</#if>
		<#assign ret=ret+");">
	</#if>
	<#return ret>
</#function>
