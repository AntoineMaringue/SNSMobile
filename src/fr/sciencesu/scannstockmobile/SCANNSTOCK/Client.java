/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Antoine
 */
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

    public Client(String IP, int PORT, String data) {
        this.host = IP;
        this.ISBN = data;
        lst = new ArrayList();
        Connexion();
    }

    /*
     * Ouverture du socket et des flux
     */
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

        /*
        
         */
        if (clientSocket != null && os != null && inputLine != null) {
            try {
                Client cli = new Client();
                new Thread(cli).start();
                while (!closed) {
                    os.println(ISBN + "/quit\n");

                }


                responseLine = cli.getResponseLine();

                /*
                 * Fermeture de la socket ainsi que des flux
                 */
                os.close();
                inputLine.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }

    /*
     * Create a thread to read from the server. (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        /*
         * Keep on reading from the socket till we receive "Bye" from the
         * server. Once we received that then we want to break.
         */
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
}
