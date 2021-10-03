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
    private ArrayList<Integer>pinnedNotes;


    public HttpRequest(String name, Socket socket, ArrayList<StickyNote> stickyNoteEntries, ArrayList<Integer>pinnedNotes) {
       super(name);
        this.socket = socket;
        this.stickyNoteEntries = stickyNoteEntries;
        this.pinnedNotes=pinnedNotes;
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
            case "PIN":
                return handlePin(data);
            case "UNPIN":
                return handleUnpin(data);
            case "CLEAR":
                return handleClear();
            case "SHAKE":
                return handleShake(data);
            default:
                return "ERROR - Request is not a POST, GET, PIN, UNPIN, CLEAR, SHAKE";
        }
    }

    private void listen() {
        String line, inMessage, outMessage;
        try {
            line = in.readLine();
            while (line != null) {
                inMessage = "";
                if (line.equals("ping")) {
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
        StickyNote stickyNote = new StickyNote();
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value;
            System.out.println("HANDLE POST: " + words[0] + " ");
            switch (words[0]) {
                case "COLOR":
                    value = line.substring(words[0].length()).trim();
                    System.out.println("SET "  + value);
                    stickyNote.setColor(value);
                    break;
                case "X":
                    value = line.substring(words[0].length()).trim();
                    stickyNote.setX(1);
                    break;
                case "Y":
                    value = line.substring(words[0].length()).trim();
                    stickyNote.setY(10);
                    break;
                case "NAME":
                    value = line.substring(words[0].length()).trim();
                    stickyNote.setName("matt");
                    break;
                case "WIDTH":
//                    stickyNote.setWidth(Integer.parseInt(words[1]));
                    stickyNote.setWidth(100);
                    break;
                case "HEIGHT":
//                    stickyNote.setHeight(Integer.parseInt(words[1]));
                    stickyNote.setHeight(200);
                    break;
                default:
                    break;
            }
        }
        stickyNoteEntries.add(stickyNote);
        message = "-----Successfully added-----\n" + stickyNoteEntries.get(
                stickyNoteEntries.size() - 1);

        return message;
    }


    private String handleGet(String[] data) {
        StringBuilder message = new StringBuilder();

        for (String line : data) {

            line = line.trim();
            String[] words = line.split(" ");
            String value = line.substring(words[0].length()).trim();
            System.out.println("HANDLE GET: " + words[0] + " ");

            if (stickyNoteEntries.size() == 0)
                return "No colors found.";
            for (StickyNote stickyNote : stickyNoteEntries) {
                message.append("_____________Retrieved_______________\n");
                message.append(stickyNote.toString());
                message.append("\r\n");
            }

            return message.toString();

        }

        return message.toString();
    }
    private String handleClear() {
        stickyNoteEntries.clear();
        return "The Sticky note board has been cleared";
    }

    private String handlePin(String[]data) {

        int x = 1;//change to 0 once we stop hardcode
        int y = 10;//change to 0 once we stop hardcode
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value;
            if (words[0] == "X") {//x value will be set here
                System.out.println("HANDLE PIN: " + words[0] + " ");
                value = line.substring(words[0].length()).trim();
            }
            if (words[0] == "Y") {//y value will be set here
                System.out.println("HANDLE PIN: " + words[0] + " ");
                value = line.substring(words[0].length()).trim();
                break;
            }
        }
        int xt = 0;
        int yt = 0;
        for (int i = 0; i < stickyNoteEntries.size(); i++) {//checks if the coordinates exist and then pins
            xt = stickyNoteEntries.get(i).getX();
            yt = stickyNoteEntries.get(i).getY();
            if (xt == x && yt == y) {
                pinnedNotes.add(x);
                pinnedNotes.add(y);
                return "The sticky note at x=" + x + " and y=" + y + " has been pinned";
            }
        }

        return "The sticky not does not exist at the coordinate provided";
    }

    private String handleUnpin(String[]data) {
        boolean flag=false;
        int x = 1;//change to 0 once we stop hardcode
        int y = 10;//change to 0 once we stop hardcode
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value;
            if (words[0] == "X") {
                System.out.println("HANDLE PIN: " + words[0] + " ");
                value = line.substring(words[0].length()).trim();
            }
            if (words[0] == "Y") {
                System.out.println("HANDLE PIN: " + words[0] + " ");
                value = line.substring(words[0].length()).trim();
                break;
            }
        }
        for (int i = 0; i < pinnedNotes.size() - 1; i++) {//checks if coordinates exist and removes from pinlist
            if (pinnedNotes.get(i) == x && pinnedNotes.get(i + 1) == y) {
                pinnedNotes.remove(i + 1);
                pinnedNotes.remove(i);
                flag=true;
                break;
            }
        }
        if(flag)return "The sticky note at x=" + x + " and y=" + y + " has been unpinned";
        else return "The sticky note for unpinning does not exist";
    }

    private String handleShake(String[]data) {
        int x=0;
        int y=0;
        boolean flag=false;
        for (int i = 0; i < stickyNoteEntries.size(); i++) {
            flag=false;
            StickyNote curr=stickyNoteEntries.get(i);
            x=curr.getX();
            y=curr.getY();
            for (int j = 0; j < pinnedNotes.size() - 1; j++) {
                if (pinnedNotes.get(j) == x && pinnedNotes.get(j + 1) == y) {
                    flag = true;
                }
            }
            if(!flag){
                stickyNoteEntries.remove(i);
                return "Unpinned note removed at x=" + x+ " and y= " + y;
            }
        }
        return "No more unpinned notes left";
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
