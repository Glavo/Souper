import sbt._
import sbt.Keys._

object Common {
  val common = Seq(
    scalaVersion := "2.12.4",
    version := "0.0.1"
  )
}