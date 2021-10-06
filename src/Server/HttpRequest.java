package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class HttpRequest extends Thread {
    private  Socket socket;
    private BufferedReader input;
    private PrintWriter output;
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
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
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
        String sentence, messageInput, messageOutput;
        try {
            sentence = input.readLine();
            while (sentence != null) {
                messageInput = "";
                if (sentence.equals("connectTest")) {
                    messageOutput = "connectServer";
                } else {
                    while (!sentence.contains("\\EOF")) {
                        messageInput = messageInput.concat(sentence + "\r\n");
                        sentence = input.readLine();
                    }

                    messageOutput = processData(messageInput.split("\n")).trim() + "\r\n\\EOF";
                }
                output.println(messageOutput);
                sentence = input.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void disconnect() throws IOException {
        output.close();
        input.close();
        socket.close();
        System.out.println("The server thread ["+getName()+"] has been disconnected.");
        System.out.println("The number of active  connections are: " + (Thread.activeCount() - 2));
        this.interrupt();
    }


    //handle stuff
    private String handlePost(String[] data) {

        StickyNote stickyNote = new StickyNote();
        String message;
        for (String s : data) {
            s = s.trim();
            String[] strings = s.split(" ");
            System.out.println("POST: " + strings[0] + " ");
            String value;
            switch (strings[0]) {
                case "COLOR":
                    value = s.substring(strings[0].length()).trim();
                    stickyNote.setColor(value);
                    break;
                case "X":
                    value = s.substring(strings[0].length()).trim();
                    stickyNote.setX(Integer.parseInt(value));
                    break;
                case "Y":
                    value = s.substring(strings[0].length()).trim();
                    stickyNote.setY(Integer.parseInt(value));
                    break;
                case "NAME":
                    value = s.substring(strings[0].length()).trim();
                    stickyNote.setName(value);
                    break;
                case "WIDTH":
                    value = s.substring(strings[0].length()).trim();
                    stickyNote.setWidth(Integer.parseInt(value));
                    break;
                case "HEIGHT":
                    value = s.substring(strings[0].length()).trim();
                    stickyNote.setHeight(Integer.parseInt(value));
                    break;
                case "MESSAGE":
                    value = s.substring(strings[0].length()).trim();
                    stickyNote.setMessage(value);
                    break;
                default:
                    break;
            }
        }
        stickyNoteEntries.add(stickyNote);
        message = "Inserted the notes successfully\n" + stickyNoteEntries.get(
                stickyNoteEntries.size() - 1);

        return message;
    }


    private String handleGet(String[] data) {
        StickyNote stickyNote = new StickyNote();
        String res="";
        for (String s : data) {

            s = s.trim();
            String[] words = s.split(" ");
            String value;
            System.out.println("GET: " + words[0] + " ");
            switch (words[0]) {
                case "COLOR":
                    value = s.substring(words[0].length()).trim();
                    stickyNote.setColor(value);
                    break;
                case "X":
                    value = s.substring(words[0].length()).trim();
                    stickyNote.setX(Integer.parseInt(value));
                    break;
                case "Y":
                    value = s.substring(words[0].length()).trim();
                    stickyNote.setY(Integer.parseInt(value));
                    break;

                case "NAME":
                    value = s.substring(words[0].length()).trim();
                    stickyNote.setName(value);
                    break;
                case "MESSAGE":
                    value = s.substring(words[0].length()).trim();
                    stickyNote.setMessage(value);
                    break;
                default:
                    break;
            }
        }

            if (stickyNoteEntries.size() == 0)
                return "No sticky note found.";

            if (stickyNote.getMessage().equals("PINS")){
                String val="";
                for (int i=0;i<pinnedNotes.size()-1;i+=2){
                    val+="x=" + pinnedNotes.get(i).toString() + " and y=" + pinnedNotes.get(i+1).toString() +".\n";

                }
                return  val;
        }
            else {

                for (StickyNote x : stickyNoteEntries) {
                    System.out.println(x.toString());

                    if (x.getColor().equals(stickyNote.getColor())) {
                        res = x.toString();
                    }
                    if (x.getX() == stickyNote.getX() && x.getY() == stickyNote.getY()) {
                        res = x.toString();
                    }
                    if (x.getName().equals(stickyNote.getName())) {
                        res = x.toString();
                    }
                    if (x.getName().equals(stickyNote.getName())) {
                        res = x.toString();
                    }


                }
            }

            if(res.length()!=0)return "Retrieved the note\n" + res;
            else  return "The Sticky note is not found";

    }
    private String handleClear() {
        stickyNoteEntries.clear();
        return "The Sticky note board has been cleared";
    }

    private String handlePin(String[]data) {
        StickyNote stickyNote = new StickyNote();
        for (String s : data) {
            s = s.trim();
            String[] strings = s.split(" ");
            String value;
            if (strings[0].equals("X")) {//x value will be set here
                System.out.println("PIN: " + strings[0] + " ");
                value = s.substring(strings[0].length()).trim();
                stickyNote.setX(Integer.parseInt(value));

            }
            if (strings[0].equals("Y"))  {//y value will be set here
                System.out.println("PIN: " + strings[0] + " ");
                value = s.substring(strings[0].length()).trim();
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
                return "The sticky note at x=" + stickyNote.getX() + " and y=" + stickyNote.getY() + " has been pinned";
            }
        }

        return "The sticky not does not exist at the coordinate provided " +stickyNote.getX() + " " + stickyNote.getY() ;
    }

    private String handleUnpin(String[]data) {
        boolean flag=false;
        StickyNote stickyNote = new StickyNote();
        for (String s : data) {
            s = s.trim();
            String[] strings = s.split(" ");
            String value;
            if (strings[0].equals("X")) {
                System.out.println("UNPIN: " + strings[0] + " ");
                value = s.substring(strings[0].length()).trim();
                stickyNote.setX(Integer.parseInt(value));

            }
            if (strings[0].equals("Y"))  {
                System.out.println("UNPIN: " + strings[0] + " ");
                value = s.substring(strings[0].length()).trim();
                stickyNote.setY(Integer.parseInt(value));

            }
        }
        for (int i = 0; i < pinnedNotes.size() - 1; i+=2) {//checks if coordinates exist and removes from pinlist
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
       String ret="";
        boolean flag;
        for (int i = 0; i < stickyNoteEntries.size(); i++) {
            flag=false;
            StickyNote curr=stickyNoteEntries.get(i);
            x=curr.getX();
            y=curr.getY();
            for (int j = 0; j < pinnedNotes.size() - 1; j+=2) {
                if (pinnedNotes.get(j) == x && pinnedNotes.get(j + 1) == y) {
                    flag = true;
                    break;
                }
            }
            if(flag==false){
                stickyNoteEntries.remove(i);
                ret+= "Unpinned note removed at x=" + x+ " and y=" + y + "\n";
                i-=1;

            }
            System.out.print("Dasdsadsa " + flag);
        }

        if(ret.equals("")) return "All notes are pinned";
        else return ret;
    }




}
