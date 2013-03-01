<#assign curr = entities[current_entity] />

${project_name}_rest_${curr.name?uncap_first}:
    type:     rest
    resource: ${project_name?cap_first}\ApiBundle\Controller\Rest${curr.name?cap_first}Controller
