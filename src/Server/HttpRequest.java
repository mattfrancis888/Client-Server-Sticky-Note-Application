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
                    stickyNote.setX(Integer.parseInt(value));
                    break;
                case "Y":
                    value = line.substring(words[0].length()).trim();
                    stickyNote.setY(Integer.parseInt(value));
                    break;
                case "NAME":
                    value = line.substring(words[0].length()).trim();
                    stickyNote.setName(value);
                    break;
                case "WIDTH":
                   value = line.substring(words[0].length()).trim();
                    stickyNote.setWidth(Integer.parseInt(value));
                    break;
                case "HEIGHT":
                 value = line.substring(words[0].length()).trim();
                    stickyNote.setHeight(Integer.parseInt(value));
                    break;
                case "MESSAGE":
                    value = line.substring(words[0].length()).trim();
                    stickyNote.setMessage(value);
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
        StickyNote stickyNote = new StickyNote();
        String res="";
        for (String line : data) {

            line = line.trim();
            String[] words = line.split(" ");
            String value;
            System.out.println("HANDLE GET: " + words[0] + " ");
            switch (words[0]) {
                case "COLOR":
                    value = line.substring(words[0].length()).trim();
                    stickyNote.setColor(value);
                    break;
                case "X":
                    value = line.substring(words[0].length()).trim();
                    stickyNote.setX(Integer.parseInt(value));
                    break;
                case "Y":
                    value = line.substring(words[0].length()).trim();
                    stickyNote.setY(Integer.parseInt(value));
                    break;
                case "NAME":
                    value = line.substring(words[0].length()).trim();
                    stickyNote.setName(value);
                    break;
                default:
                    break;
            }
        }

            if (stickyNoteEntries.size() == 0)
                return "No sticky note found.";
            for (StickyNote x : stickyNoteEntries) {
                message.append("_____________Retrieved_______________\n");
                System.out.println(x.toString());
                if(x.getColor().equals(stickyNote.getColor())){
                    res= x.toString();
                }
                if(x.getX()==stickyNote.getX() && x.getY()==stickyNote.getY()){
                    res= x.toString();
                }
                if(x.getName().equals(stickyNote.getName())){
                    res= x.toString();
                }
                if(x.getName().equals(stickyNote.getName())){
                    res= x.toString();
                }

            }
            if(res.length()!=0)return res;
            else  return "The Sticky note is not found";
    }
    private String handleClear() {
        stickyNoteEntries.clear();
        return "The Sticky note board has been cleared";
    }

    private String handlePin(String[]data) {
        StickyNote stickyNote = new StickyNote();
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value;
            if (words[0].equals("X")) {//x value will be set here
                System.out.println("HANDLE PIN: " + words[0] + " ");
                value = line.substring(words[0].length()).trim();
                stickyNote.setX(Integer.parseInt(value));

            }
            if (words[0].equals("Y"))  {//y value will be set here
                System.out.println("HANDLE PIN: " + words[0] + " ");
                value = line.substring(words[0].length()).trim();
                stickyNote.setY(Integer.parseInt(value));

            }
        }
        int xt = 0;
        int yt = 0;
        for (int i = 0; i < stickyNoteEntries.size(); i++) {//checks if the coordinates exist and then pins
            xt = stickyNoteEntries.get(i).getX();
            yt = stickyNoteEntries.get(i).getY();
            if (xt == stickyNote.getX() && yt == stickyNote.getY()) {
                pinnedNotes.add(stickyNote.getX());
                pinnedNotes.add(stickyNote.getY());
                return "The sticky note at x= " + stickyNote.getX() + " and y= " + stickyNote.getY() + " has been pinned";
            }
        }

        return "The sticky not does not exist at the coordinate provided " +stickyNote.getX() + " " + stickyNote.getY() ;
    }

    private String handleUnpin(String[]data) {
        boolean flag=false;
        StickyNote stickyNote = new StickyNote();
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value;
            if (words[0].equals("X")) {
                System.out.println("HANDLE UNPIN: " + words[0] + " ");
                value = line.substring(words[0].length()).trim();
                stickyNote.setX(Integer.parseInt(value));

            }
            if (words[0].equals("Y"))  {
                System.out.println("HANDLE UNPIN: " + words[0] + " ");
                value = line.substring(words[0].length()).trim();
                stickyNote.setY(Integer.parseInt(value));

            }
        }
        for (int i = 0; i < pinnedNotes.size() - 1; i++) {//checks if coordinates exist and removes from pinlist
            if (pinnedNotes.get(i) == stickyNote.getX() && pinnedNotes.get(i + 1) == stickyNote.getY()) {
                pinnedNotes.remove(i + 1);
                pinnedNotes.remove(i);
                flag=true;
                break;
            }
        }
        if(flag)return "The sticky note at x=" + stickyNote.getX() + " and y=" + stickyNote.getY() + " has been unpinned";
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
