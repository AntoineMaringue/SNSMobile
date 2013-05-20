package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import java.util.ArrayList;
import fr.sciencesu.scannstockmobile.API.ShoppingApi;



public class ScanNStock 
{
	public static String __IP = "192.168.1.86" , __PORT = "5000";
	/*private ScanneurBarreCode _scanner;
	private IBase _basePersoScanNStock;
	private ShoppingApi _shop;
	private String _productName;
	private ArrayList<String> _values;
	private String _tableName;
	
	public ScanNStock()
	{
		_scanner = new ScanneurBarreCode();
		_shop = new ShoppingApi();		
		_basePersoScanNStock = Base.getInstance();
	}
	
	public ScanNStock(String nameProductSearch)
	{
		_scanner = new ScanneurBarreCode();
		_shop = new ShoppingApi();	
		_basePersoScanNStock = Base.getInstance();
		_productName = nameProductSearch;
	}

	@Override
	public void takePhoto() 
	{
		//Fonction Andro�d
	}

	@Override
	public void scanBarCode() 
	{
		_scanner.runScan(false);
	}

	@Override
	public void getInfosProduct() 
	{
		String ean = _scanner.getCodeEAN();
		_shop.runSearchProduct(ean,_productName);
	}

	@Override
	public void InsertToBase() 
	{
		_basePersoScanNStock.connection();
		_basePersoScanNStock.insertInto(_tableName, _values);
		_basePersoScanNStock.deconnection();
	}

	public void launch() 
	{
		takePhoto();
		scanBarCode();
		getInfosProduct();
		InsertToBase();
	}*/
}
