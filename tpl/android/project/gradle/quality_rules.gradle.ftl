apply plugin: 'checkstyle'
apply plugin: 'findbugs'

check.dependsOn 'checkstyle', 'findbugs'

task checkstyle(type: Checkstyle) {
    ignoreFailures = true
    showViolations = true

    configFile file("${r"${project.rootDir}"}/checkstyle_rules.xml")
    source 'src'
    include '**/*.java'
    exclude '**/gen/**'

    classpath = files()
}

task findbugs(type: FindBugs) {
    ignoreFailures = true
    effort = "max"
    reportLevel = "high"
    excludeFilter = new File("${r"${project.rootDir}"}/findbugs_excludes.xml")
    classes = files("${r"$project"}.buildDir/intermediates/classes/")

    source 'src'
    include '**/*.java'
    exclude '**/gen/**'

    reports {
        xml {
            destination "$project.buildDir/reports/findbugs/findbugs.xml"
            xml.withMessages true
        }
    }

    classpath = files()
}