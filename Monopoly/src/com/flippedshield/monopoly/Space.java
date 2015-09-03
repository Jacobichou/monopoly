package com.flippedshield.monopoly;

public abstract class Space {
	protected String name;
	
	public Space(String name){
		setName(name); 
	}
	
	public abstract void onLanding();
	public abstract void onLeaving();
	
	public void setName(String name) { this.name = name; } 
	public String getName() { return name; }

}
