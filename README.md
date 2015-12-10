 
An sbt plugin to list all dependencies of an SBT project.

Forked from [mslinn/dependencyReport](https://github.com/mslinn/dependencyReport), updated to use Scala 2.10.5 and SBT 0.13.9.

## Installation

Clone this project:
```
git clone git@github.com:devstuff/dependencyReport.git
```

Compile and publish to your local Ivy cache:
```
sbt clean compile publishLocal
```

Add the plugin reference into sbt's user-specific global plugins list, canonical path: `~/.sbt/0.13/global.sbt`:
```
addSbtPlugin("com.devstuff" % "dependency-report" % "1.0.1-SNAPSHOT")
```

Add the plugin settings into sbt's user-specific plugins file, canonical path: `~/.sbt/0.13/plugins/plugins.sbt`:
```
com.devstuff.dependencyreport.DependencyReport.dependencyReportSettings
```

These plugin tasks are now available to your SBT project:

1. `compile:dependencyReport`
2. `test:dependencyReport`
3. `compile:dependencyReportCsv`
4. `test:dependencyReportCsv`

## Usage

Use this plug-in any of the following ways:

```
$ sbt dependencyReport
$ sbt compile:dependencyReport
$ sbt test:dependencyReport
$ sbt
[info] Loading global plugins from /home/devstuff/.sbt/0.13/plugins
[info] Loading project definition from /work/myProject/project
[info] Set current project to myProject (in build file:/work/myProject/)
> dependencyReport
```

## Sample Output

Output is sorted by both Group and Artifact identifiers.

The `dependencyReport` task output includes the number of dependencies. Example output:

```
35 dependencies:
ch.qos.logback                      logback-classic                1.0.0
ch.qos.logback                      logback-core                   1.0.0
com.amazonaws                       aws-java-sdk                   1.1.8
com.google.protobuf                 protobuf-java                  2.4.1
com.novus                           salat-core_2.9.1               1.9.0
...
```

The `dependencyReportCsv` taske output does NOT contain the dependency count. Example output:

```
"ch.qos.logback", "logback-classic", "1.0.0"
"ch.qos.logback", "logback-core", "1.0.0"
"com.amazonaws", "aws-java-sdk", "1.1.8"
"com.google.protobuf", "protobuf-java", "2.4.1"
"com.novus", "salat-core_2.9.1", "1.9.0"
...
```

If you run these tasks from the root of a multiple project build,   
