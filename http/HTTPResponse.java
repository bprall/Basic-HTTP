package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

/**
 * Class representing a single HTTP response message.
 *
 * @version 1.0
 */
public class HTTPResponse {

    private static final String SERVER_NAME = "prallb";
    private static final String CONTENT_DIRECTORY = "content";
    private static final String ERROR_DIRECTORY = "errors";

    private DataOutputStream output;
    private HTTPRequest request;
    private int statusCode;

    public HTTPResponse(DataOutputStream output, HTTPRequest request) {
        this.output = output;
        this.request = request;
    }

    public void sendResponse() {
        if (request.isValid()) {
            String path = request.getPath();
            if ("/".equals(path)) {
                sendFileResponse("index.html", "text/html");
            } else {
                sendFileResponse(path.substring(1), getContentType(path));
            }
        } else {
            sendErrorResponse(400, "Bad Request", "400.html");
        }
    }
    
    private void sendFileResponse(String filePath, String contentType) {
        Path file = Paths.get(CONTENT_DIRECTORY, filePath);

        if (Files.exists(file) && !Files.isDirectory(file)) {
            sendSuccessResponse(contentType, file);
        } else {
            sendErrorResponse(404, "Not Found", "404.html");
        }
    }

    private void sendErrorResponse(int statusCode, String statusText, String errorPage) {
        this.statusCode = statusCode;
        Path path = Paths.get(ERROR_DIRECTORY, errorPage);
        getResponse("text/html", statusText, path);
    }

    private void sendSuccessResponse(String contentType, Path file) {
    	this.statusCode = 200;
        getResponse(contentType, "OK", file);
    }
    
    private void getResponse(String contentType, String statusText, Path file) {
    	try {
            String responseHeader = "HTTP/1.1 " + this.statusCode + " " + statusText + "\r\n";
            String serverHeader = "Server: " + SERVER_NAME + "\r\n";
            long contentLength = getContentLength(file);
            String contentLengthHeader = "Content-Length: " + contentLength + "\r\n";
            String dateHeader = "Date: " + getFormattedDate() + "\r\n";
            String contentTypeHeader = "Content-Type: " + contentType + "\r\n";

            output.writeBytes(responseHeader);
            output.writeBytes(serverHeader);
            output.writeBytes(contentLengthHeader);
            output.writeBytes(dateHeader);
            output.writeBytes(contentTypeHeader);
            output.writeBytes("\r\n");

            byte[] fileData = Files.readAllBytes(file);
            output.write(fileData, 0, fileData.length);

            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long getContentLength(Path file) {
        try {
            return Files.size(file);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getContentType(String filePath) {
        if (filePath.endsWith(".html")) {
            return "text/html";
        } else if (filePath.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }

    private String getFormattedDate() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/New_York"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        return formatter.format(now);
    }
    
    public int getStatusCode() {
        return statusCode;
    }
}