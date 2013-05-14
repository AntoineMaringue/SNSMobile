package fr.sciencesu.scannstockmobile.API;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



public class ScanneurBarreCode implements IScanneurBarreCode
{
	
	//private final String pathLibOcr = "\\TestApiGoogleSearch\\AspriseOCR.dll";
	private final String filePathImg = "C:\\Users\\Antoine\\Downloads\\Asprise-OCR-Java-Windows_XP_64bit-4.0\\sample-images\\ocr.gif";
	private String _codeEan;
	private Vector<?> _multiBarreCode;
	private Calendar _scanningDate;
	
	/**
	 * Constructeur de la classe barre code
	 */
	public ScanneurBarreCode()
	{
		_codeEan = "";
		_scanningDate = Calendar.getInstance();
		_multiBarreCode = new Vector();
	}
	
	public void runScanningDate()
	{
		// D�clarations des variables
		//BufferedImage image = null;
/*		OCR ocr = null;	
		ocr = new OCR();
		Bitmap tgtImg = BitmapFactory.decodeFile("ImageD2.jpg");
		
		//image = ImageIO.read(new File(filePathImg));	
		//String strDate = ocr.recognizeCharacters(image);			
		//_scanningDate.setTime(getDate(strDate));
		System.out.println("Date: \n"+ _scanningDate.getTime());*/
	}
	
	public Date getDate(String s)
	{ 
	       Date d = null; 
	       String nouveau = s.substring(6, 10)+"-"+s.substring(3, 5)+"-"+s.substring(0, 2); //annee, mois et jour 
	       try 
	       { 
	    	   d = new Date(nouveau); 
	       } 
	       catch (Exception ex) 
	       { 
	        
	       } 
	       return d; 
	    }

	public void runScan(boolean multiBc)
	{
            /*
		// D�clarations des variables
		//BufferedImage image = null;
		OCR ocr = null;
		//OCR.setLibraryPath(pathLibOcr);
		
		ocr = new OCR();
		//image = ImageIO.read(new File(filePathImg));	
		
		//image = image.getSubimage(0, 0, 200, 100);
		
		if(!multiBc)
		{
			//_codeEan = ocr.recognizeBarcode(image);			
		 
			System.out.println("RESULTS: \n"+ _codeEan);	
		}
		else
		{
			//_multiBarreCode = ocr.recognizeBarcodes(image);
		}
	*/}
	
	public String getCodeEAN()
	{
		return _codeEan;
	}
	
	public Date getScanningDate()
	{
		return _scanningDate.getTime();
	}
	
}
