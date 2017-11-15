package com.lofisoftware.thirdplanet.messages;

import com.lofisoftware.thirdplanet.entities.Entity;
import com.lofisoftware.thirdplanet.universe.Direction;
import com.lofisoftware.thirdplanet.universe.Universe;

public abstract class Message {
	public Universe universe;

	private String text;
	public String text(){ return text; }

	public Message(Universe universe, String text){
		this.universe = universe;
		this.text = text;
	}

	abstract public boolean involves(Entity entity);

	public static String addArticle(String article, String word){
		if (Character.isUpperCase(word.charAt(0)))
			return word;
		return article + " " + word;
	}
	
	public static String aOrAn(String word){
		String firstLetter = word.substring(0,1);
		if (firstLetter.equalsIgnoreCase("a") || firstLetter.equalsIgnoreCase("e") || 
				firstLetter.equalsIgnoreCase("i") || firstLetter.equalsIgnoreCase("o") ||
				firstLetter.equalsIgnoreCase("u"))
			return "an " + word;
		
		return "a " + word;
	}
	
	public static String wereWas(String word){
		
		if (word.equalsIgnoreCase("You"))
			return word + " were ";
		
		return word + " was ";
	}
	
	public static String getQuadrant(int direction) {
		switch (direction) {
		case Direction.NORTH:
			return "northeast";
		case Direction.EAST:
			return "southeast";
		case Direction.SOUTH:
			return "southwest";
		case Direction.WEST:
			return "northwest";					
		}
		return "";
	}
}
