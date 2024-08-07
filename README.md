# Basic-HTTP

This project provides a basic HTTP server implementation in Java. It responds to HTTP requests with appropriate content or error messages.

## Functions

- **Handle HTTP Requests**: The server processes incoming HTTP requests and sends the appropriate response.
- **Serve Files**: If a valid file is requested, it is served from the `content` directory. If the file does not exist, a 404 error is returned.
- **Error Handling**: Invalid requests return a 400 Bad Request error, and non-existent paths return a 404 Not Found error.
- **Persistent Connections**: The server can keep connections open for multiple requests unless an error occurs.

## Getting Started

1. **Clone the Repository:**
   ```bash 
   git clone <repository_url> 
   cd <repository_directory>
   ```

2. **Build the Server:**
    ```bash
    Copy code
    make
    ```

3. **Run the Server:**
    ```bash
    Copy code
    sudo java http.HTTPServer
    ```
4. **Testing:**

    - **Using Telnet:**
        
        In another terminal window paste this:
        ```bash
        telnet localhost 80
        GET / HTTP/1.1
        Host: localhost
        ```
    
    - **Using a Web Browser:**
    
        Open your browser and go to `http://localhost/` or `http://localhost/test.html` to test the server responses.

## Files

- **`HTTPConnection.java`**: Manages a single client connection. Handles HTTP request processing and sends appropriate responses. Runs in a separate thread for each connection.

- **`HTTPRequest.java`**: Represents an HTTP request message. Parses and validates request lines and headers. Determines if the request is valid and retrieves request details like the path and host header.

- **`HTTPResponse.java`**: Constructs and sends HTTP responses. Handles serving files from the `content` directory, or sends error pages if files are not found. Includes methods for creating success and error responses, setting headers, and formatting dates.

- **`HTTPServer.java`**: Initializes and runs the HTTP server. Listens for incoming connections on port 80, and uses a thread pool to handle multiple client connections concurrently.


# Notes
- Ensure that the content and errors directories contain the necessary HTML files.
- Use Wireshark to monitor network traffic and verify server responses if needed.
- You can replace the html files in content to display your own locally.
