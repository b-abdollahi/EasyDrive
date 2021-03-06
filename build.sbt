name := "easydrive"

version := "1.0"

lazy val `easydrive` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( javaJdbc , javaEbean , cache , javaWs , filters)


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  