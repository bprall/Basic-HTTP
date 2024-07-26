package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * HTTPConnection represents a single client connection to this server.
 *
 * @version 1.0
 */
public class HTTPConnection implements Runnable {
    private Socket client;
    private Scanner socketInput;
    private DataOutputStream socketOutput;
    private boolean connected;

    HTTPConnection(Socket client) {
        this.client = client;
    }

    private void processRequest() {
        try {
            var request = new HTTPRequest(socketInput);
            if (request.isValid()) {
                String url = request.getHostHeader() + request.getPath();
                System.out.println(client + ": Received valid request for " + url);
                HTTPResponse response = new HTTPResponse(socketOutput, request);
                response.sendResponse();
                System.out.println(client + ": Sending response with status code: " + response.getStatusCode());
                if (response.getStatusCode() != 200) {
                    connected = false;
                }
            }
            else {
                System.out.println(client + ": Received invalid request");
                HTTPResponse response = new HTTPResponse(socketOutput, request);
                response.sendResponse();
                System.out.println(client + ": Sending response with status code: " + response.getStatusCode());
                connected = false;
            }              
        } catch (NoSuchElementException e) {
            connected = false;
        }
    }

    @Override
    public void run() {
        System.out.println(client + ": connected");
        connected = true;

        try {
            socketInput = new Scanner(client.getInputStream());
            socketOutput = new DataOutputStream(client.getOutputStream());

            while(connected) {
                processRequest();
            }
        } catch(Exception e) {
            System.out.println(client + ": socket error: " + e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {}
            System.out.println(client + ": closed");
        }
    }
}