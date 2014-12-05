import com.earldouglas.xsbtwebplugin.PluginKeys._
import com.earldouglas.xsbtwebplugin.WebPlugin._
import org.flywaydb.sbt.FlywayPlugin._
import org.scalatra.sbt._
import sbt.Keys._
import sbt._
import xerial.sbt.Pack._

object Build extends Build {
  lazy val project = Project(
    Name,
    file("."),
    settings = ScalatraPlugin.scalatraWithDist ++ packSettings ++ Seq(
      scalacOptions ++= Seq("-unchecked", "-deprecation"),
      packJvmOpts := Map("runner" -> Seq("-Xmx1024m")),
      port in container.Configuration := 8085,
      packMain := Map("runner" -> "com.hulaa.kibana.main.Runner"),
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Hulaa Archiva Repository" at "http://molokai.hulaa.com:8084/repository/internal/",
      credentials += Credentials("Repository Archiva Managed internal Repository", "molokai.hulaa.com", "hulaa", "Localhost123"),
      libraryDependencies ++= (libraryDependencies in ThisBuild).value ++ Seq(
        "org.eclipse.jetty" % "jetty-webapp" % "9.1.5.v20140505" % "container",
        "org.eclipse.jetty" % "jetty-plus" % "9.1.5.v20140505" % "container",
        "org.eclipse.jetty" % "jetty-webapp" % JettyVersion
      ),
      resourceGenerators in Compile <+= (resourceManaged, baseDirectory) map { (managedBase, base) =>
        val webappBase = base / "src" / "main" / "webapp"
        for {
          (from, to) <- webappBase ** "*" pair rebase(webappBase, managedBase / "main" / "webapp")
        } yield {
          Sync.copy(from, to)
          to
        }
      }
    )
  ).enablePlugins(play.twirl.sbt.SbtTwirl)
    .settings(flywaySettings: _*).settings(
      flywayUrl := "jdbc:mariadb://localhost/scheduler",
      flywayUser := "root",
      flywayPassword := "localhost",
      flywayDriver := "org.mariadb.jdbc.Driver",
      flywayLocations := Seq("migration.scheduler"),
      flywayTable := "SCHEMA_VERSION"
    )
  val Organization = "com.hulaa"
  val Name = "HulaaKibana"
  val Version = "1.0"
  val ScalaVersion = "2.11.4"
  val JettyVersion = "9.2.4.v20141103"
  val HikariCPVersion = "2.2.4"
  val LogstashEncoderVersion = "3.4"
}
