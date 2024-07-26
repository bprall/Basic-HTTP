package http;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

/**
 * Class representing an HTTP Server.
 *
 * @version 1.0
 */
public class HTTPServer {
    public static void main(String[] args) {
        try (
            var listener = new ServerSocket(80);
        ) {
            var threadPool = Executors.newFixedThreadPool(100);

            while(true) {
                threadPool.execute(new HTTPConnection(listener.accept()));
            }
        } catch(IOException e) {
            System.out.println("main thread: error: " + e);
        }
    }
}