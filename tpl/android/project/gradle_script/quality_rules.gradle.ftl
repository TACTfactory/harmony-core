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
            destination "${r"$project"}.buildDir/reports/findbugs/findbugs.xml"
            xml.withMessages true
        }
    }

    classpath = files()
}

task jacocoTestReport(type:JacocoReport, dependsOn: "connectedDebugAndroidTest") {

    group = "Reporting"

    description = "Generate Jacoco coverage reports"

    // exclude auto-generated classes and tests
    def fileFilter = ['**/R.class',
                      '**/R$*.class',
                      '**/BuildConfig.*',
                      '**/Manifest*.*',
                      '${project_path}/view/*',
                      '${project_path}/menu/*',
                      '${project_path}/harmony/*'
                      ]

    def debugTree = fileTree(dir:
            "${r"${project.buildDir}"}/intermediates/classes/debug",
            excludes: fileFilter)

    def mainSrc = "${r"${project.projectDir}"}/src/${project_path}"

    sourceDirectories = files([mainSrc])
    classDirectories = files([debugTree])

    executionData = fileTree(dir: project.projectDir, includes:
            ['**/*.exec', '**/*.ec'])

    reports {
        xml.enabled = true
        xml.destination = "${r"${buildDir}"}/jacocoTestReport.xml"
        csv.enabled = false
        html.enabled = true
        html.destination = "${r"${buildDir}"}/reports/jacoco"
    }
}