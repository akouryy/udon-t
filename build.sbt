name := "Udon Twitter Client"

version := "a.0"

scalaVersion := "2.11.6"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.40-R8"
libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.4"

fork := true

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xlint")
