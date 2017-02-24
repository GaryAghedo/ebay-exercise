

name:= "ebay exercise"
version := "1.0"

scalaVersion := "2.12.1"
val specs2Version = "3.8.7"
val rootDependencies = Seq(
  "org.specs2"             %%   "specs2-core"      % specs2Version,
  "com.github.nscala-time" %%   "nscala-time"      % "2.14.0"
)

libraryDependencies ++= rootDependencies


