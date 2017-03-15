name := """amazon-SNS"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies += javaJpa

libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0"

libraryDependencies += javaWs % "test"

libraryDependencies += "org.hibernate" % "hibernate-core" % "5.2.5.Final"

libraryDependencies += "com.amazonaws" % "aws-java-sdk-ec2" % "1.11.22"

libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.11.22"

libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.22"

libraryDependencies += "org.mariadb.jdbc" % "mariadb-java-client" % "1.5.7"
