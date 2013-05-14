package fr.sciencesu.scannstockmobile.API;

import java.util.ArrayList;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.saj.InvalidSyntaxException;



public class ParseurJSON implements IParseur {

	public ParseurJSON() 
	{
		
	}

	@Override
	public String getInformations(String requestHttpForGoogle) 
	{
		String test ="";
		java.util.List<JsonNode> items;
		try 
		{
			items = new JdomParser().parse(requestHttpForGoogle)
				    .getArrayNode("items");//.getStringValue("singles", 1);
			
			for (JsonNode jsonNode : items) 
			{
				System.out.println(jsonNode.getElements().iterator().next().toString());
			}
		} 
		catch (InvalidSyntaxException e) 
		{
			
			e.printStackTrace();
		}
		
		
		return test;
	}
}
