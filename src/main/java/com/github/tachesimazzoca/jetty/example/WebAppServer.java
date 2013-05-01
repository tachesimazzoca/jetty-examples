package com.github.tachesimazzoca.jetty.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.deployer.WebAppDeployer;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.nio.SelectChannelConnector;

public class WebAppServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(8080);
        server.addConnector(connector);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        HandlerCollection handler = new HandlerCollection();
        handler.setHandlers(new Handler[] { contexts });
        server.setHandler(handler);

        WebAppDeployer webapp = new WebAppDeployer();
        webapp.setContexts(contexts);
        webapp.setWebAppDir("./webapps");
        webapp.setParentLoaderPriority(false);
        webapp.setExtract(false);
        webapp.setAllowDuplicates(false);
        server.addLifeCycle(webapp);

        server.start();
        server.join();
    }
}
