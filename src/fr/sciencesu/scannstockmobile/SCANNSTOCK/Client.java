package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import fr.sciencesu.scannstockmobile.SCANNEUR.splashActivity;

import android.util.Log;

public class Client 
{

	public Client() 
	{
		buildParameter("localhost","42220");
	}
	
	public Client(String ip, String port) 
	{
		buildParameter(ip,port);
	}
	
	private void buildParameter(String host , String port) 
	{
		this.host = host;
		this.port = port;
	}

	private String host,port;
	
	public boolean sendToServer(String data)
	{
		
        InetAddress adds;//nom de ID du serveur
        Socket comm = null;
        
        DataInputStream dis = null;
        ObjectOutputStream oos = null;
        
        String isbn = data.split(";")[0];
        String dep = data.split(";")[1];

        try 
        {
            System.out.println("Demande de connexion ...");
            Log.e("TCP Client", "C: Connecting...");
            Log.e("HOST ", host+"...");
            Log.e("PORT ", port+"...");
            adds = InetAddress.getByName(host.toString());
            int p = Integer.parseInt(port);
            comm = new Socket(adds,p);
            System.out.println("Connexion établie");
            Log.e("TCP Client", "C: Connected Established...");
        } 
        catch (IOException io) 
        {
        	io.getMessage();
        }
        
        try 
        {
        	
        
            //on l'envoie au serveur
            DataOutputStream dos=new DataOutputStream(comm.getOutputStream());
            dos.writeChars(isbn+"\n");

            //Envoie au serveur
           // oos = new ObjectOutputStream(comm.getOutputStream());
           // oos.writeObject(isbn);
            
            //Recois du serveur                
            //dis = new DataInputStream(comm.getInputStream());
           // System.out.println("-->" + dis.readDouble());
                  
        } 
        catch (IOException io) {
            System.exit(1);
        }
        
        return true;
        }

}
