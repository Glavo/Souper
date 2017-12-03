lazy val root = (project in file("."))
  .aggregate(ssoup)
  .settings(
    autoScalaLibrary := false
  )

lazy val ssoup = (project in file("ssoup"))
  .settings(Common.common: _*)
  .settings(
    name := "Ssoup",
    libraryDependencies += "org.jsoup" % "jsoup" % "1.11.2"
  )