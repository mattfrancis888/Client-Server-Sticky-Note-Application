package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class HttpRequest extends Thread {
    private  Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private  ArrayList<StickyNote> stickyNoteEntries;

    public HttpRequest(String name, Socket socket, ArrayList<StickyNote> stickyNoteEntries) {
//        super(name);
        this.socket = socket;
        this.stickyNoteEntries = stickyNoteEntries;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            listen();
           disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String processData(String[] data) {
        //POST,GET,PIN,UNPIN,CLEAR,SHAKE,
        final String request = data[0].trim();
        switch (request) {
            case "POST":
                return handlePost(data);
            case "GET":
                return handleGet(data);
//            case "PIN":
//                return handlePin(data);
//            case "UNPIN":
//                return handleUnpin(data);
//            case "CLEAR":
//                return handleClear(data);
//            case "SHAKE":
//                return handleShake(data);
            default:
                return "ERROR - Request is not a POST, GET, PIN, UNPIN, CLEAR, SHAKE";
        }
    }

    private void listen() {
        String line, inMessage, outMessage;
        try {
            //Read everything line by line from the input
            line = in.readLine();

            while (line != null) {
                System.out.println("LISTEN VAL" + line);
                inMessage = "";
                if (line.equals("ping")) { //COME BACK
                    outMessage = "pong";
                } else {
                    /* READ DATA START */
                    while (!line.contains("\\EOF")) {
                        inMessage = inMessage.concat(line + "\r\n");
                        line = in.readLine();
                    }
                    /* READ DATA END */

                    /* PROCESS DATA START*/
                    outMessage = processData(inMessage.split("\n")).trim() + "\r\n\\EOF";
                }
                out.println(outMessage);
                line = in.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    //handle stuff
    private String handlePost(String[] data) {
        String message;
//        StickyNote stickyNote = new StickyNote();
//        for (String line : data) {
//            line = line.trim();
//            String[] words = line.split(" ");
//            String value;
//            switch (words[0]) {
//                case "COLOR":
//                    if (Util.findByISBN(bookEntries, words[1]) != null) {
//                        message = "ERROR: Book already exists";
//                        return message;
//                    }
//                    stickyNote.setColor(words[1]);
//                    break;
//                case "X":
//                    value = line.substring(words[0].length()).trim();
//                    stickyNote.setX(value);
//                    break;
//                case "Y":
//                    value = line.substring(words[0].length()).trim();
//                    stickyNote.setY(value);
//                    break;
//                case "NAME":
//                    value = line.substring(words[0].length()).trim();
//                    stickyNote.setName(value);
//                    break;
//                case "WIDTH":
//                    stickyNote.setWidth(Integer.parseInt(words[1]));
//                    break;
//                case "HEIGHT":
//                    stickyNote.setHeight(Integer.parseInt(words[1]));
//                    break;
//                default:
//                    break;
//            }
//        }
        message = "-----Successfully added-----\n";
//        bookEntries.add(bookEntry);
        return message;
    }


    private String handleGet(String[] data) {
        StringBuilder message = new StringBuilder();
        System.out.println("handleGet" + data);

        return message.toString();
    }

    private void disconnect() throws IOException {
        out.close();
        in.close();
        socket.close();
        System.out.println("Server thread [" + getName() + "] disconnected.");
        System.out.println("Active connections: " + (Thread.activeCount() - 2));
        this.interrupt();
    }

}
