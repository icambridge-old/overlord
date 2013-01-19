import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName         = "overlord"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.google.guava" % "guava" % "12.0",
    "org.jclouds" % "jclouds-compute" % "1.5.5",
    "org.jclouds" % "jclouds-loadbalancer" % "1.5.5",
    "org.jclouds.provider" % "rackspace-cloudservers-uk" % "1.5.5",
    "org.jclouds.provider" % "rackspace-cloudservers-us" % "1.5.5",
    "org.jclouds.provider" % "rackspace-cloudloadbalancers-uk" % "1.5.5",
    "org.jclouds.provider" % "rackspace-cloudloadbalancers-us" % "1.5.5",
    "org.reflections" % "reflections" % "0.9.7.RC1",
    "com.google.code.findbugs" % "jsr305" % "1.3.+"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    resolvers += "Maven central" at "http://repo1.maven.org/maven2/",
    resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  )

}