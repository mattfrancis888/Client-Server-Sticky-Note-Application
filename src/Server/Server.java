import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String[] args) throws IOException {
        // Get the port number from the command line.
        int port;
        if (args.length == 0) {
            System.out.println("Defaulting to the port 5555");
            port = 5555;
        } else {
            try {
                port = new Integer(argv[0]).intValue();
            } catch (Exception e) {
                System.out.println("Invalid port format, defaulting to the 5555");
                port = 5555;
            }
        }

        // Establish the listen socket.
        ServerSocket socket = new ServerSocket(port);
        System.out.println("Server is currently running on port " + port);
        // Process HTTP service requests in an infinite loop.
        // Add our object/data structure of notes here

        while (true) {
            // Listen for a TCP connection request.
            Socket connection = socket.accept();

            // Construct an object to process the HTTP request message.
            HttpRequest request = new HttpRequest(connection);

            // Create a new thread to process the request.
            Thread thread = new Thread(request);

            // Start the thread.
            thread.start();
        }
    }
}
