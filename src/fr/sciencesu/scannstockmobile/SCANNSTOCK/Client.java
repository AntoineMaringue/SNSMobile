/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;


/**
 *
 * @author Antoine
 */
/*
 public final class Client implements Runnable {

 // The client socket
 private static Socket clientSocket = null;
 // The output stream
 private static PrintStream os = null;
 // The input stream
 //private static DataInputStream is = null;
 private static final int PortNumber = 5000;
 private static BufferedReader inputLine = null;
 private static boolean closed = false;
 private String ISBN = "";
 // The default host.
 private String host;
 private String responseLine = "";
 private ArrayList<String> lst;

 public Client() {
 lst = new ArrayList();
 }

 public String getResponseLine() {
 return responseLine;
 }
    
 public Client(String IP, int PORT) {
 this.host = IP;
 lst = new ArrayList();
        
 }

 public Client(String IP, int PORT, String data) {
 this.host = IP;
 this.ISBN = data;
 lst = new ArrayList();
 Connexion();
 }


 public void Connexion() {

 try {
 clientSocket = new Socket(host, PortNumber);
 //inputLine = new BufferedReader(new InputStreamReader(System.in));
 os = new PrintStream(clientSocket.getOutputStream());
 // is = new DataInputStream(clientSocket.getInputStream());           
 inputLine = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 } catch (UnknownHostException e) {
 System.err.println("Impossible d'identifier l'host " + host);
 } catch (IOException e) {
 System.err.println("Entrée/Sortie indisponible pour vous connecter à l'host "
 + host);
 }

     
 if (clientSocket != null && os != null && inputLine != null) {
 try {
 Client cli = new Client();
 new Thread(cli).start();
 while (!closed) {
 os.println(ISBN + "/quit\n");

 }


 responseLine = cli.getResponseLine();

          
 os.close();
 inputLine.close();
 clientSocket.close();
 } catch (IOException e) {
 System.err.println("IOException:  " + e);
 }
 }
 }
    
 public void sendData(String data)
 {
 try {
 clientSocket = new Socket(host, PortNumber);
 //inputLine = new BufferedReader(new InputStreamReader(System.in));
 os = new PrintStream(clientSocket.getOutputStream());
 // is = new DataInputStream(clientSocket.getInputStream());           
 inputLine = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 } catch (UnknownHostException e) {
 System.err.println("Impossible d'identifier l'host " + host);
 } catch (IOException e) {
 System.err.println("Entrée/Sortie indisponible pour vous connecter à l'host "
 + host);
 }

  
 if (clientSocket != null && os != null && inputLine != null) {
 try {
 Client cli = new Client();
 new Thread(cli).start();
 while (!closed) {
 os.println(data + "/quit\n");

 }


 responseLine = cli.getResponseLine();

 os.close();
 inputLine.close();
 clientSocket.close();
 } catch (IOException e) {
 System.err.println("IOException:  " + e);
 }
 }
 }

 public void run() {

 getDataServer();
 }

 private void getDataServer() {
 try {
 String s;

 while ((s = inputLine.readLine()) != null) {
 System.out.println(s);
 lst.add(s);
 responseLine += s + "\n";
 if (s.indexOf("*** Fin") != -1) {
 break;
 }
 }
 closed = true;
 System.out.println("CLOSED = TRUE");
 } catch (IOException e) {
 System.err.println("IOException:  " + e);
 }
 }
 }*/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


/*import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.io.PrintStream;
 import java.net.InetAddress;
 import java.net.Socket;
 import java.net.UnknownHostException;
 import java.util.ArrayList;*/
/**
 *
 * @author Antoine
 */
/*public final class Client implements Runnable {

 // The client socket
 private static Socket clientSocket = null;
 // The output stream
 private static PrintStream os = null;
 // The input stream
 //private static DataInputStream is = null;
 private static final int PortNumber = 5000;
 private static BufferedReader inputLine = null;
 private static boolean closed = false;
 private String ISBN = "";
 // The default host.
 private String host;
 private String responseLine = "";
 private ArrayList<String> lst;

 public Client() {
 lst = new ArrayList();
 }

 public String getResponseLine() {
 return responseLine;
 }

 public Client(String IP, int PORT, String data) {
 this.host = IP;
 this.ISBN = data;
 lst = new ArrayList();
 Connexion();
 }

    
 public void Connexion() {

 try {
 InetAddress serverAddr = InetAddress.getByName(host);
 clientSocket = new Socket(host, PortNumber);
 //inputLine = new BufferedReader(new InputStreamReader(System.in));
 os = new PrintStream(clientSocket.getOutputStream());
 // is = new DataInputStream(clientSocket.getInputStream());           
 inputLine = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 } catch (UnknownHostException e) {
 System.err.println("Impossible d'identifier l'host " + host);
 } catch (IOException e) {
 System.err.println("Entrée/Sortie indisponible pour vous connecter à l'host "
 + host);
 }

        
 if (clientSocket != null && os != null && inputLine != null) {
 try {
 Client cli = new Client();
 new Thread(cli).start();
 while (!closed) {
 os.println(ISBN + "/quit\n");

 }


 responseLine = cli.getResponseLine();

               
 os.close();
 inputLine.close();
 clientSocket.close();
 } catch (IOException e) {
 System.err.println("IOException:  " + e);
 }
 }
 }

    
 public void run() {
        
 getDataServer();
 }

 private void getDataServer() {
 try {
 String s;

 while ((s = inputLine.readLine()) != null) {
 System.out.println(s);
 lst.add(s);
 responseLine += s + "\n";
 if (s.indexOf("*** Fin") != -1) {
 break;
 }
 }
 closed = true;
 } catch (IOException e) {
 System.err.println("IOException:  " + e);
 }
 }
 }*/
public class Client implements Runnable {

    private Socket requestSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String message;
    private PipedOutputStream output = new PipedOutputStream();
    public PrintWriter writer = new PrintWriter(output);
    public static String traitement = "";
    private String ip;
    private int port;
    public String data = "";
    public String response = "";
    private String id, mdp;
    private String isbn;
    private ArrayList<String> associations;

    public Client(String IP, int Port) throws IOException {
        ip = IP;
        port = Port;
        associations  = new ArrayList();
    }

    public void run() {
        try {

            //1. creating a socket to connect to the server
            requestSocket = new Socket(ip, port);
            System.out.println("Connected to " + ip + " in port " + port + "");
            //2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());

            //3: Communicating with the server
            do {
                try {
                    //sendMessage("Hi my server");
                    ;
                    message = (String) in.readObject();
                    if (!message.equals("") && !message.equals("_")) {
                        setResponseLine(message);
                    }
                    
                    if (!message.equals("") && !message.equals("_") && message.contains("ASSS")) {
                        setAssociations(message.split(",")[1]);
                    }
                    if (!message.equals("_")) {
                        System.out.println("server>" + message);
                        System.out.println("traitement :");

                    }
                    if (sendEvent()) {

                        switch (data.charAt(0)) {
                            case 'e': {
                                message = "bye";
                                sendMessage(message);
                                setEvent(false);
                                break;
                            }
                            case '1': {

                                message = "Validation;" + getId() + getMdp();
                                sendMessage(message);
                                setEvent(false);
                                break;
                            }
                            case '2': {

                                message = "Recupération de la liste des associations,";
                                sendMessage(message);
                                setEvent(false);
                                break;
                            }
                            case '3': {

                                message = "isbn,"+getISBN();
                                sendMessage(message);
                                setEvent(false);
                                break;
                            }
                        }

                    } else {
                        sendMessage("_");
                    }



                } catch (Exception classNot) {
                    System.err.println("data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (Exception unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } finally {
            //4: Closing connection
            try {
                in.close();
                out.close();
                requestSocket.close();
                System.exit(0);
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void sendMessage(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        } catch (Exception ioException) {
            ioException.printStackTrace();
        }
    }
    private boolean event = false;

    public boolean sendEvent() {
        return event;
    }

    public void setEvent(boolean e) {
        event = e;
    }

    public String getResponseLine() {
        return response;
    }

    void setResponseLine(String response) {
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String Mdp) {
        this.mdp = Mdp;
    }

    public void setISBN(String bn) {
        isbn = bn;
    }
    
    public String getISBN() {
        return this.isbn;
    }

    private void setAssociations(String string) {
        for (String association : string.split(";")) {
            associations.add(association);
        }
    }
    
    public ArrayList<String> getAssociations() {
        return associations;
    }
}
