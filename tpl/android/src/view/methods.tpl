<#function setLoader field>
	<#if field.type=="date" || field.type=="time" || field.type=="datetime">
		<#assign pre=appendTry()>
		<#assign post=appendCatch()>
	<#else>
		<#assign pre="">
		<#assign post="">
	</#if>

	<#assign type=field.type?lower_case>
	<#assign ret=pre+"this."+field.name+"View">
	<#if type=="boolean">
		<#assign ret=ret+".setChecked(this.model.is"+field.name?cap_first+"());">
	<#else>
		<#assign getter="this.model.get"+field.name?cap_first+"()">
		<#assign ret=ret+".setText(">
		<#if type=="string" || type=="email" || type=="login" || type=="password" || type=="city" || type=="text" || type=="phone" || type=="country">
			<#assign ret=ret+getter>
		<#elseif type=="datetime">
			<#assign ret=ret+getter+".toString(DateTimeFormat.shortDateTime())">
		<#elseif type=="date">
			<#assign ret=ret+"DateFormat.getDateFormat(getActivity()).format("+getter+".toDate())">
		<#elseif type=="time">
			<#assign ret=ret+getter+".toString(DateTimeFormat.shortTime())">
		<#elseif type == "int" || type=="long" || type=="ean" || type=="zipcode" || type=="float">
			<#assign ret=ret+"String.valueOf("+getter+")"> 
		</#if>
		<#assign ret=ret+");"+post>
	</#if>
	<#return ret>
</#function>

<#function setAdapterLoader field>
	<#assign type=field.type?lower_case>
	<#assign ret="this."+field.name+"View">
	<#if type=="boolean">
		<#assign ret=ret+".setChecked(model.is"+field.name?cap_first+"());">
	<#else>
		<#assign getter="model.get"+field.name?cap_first+"()">
		<#assign ret=ret+".setText(">
		<#if type=="string" || type=="email" || type=="login" || type=="password" || type=="city" || type=="text" || type=="phone" || type=="country">
			<#assign ret=ret+getter>
		<#elseif type=="datetime">
			<#assign ret=ret+getter+".toString(DateTimeFormat.shortDateTime())">
		<#elseif type=="date">
			<#assign ret=ret+getter+".toString(DateTimeFormat.shortDate())">
		<#elseif type=="time">
			<#assign ret=ret+getter+".toString(DateTimeFormat.shortTime())">
		<#elseif type == "int" || type=="long" || type=="ean" || type=="zipcode" || type=="float">
			<#assign ret=ret+"String.valueOf("+getter+")">
		</#if>
		<#assign ret=ret+");">
	</#if>
	<#return ret>
</#function>

<#function setSaver field>
	<#if field.type=="date" || field.type=="time" || field.type=="datetime">
		<#assign pre=appendTry()>
		<#assign post=appendCatch()>
	<#else>
		<#assign pre="">
		<#assign post="">
	</#if>

	<#assign type=field.type?lower_case>
	<#assign ret=pre+"this.model.set"+field.name?cap_first+"(">
	<#if type=="boolean">
		<#assign ret=ret+"this."+field.name+"View.isChecked());">
	<#else>
		<#assign getter="this."+field.name+"View.getEditableText().toString()">
		<#if type=="string" || type=="email" || type=="login" || type=="password" || type=="city" || type=="text" || type=="phone" || type=="country">
			<#assign ret=ret+getter>
		<#elseif type=="datetime">
			<#assign ret=ret+"DateTimeFormat.shortDateTime().parseDateTime("+getter+")">
		<#elseif type=="date">
			<#assign ret=ret+"new DateTime(DateFormat.getDateFormat(getActivity()).parse("+getter+").getTime())">
		<#elseif type=="time">
			<#assign ret=ret+"DateTimeFormat.shortTime().parseDateTime("+getter+")">
		<#elseif type == "int" || type=="ean" || type=="zipcode">
			<#assign ret=ret+"Integer.parseInt("+getter+")">
		<#elseif type=="long">
			<#assign ret=ret+"Long.parseLong("+getter+")">
		<#elseif type=="float">
			<#assign ret=ret+"Float.parseFloat("+getter+")">
		</#if>
		<#assign ret=ret+");"+post>
	</#if>
	<#return ret>
</#function>

<#function appendTry>
	<#return "try{
				"/>
</#function>

<#function appendCatch>
	<#return "
			}catch(Exception e){
				e.printStackTrace();
			}

"/>
</#function>
