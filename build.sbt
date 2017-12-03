lazy val root = (project in file("."))
  .aggregate(souper)
  .settings(
    autoScalaLibrary := false
  )

lazy val souper = (project in file("souper"))
  .settings(Common.common: _*)
  .settings(
    name := "Souper",
    libraryDependencies += "org.jsoup" % "jsoup" % "1.11.2"
  )