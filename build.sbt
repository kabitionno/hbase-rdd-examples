import AssemblyKeys._

name := "hbase-rdd-examples"

version := "0.4.1"

scalaVersion := "2.10.4"

assemblySettings

assemblyOption in assembly ~= { _.copy(includeScala = false) }

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:postfixOps"
)

resolvers ++= Seq(
  "Cloudera repos" at "https://repository.cloudera.com/artifactory/cloudera-repos",
  "Cloudera releases" at "https://repository.cloudera.com/artifactory/libs-release"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.2.0-cdh5.3.0" % "provided",
  "org.apache.spark" %% "spark-streaming" % "1.2.0-cdh5.3.0" % "provided",
  "org.apache.hbase" % "hbase-common" % "0.98.6-cdh5.3.0" % "provided",
  "org.apache.hbase" % "hbase-client" % "0.98.6-cdh5.3.0" % "provided",
  "org.apache.hbase" % "hbase-server" % "0.98.6-cdh5.3.0" % "provided",
  "eu.unicredit" %% "hbase-rdd" % "0.4.1"
)