val projectName = "dependency-report"

lazy val VersionMatch = new Object {
  import scala.math.Ordering.Implicits._

  private val versionRegex = "(\\d+)\\.(\\d+)\\.(\\d+).*".r

  def atLeast(actualVersion: String, requiredVersion: (Int, Int, Int)) = actualVersion match {
    case versionRegex(actualMajor, actualMinor, actualPatch) if (actualMajor.toInt, actualMinor.toInt, actualPatch.toInt) >= requiredVersion => true
    case _ => false
  }
}

// Common to all builds. Do not modify these, change globalSettings instead.
lazy val commonSettings = Seq(
  conflictManager := ConflictManager.latestRevision, // this is the default, but we set it explicitly for IDEA. See: https://youtrack.jetbrains.com/issue/SCL-7646
  homepage := Some(url("https://www.devstuff.com/")),
  javacOptions ++= Seq("-encoding", "utf8", "-source", "1.6", "-target", "1.6", "-Xlint:all"),
  organization := "com.devstuff",
  scalaVersion := "2.10.5",
  scalacOptions ++= Seq("-encoding", "UTF-8", "-target:jvm-1.6", "-unchecked", "-deprecation", "-feature", "-Xfatal-warnings"),
//  scoverage.ScoverageSbtPlugin.ScoverageKeys.coverageFailOnMinimum := true,
//  scoverage.ScoverageSbtPlugin.ScoverageKeys.coverageHighlighting := VersionMatch.atLeast(scalaVersion.value, (2, 11, 2)), // Highlighting will not work until 2.11.2. See https://github.com/scoverage/sbt-scoverage#highlighting
//  scoverage.ScoverageSbtPlugin.ScoverageKeys.coverageMinimum := 100,
  showTiming := true,
  updateOptions := updateOptions.value.withCachedResolution(true), // sbt 0.13.7+
  version := sys.env.getOrElse("BUILD_NUMBER", "1.0.1-SNAPSHOT")
)

// Project-specific settings.
lazy val globalSettings = commonSettings ++ Seq(
  description := "Dependency report plugin.",
  startYear := Some(2015)
)

lazy val dependencyReportPlugin: Project = Project(
  id = projectName,
  base = file(projectName),
  settings = globalSettings ++ Seq(
    //crossPaths := false,
    sbtPlugin := true
  )
)

lazy val root: Project = Project(
  id = "root",
  base = file("."),
  settings = globalSettings ++ Seq(
    // Root project should not publish anything
    publishArtifact := false
  )
).aggregate(dependencyReportPlugin)
