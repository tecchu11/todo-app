import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${detekt.toolVersion}")
}

project.afterEvaluate {
    configurations["detekt"].resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin") {
            useVersion("1.8.21")
        }
    }
}

detekt {
    config.from("$rootDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    parallel = true
    autoCorrect = true
}

val reportMerge by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
    output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.sarif"))
}

tasks.withType<Detekt> {
    reports {
        html.required.set(false)
        xml.required.set(false)
        md.required.set(false)
        txt.required.set(false)
        sarif.required.set(true)
    }

    finalizedBy(reportMerge)

    reportMerge.configure {
        input.from(this@withType.sarifReportFile)
    }
}
