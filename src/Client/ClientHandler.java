package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientHandler {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String address, int port) throws IOException {
        clientSocket = new Socket();
        System.out.println(address + ' ' + port);
        try {
            clientSocket.connect(new InetSocketAddress(address, port), 5000);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new IOException("ERROR: Connection refused please check IP Address and Port and try again");
        }
    }

    public boolean isConnected() {
        try {
            out.println("ping");
            return in.readLine().equals("pong");
        } catch (NullPointerException | IOException e) {
            return false;
        }
    }

    private String processRequest(Request request, String color) {
        String requestData = request.name() + "\r\n";

            requestData += "COLOR " + color;

        return requestData;
    }

    public String sendMessage(Request request, String color) throws IOException {
        String requestData = processRequest(request, color);
//        System.out.println("sendMessage: "  + requestData + "color is: " + color);
        System.out.println("sendMessage: "  + requestData);
       out.println(requestData + "\r\n\\EOF");
        out.println("test" + "\r\n\\EOF");
        String response = "";
        String line = in.readLine();
        while (line != null && !line.contains("\\EOF")) {
            response = response.concat(line + "\r\n");
            line = in.readLine();
        }
        return response;
    }
}
