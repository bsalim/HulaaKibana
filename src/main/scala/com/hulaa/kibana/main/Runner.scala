package com.hulaa.kibana.main


import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.session.{HashSessionIdManager, HashSessionManager, SessionHandler}
import org.eclipse.jetty.servlet.{DefaultServlet, ServletContextHandler, ServletHolder}

/**
 * Deny Prasetyo,S.T
 * Java(Scala) Developer and Trainer
 * Software Engineer | Java Virtual Machine Junkie!
 * jasoet87@gmail.com
 * <p/>
 * http://github.com/jasoet
 * http://bitbucket.com/jasoet
 *
 */

object Runner extends App {
  private lazy val contextPath = "/"
  private lazy val port = 8085


  private def getContextHandler: ServletContextHandler = {
    val contextHandler: ServletContextHandler = new ServletContextHandler()
    contextHandler.setErrorHandler(null)
    contextHandler.setContextPath(contextPath)
    // contextHandler.setInitParameter("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext")
    // contextHandler.setInitParameter("contextConfigLocation",
    //   """
    //     |com.hulaa.core.config.LocaleSessionConfig,
    //     |com.hulaa.scheduler.config.EnvironmentConfig,
    //     |com.hulaa.scheduler.config.RepositoryConfig,
    //     |com.hulaa.scheduler.config.WebScalatraConfig,
    //     |com.hulaa.scheduler.config.ScheduleConfig
    //   """.stripMargin)

    // contextHandler.addEventListener(new ContextLoaderListener())
    // contextHandler.addEventListener(new RequestContextListener())
    // contextHandler.setSessionHandler(new SessionHandler(new HashSessionManager))
    //    contextHandler.addFilter(new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain")), "/*", util.EnumSet.allOf(classOf[DispatcherType]));
    // logger.info(s"Serve Static From ${this.getClass.getClassLoader.getResource("webapp").toExternalForm}")
    contextHandler.setResourceBase(this.getClass.getClassLoader.getResource("webapp").toExternalForm)
    val defaulHolder = new ServletHolder(new DefaultServlet)
    defaulHolder.setInitParameter("acceptRanges", "true")
    defaulHolder.setInitParameter("cacheControl", "max-age=3600,public")
    contextHandler.addServlet(defaulHolder, "/kibana/*")
    contextHandler
  }

  private lazy val _server: Server = new Server(port)
  _server.setSessionIdManager(new HashSessionIdManager())
  _server.setStopAtShutdown(true)
  _server.setHandler(getContextHandler)

  def start() = {
    if (_server.isStopped) {
      _server.start()
      _server.join()
    }
  }

  def stop() = {
    if (_server.isRunning) {
      _server.stop()
    }
  }

  def server = _server

  start()

}
