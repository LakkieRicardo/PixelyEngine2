ThisBuild / scalaVersion := "2.13.6"
ThisBuild / organization := "net.lakkie.pixelyengine2"
ThisBuild / version := "0.1-snapshot"

lazy val root = (project in file("."))
    .settings(
        name := "PixelyEngine",
        assembly / mainClass := Some("pixely.test.TestMain"),
        libraryDependencies += "io.spray" %%  "spray-json" % "1.3.6",
    )

lazy val utils = (project in file("utils"))
    .settings(
        assembly / assemblyJarName := "PixelyEngine2.jar"
    )