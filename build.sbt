// Name of the project
name := "Udon Twitter Client"

// Project version
version := "a.0"

// Version of Scala used by the project
scalaVersion := "2.11.6"

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.40-R8"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xlint")
