package Server;
import java.io.*;
import java.net.*;
import java.util.*;



public class Server {

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
