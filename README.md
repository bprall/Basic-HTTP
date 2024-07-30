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
        
        In another terminal window enter paste this:
        ```bash
        telnet localhost 80
        GET / HTTP/1.1
        Host: localhost
        ```
    
    - **Using a Web Browser:**
    
        Open your browser and go to `http://localhost/` or `http://localhost/test.html` to test the server responses.

# Notes
- Ensure that the content and errors directories contain the necessary HTML files.
- Use Wireshark to monitor network traffic and verify server responses if needed.