package fr.sciencesu.scannstockmobile.DAO;

import java.util.ArrayList;

public final class Base implements IBase
{

	private static IBase instance;
	
	private Base()
	{
		
	}
	
	public static IBase getInstance()
	{
		synchronized (Base.class) 
		{
			if(instance == null)
			{
				instance = new Base();
			}
		}
		
		return instance;
	}
	
	@Override
	public boolean connection() 
	{
		
		return true;
	}

	@Override
	public boolean insertInto(String table, ArrayList<String> values) 
	{
		
		return false;
	}

	@Override
	public void deconnection() 
	{
		
		
	}

	@Override
	public void selectVisual(String name) 
	{
		
		
	}

	@Override
	public void selectDesc(String name) 
	{
		
		
	}

	@Override
	public boolean connection(String id, String mdp) 
	{
		
		return true;
	}

}
