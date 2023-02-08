import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

val reportMerge by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
    output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.sarif"))
}

detekt {
    config = files("$rootDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    parallel = true
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
