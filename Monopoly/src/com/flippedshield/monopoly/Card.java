package com.flippedshield.monopoly;

import org.json.simple.JSONObject;

public class Card {
	
	private String name;
	
	public Card(JSONObject obj)
	{
		parseJsonObject(obj);
	}
	
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
	
	private void parseJsonObject(JSONObject card)
	{
		String cardName = (String) card.get("name"); 
		setName(cardName); 
		// setName(card.get("name").toString());
	}
}
