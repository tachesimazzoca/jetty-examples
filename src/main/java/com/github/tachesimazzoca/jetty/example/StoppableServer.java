package com.github.tachesimazzoca.jetty.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

public class StoppableServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandlers(new Handler[] {
            new AbstractHandler() {
                public void handle(String target, HttpServletRequest request,
                        HttpServletResponse response, int dispatch)
                               throws IOException, ServletException {
                    Request req = (request instanceof Request)
                            ? (Request) request
                            : HttpConnection.getCurrentConnection().getRequest();
                    req.setHandled(true);

                    response.setContentType("text/plain");
                    response.setStatus(HttpServletResponse.SC_OK);

                    response.getWriter().println("http://" + request.getHeader("Host") + "/stop");
                }
            },
            new AbstractHandler() {
                public void handle(String target, HttpServletRequest request,
                        HttpServletResponse response, int dispatch)
                                throws IOException, ServletException {
                    if (target.equals("/stop")) {
                        new Thread(new ServerStopper(this.getServer())).start();
                    }
                }
            }
        });
        server.start();
        server.join();
    }

    private static class ServerStopper implements Runnable {
        private Server server;

        public ServerStopper(Server server) {
            this.server = server;
        }

        public void run() {
            try {
                server.stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
