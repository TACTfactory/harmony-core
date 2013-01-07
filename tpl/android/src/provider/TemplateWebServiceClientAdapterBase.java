package ${project_namespace}.data;

public class ${project_name}WebServiceClientAdapter {
	<#list ${entities as entity}>
	/**
	 *
	 *
	 */
	protected int get${entity.name}s(List<${entity.name} ${entity.name?uncap_first}, int quantity, int page){

	}

	</#list>
}
