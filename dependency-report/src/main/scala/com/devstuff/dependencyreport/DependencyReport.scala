package com.devstuff.dependencyreport

import sbt._
import Keys._

object DependencyReport extends Plugin {
  lazy val dependencyReport = TaskKey[String]("dependency-report")
  lazy val dependencyReportCsv = TaskKey[String]("dependency-report-csv")

  val dependencyReportSettings: Seq[Setting[_]] = inConfig(Compile)(unscopedSettings) ++ inConfig(Test)(unscopedSettings)

  lazy val unscopedSettings: Seq[Setting[_]] = Seq(
    dependencyReport <<= (externalDependencyClasspath in Compile, streams) map {
      (cp: Seq[Attributed[File]], streams) =>
         val report: String = cp.map {
           attributed =>
             attributed.get(Keys.moduleID.key) match {
               case Some(moduleId) => "%-35s %-30s %-10s %-10s".format(
                 moduleId.organization,
                 moduleId.name,
                 moduleId.revision,
                 moduleId.configurations.getOrElse("")
               )

               case None => // unmanaged JAR
                 attributed.data.getAbsolutePath
             }
         }.sortWith(_ < _).mkString("\n")
       println("\n" + report.split("\n").length + " dependencies:\n" + report + "\n")
       report
    },

    dependencyReportCsv <<= (externalDependencyClasspath in Compile, streams) map {
      (cp: Seq[Attributed[File]], streams) =>
        val report: String = cp.map {
          attributed =>
            attributed.get(Keys.moduleID.key) match {
              case Some(moduleId) =>
                "\"%s\", \"%s\", \"%s\", \"%s\"".format(moduleId.organization, moduleId.name, moduleId.revision, moduleId.configurations.getOrElse(""))

              case None =>
                // unmanaged JAR
                attributed.data.getAbsolutePath
            }
        }.sortWith(_ < _).mkString("\n")
        println(report)
        report
    }
  )
}
