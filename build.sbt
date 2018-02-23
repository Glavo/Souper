name := "Souper"

organization := "org.glavo"

version := "1.0.0"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.11", "2.12.4")

// https://mvnrepository.com/artifact/org.jetbrains/annotations
libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.11.2",
  "org.jetbrains" % "annotations" % "15.0"
)

