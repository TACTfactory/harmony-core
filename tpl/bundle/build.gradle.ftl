task annotationJar(type: Jar) {
    baseName 'bundle-${bundle_name?lower_case}-annotations'
    from sourceSets.main.output
    include '${bundle_namespace?replace(".","/")}/annotation/**'
    
    doLast{
        copy {
            from new File(project.buildDir, 'libs')
            into new File(project.projectDir, 'lib')
            include '*annotations*.jar'
            rename '(.*)-[\0-9]*(.jar)', '$1$2'
        }
    }
}

jar {
    baseName 'bundle-${bundle_name?lower_case}'
    exclude '${bundle_namespace?replace(".","/")}/annotation/**'
    dependsOn annotationJar
}

javadoc {
    exclude '${bundle_namespace?replace(".","/")}/test/**'
}