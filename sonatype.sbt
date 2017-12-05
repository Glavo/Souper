// Your profile name of the sonatype account. The default is the same with the organization value
sonatypeProfileName := "org.glavo"

// To sync with Maven central, you need to supply the following information:
publishMavenStyle := true

// License of your choice
licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
homepage := Some(url("https://github.com/Glavo/Souper"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/Glavo/Souper"),
    "scm:git@github.com:Glavo/Souper.git"
  )
)
developers := List(
  Developer(id = "", name = "Glavo", email = "zjx001202@gmail.com", url = url("http://www.glavo.org"))
)