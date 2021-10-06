package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientHelper {
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
            throw new IOException("Failed to connect to server. Verify your IP Address and Port. Try again");
        }
    }
    public void disconnect() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public boolean isConnected() {
        try {
            out.println("connectTest");
            return in.readLine().equals("connectServer");
        } catch (NullPointerException | IOException e) {
            return false;
        }
    }
    private String handleRequest(Request request, String color, int x, int y, String name, int width, int height, String message) {
        String requestData = request.name() + "\r\n";

        requestData += "COLOR " + color +  "\r\n" + "X " + x +  "\r\n" + "Y " + y + "\r\n" +
                "NAME " + name + "\r\n" + "WIDTH " + width +  "\r\n" + "HEIGHT " + height + "\r\n" + "MESSAGE " + message + "\r\n";

        return requestData;
    }

    public String sendData(Request request, String color, int x, int y, String name, int width, int height,String message)  throws IOException {

        String requestData = handleRequest(request, color, x,y,name,width,height, message);
        System.out.println("color is: " + color);
        System.out.println("sendData: "  + requestData);
       out.println(requestData + "\r\n\\EOF");

        String response = "";
        String line = in.readLine();
        while (line != null && !line.contains("\\EOF")) {
            response = response.concat(line + "\r\n");
            line = in.readLine();
        }
        return response;
    }
}
