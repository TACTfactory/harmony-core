apply plugin: 'checkstyle'
apply plugin: 'findbugs'
apply plugin: 'jacoco'
apply plugin: 'pmd'

def gradleExtra="${r"${project.rootDir}"}/gradle/script"
def reportDir="${r"${project.buildDir}"}/reports"
def outputDir="${r"${project.buildDir}"}/intermediates/classes"

task checkstyle(type: Checkstyle) {
    description 'Run Checkstyle'
    group 'verification'

    ignoreFailures = true
    showViolations = true

    configFile file("${r"${gradleExtra}"}/checkstyle_rules_lvl1.xml")
    source 'src'
    include '**/*.java'
    exclude '**/gen/**'

    classpath = files()
}

task findbugs(type: FindBugs, dependsOn: "assembleDebug") {
    description 'Run Findbugs'
    group 'verification'

    ignoreFailures = true
    effort = "max"
    reportLevel = "high"
    excludeFilter = new File("${r"${gradleExtra}"}/findbugs_excludes.xml")
    classes = files("${r"${outputDir}"}")

    source 'src'
    include '**/*.java'
    exclude '**/gen/**'

    reports {
        xml {
            destination "${r"${reportDir}"}/findbugs/findbugs.xml"
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
            "${r"${outputDir}"}/debug",
            excludes: fileFilter)

    def mainSrc = "${r"${project.projectDir}"}"

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

task pmd (type: Pmd) {
    description 'Run pmd'
    group 'verification'

    ignoreFailures = true

    ruleSetFiles = files("${r"${gradleExtra}"}/pmd_rules_lvl1.xml")
    source = fileTree('src/${project_path}')

    reports {
        xml.enabled = true
        html.enabled = true
    }
}
