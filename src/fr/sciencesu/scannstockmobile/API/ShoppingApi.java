package fr.sciencesu.scannstockmobile.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ShoppingApi 
{
	
	private final static String googleUri="https://www.googleapis.com/shopping/search/v1/public/products?key=";
	private final static String keyGoogle = "AIzaSyAc7_OzUpgYJ2tg0_XLRY2FtTpbGpL1yIU";
	private final static String countryName = "FR";
	
	private String informations;
	
	public ShoppingApi()
	{
		informations = "";
	}	
	
	public void runSearchProduct(String ean, String nameProduct)
	{
		IParseur parseur = new ParseurJSON();
		informations = parseur.getInformations(requestHttpForGoogle(ean,nameProduct,"JSON"));
	}
	
	public String getInformations()
	{
		return informations;
	}
	
	private String requestHttpForGoogle(String EAN,String nameProduct,String format)
	{
		HttpURLConnection connection = null;  
		BufferedReader serverResponse = null;  
		
		String replyServer = "";
		
		String url = googleUri
				+keyGoogle
				+"&country="+countryName
				+"&gin="+EAN
				+"&q="+nameProduct
				+"&alt="+format;
  
		try  
		{  
			
			//OPEN CONNECTION  
			connection = ( HttpURLConnection ) 
					new URL( url ).openConnection();  
			  
			//SET REQUEST INFO  
			//connection.setRequestMethod( "POST" );  
			//connection.setDoOutput( true );  
			  
			//CREATE A WRITER FOR OUTPUT  
			//outWriter = new PrintWriter( connection.getOutputStream() );  
			  
			//PARAMETERS  
			/*
			buff.append( "param1=" );   
			buff.append( URLEncoder.encode( "Param 1 Value", "UTF-8" ) );  
			buff.append( "&" );  
			buff.append( "param2=" );   
			buff.append( URLEncoder.encode( "Param 2 Value", "UTF-8" ) );  
			  
			//SEND PARAMETERS  
			outWriter.println( buff.toString() );  
			outWriter.flush();  
			outWriter.close();  */
			  
			//RESPONSE STREAM  
			serverResponse = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );  
			  
			//READ THE RESPOSNE  
			String line;  
			while ( (line = serverResponse.readLine() ) != null )   
			{  
				replyServer += line+ "\n";  
			}  
			System.out.println(replyServer);
		}  
		catch (MalformedURLException mue)  
		{  
			mue.printStackTrace();  
		}  
		
		catch (IOException ioe)  
		{  
			ioe.printStackTrace();  
		}  
		finally  
		{  
			if ( connection != null )  
				connection.disconnect();  
			  
			if ( serverResponse != null )  
			{  
				try 
				{ 
					serverResponse.close(); 
				}
				catch (Exception ex) {}  
			}  
		}  	
		return replyServer;
	}

}
