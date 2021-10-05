package Server;
import java.io.*;
import java.net.*;
import java.util.*;



public class Server {

public static void main(String[] args) throws IOException {
    int port=3000;
    if (args.length> 0) port = Integer.parseInt(args[0]);

    ArrayList<StickyNote> stickyEntries = new ArrayList<StickyNote>();
    ArrayList<Integer>pinnedNotes=new ArrayList<Integer>();
    ServerSocket socketServer = new ServerSocket(port);
    System.out.println("The server is running on port " + port + ".");

    while (true) {
        Socket socketClient = socketServer.accept();
        HttpRequest serverThread = new HttpRequest(Thread.activeCount() + "",
                socketClient, stickyEntries,pinnedNotes);
        serverThread.start();
        System.out.println("Connection is detected, the server is starting at thread [" + (Thread.activeCount() - 1) + "]");
        System.out.println("The number of active connections are: " + (Thread.activeCount() - 1));
    }
}
}
