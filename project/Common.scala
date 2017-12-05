import sbt._
import sbt.Keys._

object Common {
  val common = Seq(
    scalaVersion := "2.12.4",
    version := "0.0.1",
    // https://mvnrepository.com/artifact/org.jetbrains/annotations
    libraryDependencies ++= Seq(
      "org.jetbrains" % "annotations" % "15.0",
      "com.novocode" % "junit-interface" % "0.11" % "test"
    )
  )
}