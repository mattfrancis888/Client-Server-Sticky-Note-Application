package Server;
import java.io.*;
import java.net.*;
import java.util.*;



public class Server {

//    public static void main(String[] args) throws IOException {
//        // Get the port number from the command line.
//        int port;
//        if (args.length == 0) {
//            System.out.println("Defaulting to the port 5555");
//            port = 5555;
//        } else
//            port = Integer.parseInt(args[0]);
//
//        // Establish the listen socket.
//        ServerSocket socket = new ServerSocket(port);
//        System.out.println("Server is currently running on port " + port);
//        // Process HTTP service requests in an infinite loop.
//        // Add our object/data structure of notes here
//
//        while (true) {
//            // Listen for a TCP connection request.
//            Socket connection = socket.accept();
//
//            // Construct an object to process the HTTP request message.
//            HttpRequest request = new HttpRequest(connection);
//
//            // Create a new thread to process the request.
//            Thread thread = new Thread(request);
//
//            // Start the thread.
//            thread.start();
//        }
//    }
public static void main(String[] args) throws IOException {
    int port;
    if (args.length == 0) port = 3000;
    else port = Integer.parseInt(args[0]);

    ArrayList<StickyNote> stickyEntries = new ArrayList<StickyNote>();
    ServerSocket serverSocket = new ServerSocket(port);
    System.out.println("Server running on port " + port + ".");

    while (true) {
        Socket clientSocket = serverSocket.accept();
        HttpRequest serverThread = new HttpRequest(Thread.activeCount() + "",
                clientSocket, stickyEntries);
        serverThread.start();
        System.out.println("Connection detected, starting server thread [" + (Thread.activeCount() - 1) + "]");
        System.out.println("Active connections: " + (Thread.activeCount() - 1));
    }
}
}
