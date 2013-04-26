name := "jetty-examples" 

version := "0.1-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.mortbay.jetty" % "jetty" % "6.1.22",
  "javax.servlet" % "servlet-api" % "2.5" % "provided"
)

artifactName := { (config:ScalaVersion, module:ModuleID, artifact:Artifact) =>
  artifact.name + "." + artifact.extension
}
