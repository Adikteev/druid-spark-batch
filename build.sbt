/*
 *  Licensed to Metamarkets Group Inc. (Metamarkets) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  Metamarkets licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
organization := "io.druid.extensions"
name := "druid-spark-batch"


lazy val commonSettings = Seq(
  organization := "io.druid.extensions",
  name := "druid-spark-batch",
  version := "1.1",
  scalaVersion := "2.11.12"
)

licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
homepage := Some(url("https://github.com/metamx/druid-spark-batch"))
crossScalaVersions := Seq("2.11.12")
scalaVersion := "2.11.12"
releaseIgnoreUntrackedFiles := true

val druid_version = "0.14.1-incubating"
// This is just used here for Path, so anything that doesn't break spark should be fine
val hadoop_version = "2.7.3"
val spark_version = "2.4.1"
val guava_version = "16.0.1"
val mesos_version
= "0.25.0"

val sparkDep = ("org.apache.spark" %% "spark-core" % spark_version
  exclude("org.roaringbitmap", "RoaringBitmap")
  exclude("log4j", "log4j")
  exclude("org.slf4j", "slf4j-log4j12")
  exclude("com.google.guava", "guava")
  exclude("org.apache.hadoop", "hadoop-client")
  //exclude("org.apache.hadoop", "hadoop-yarn-api")
  //exclude("org.apache.hadoop", "hadoop-yarn-common")
  exclude("com.sun.jersey", "jersey-server")
  exclude("com.sun.jersey", "jersey-core")
  exclude("com.sun.jersey", "jersey-core")
  exclude("com.sun.jersey.contribs", "jersey-guice")
  exclude("org.eclipse.jetty", "jetty-server")
  exclude("org.eclipse.jetty", "jetty-plus")
  exclude("org.eclipse.jetty", "jetty-util")
  exclude("org.eclipse.jetty", "jetty-http")
  exclude("org.eclipse.jetty", "jetty-servlet")
  exclude("com.esotericsoftware.minlog", "minlog")
  
  exclude("com.fasterxml.jackson.core", "jackson-core")
  exclude("com.fasterxml.jackson.core", "jackson-annotations")
  exclude("com.fasterxml.jackson.dataformat", "jackson-dataformat-smile")
  exclude("com.fasterxml.jackson.datatype", "jackson-datatype-joda")
  exclude("com.fasterxml.jackson.core", "jackson-databind")
 
  exclude("io.netty", "netty")
  exclude("org.apache.mesos", "mesos")
  ) 
libraryDependencies += sparkDep

val hadoopDep = ("org.apache.hadoop" % "hadoop-client" % hadoop_version
  exclude("asm", "asm")
  exclude("org.ow2.asm", "asm")
  exclude("org.jboss.netty", "netty")
  exclude("commons-logging", "commons-logging")
  exclude("com.google.guava", "guava")
  exclude("org.mortbay.jetty", "servlet-api-2.5")
  exclude("javax.servlet", "servlet-api")
  exclude("junit", "junit")
  exclude("org.slf4j", "slf4j-log4j12")
  exclude("log4j", "log4j")
  exclude("commons-beanutils", "commons-beanutils")
  //exclude("org.apache.hadoop", "hadoop-yarn-api")
  exclude("com.sun.jersey", "jersey-server")
  exclude("com.sun.jersey", "jersey-core")
  exclude("com.sun.jersey", "jersey-core")
  exclude("com.sun.jersey.contribs", "jersey-guice")
  exclude("org.eclipse.jetty", "jetty-server")
  exclude("org.eclipse.jetty", "jetty-plus")
  exclude("org.eclipse.jetty", "jetty-util")
  exclude("org.eclipse.jetty", "jetty-http")
  exclude("org.eclipse.jetty", "jetty-servlet")
  exclude("commons-beanutils", "commons-beanutils-core")
  exclude("com.fasterxml.jackson.core", "jackson-core")
  exclude("com.fasterxml.jackson.core", "jackson-annotations")
  exclude("com.fasterxml.jackson.dataformat", "jackson-dataformat-smile")
  exclude("com.fasterxml.jackson.datatype", "jackson-datatype-joda")
  exclude("com.fasterxml.jackson.core", "jackson-databind")
  exclude("io.netty", "netty")
  ) 
// For Path
libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "2.7.3"

libraryDependencies += hadoopDep

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "org.apache.druid" % "druid-processing" % druid_version 
libraryDependencies += "org.apache.druid" % "druid-server" % druid_version 
libraryDependencies += "org.apache.druid" % "druid-indexing-service" % druid_version
libraryDependencies += "org.apache.druid" % "druid-indexing-hadoop" % druid_version 
libraryDependencies += "org.apache.druid.extensions" % "druid-avro-extensions" % druid_version 
libraryDependencies += "org.apache.druid.extensions" % "druid-parquet-extensions" % druid_version 

libraryDependencies +=
  "org.joda" % "joda-convert" % "1.8.1"  // Prevents intellij silliness and sbt warnings
libraryDependencies += "com.google.guava" % "guava" % guava_version // Prevents serde problems for guice exceptions
libraryDependencies += "com.sun.jersey" % "jersey-servlet" % "1.17.1" 
libraryDependencies += "com.google.inject" % "guice" % "4.1.0"
libraryDependencies += "com.google.inject.extensions" % "guice-multibindings" % "4.1.0"

libraryDependencies += "org.apache.mesos" % "mesos"  % mesos_version   classifier "shaded-protobuf"

lazy val parquetV = "1.10.1"

lazy val shaded = (project in file("."))
  .settings(commonSettings)

libraryDependencies += "org.apache.parquet" % "parquet-common" % parquetV
libraryDependencies += "org.apache.parquet" % "parquet-encoding" % parquetV
libraryDependencies += "org.apache.parquet" % "parquet-column" % parquetV
libraryDependencies += "org.apache.parquet" % "parquet-hadoop" % parquetV
libraryDependencies += "org.apache.parquet" % "parquet-avro" % parquetV

libraryDependencies += "org.apache.zookeeper" % "zookeeper" % "3.5.4-beta" 
libraryDependencies += "org.apache.curator" % "curator-framework" % "4.1.0"
libraryDependencies += "io.dropwizard.metrics" % "metrics-core" % "4.1.0"
libraryDependencies += "io.dropwizard.metrics" % "metrics-jvm" % "4.1.0"
libraryDependencies += "io.dropwizard.metrics" % "metrics-json" % "4.1.0"
libraryDependencies += "io.dropwizard.metrics" % "metrics-graphite" % "4.1.0"

libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8"

releaseCrossBuild := true

dependencyOverrides +="com.google.guava" % "guava" % guava_version

  assemblyMergeStrategy in assembly := {
  case PathList("org", "objectweb", xs@_*) => MergeStrategy.first
  case PathList("com", "amazonaws", xs@_*) => MergeStrategy.first
  case PathList("org", "apache", "commons", xs@_*) => MergeStrategy.first
  case PathList("org", "apache", "log4j", xs@_*) => MergeStrategy.first
  case PathList("org", "slf4j", xs@_*) => MergeStrategy.first
  case PathList("com", "sun", "activation", xs@_*) => MergeStrategy.first
  case PathList("com", "sun", "research", xs@_*) => MergeStrategy.first
  case PathList("io", "netty", xs@_*) => MergeStrategy.last
  case PathList("javax", "activation", xs@_*) => MergeStrategy.first
  case PathList("javax", "inject", xs@_*) => MergeStrategy.first
  case PathList("javax", "ws", xs@_*) => MergeStrategy.first
  case PathList("jersey", "repackaged", xs@_*) => MergeStrategy.first
  case PathList("org", "aopalliance", xs@_*) => MergeStrategy.first
  case PathList("javax", "el", xs@_*) => MergeStrategy.first
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case PathList("org", "apache", "commons", "logqging", xs@_*) => MergeStrategy.first
  case PathList("javax", "annotation", xs@_*) => MergeStrategy.last //favor jsr305
  case PathList("mime.types") => MergeStrategy.filterDistinctLines
  case PathList("com", "google", "common", "base", xs@_*) => MergeStrategy.last // spark-network-common pulls these in
  case PathList("org", "apache", "spark", "unused", xs@_*) => MergeStrategy.first
  case PathList("META-INF", xs@_*) =>
    xs map {
      _.toLowerCase
    } match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps@(x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "services" :: xs =>
        MergeStrategy.filterDistinctLines
      case "jersey-module-version" :: xs => MergeStrategy.first
      case "sisu" :: xs => MergeStrategy.discard
      case "maven" :: xs => MergeStrategy.discard
      case "plexus" :: xs => MergeStrategy.discard
      case _ => MergeStrategy.discard
    }
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

resolvers += Resolver.mavenLocal
resolvers += "JitPack.IO" at "https://jitpack.io"
resolvers += "clojar" at "https://clojars.org/repo"
resolvers += "Confluent Platform" at "http://packages.confluent.io/maven/"

publishMavenStyle := true

//TODO: remove this before moving to druid.io
publishTo := Some("central-local" at "https://metamx.artifactoryonline.com/metamx/libs-releases-local")
pomIncludeRepository := { _ => false }

pomExtra := (
  <scm>
    <url>https://github.com/metamx/druid-spark-batch.git</url>
    <connection>scm:git:git@github.com:metamx/druid-spark-batch.git</connection>
  </scm>
    <developers>
      <developer>
        <name>Metamarkets Open Source Team</name>
        <email>oss@metamarkets.com</email>
        <organization>Metamarkets Group Inc.</organization>
        <organizationUrl>https://www.metamarkets.com</organizationUrl>
      </developer>
    </developers>
  )

testOptions += Tests.Argument(TestFrameworks.JUnit, "-Duser.timezone=UTC")
// WTF SBT?
javaOptions in Test += "-Duser.timezone=UTC"
fork in Test := true
