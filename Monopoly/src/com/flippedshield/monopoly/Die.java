package com.flippedshield.monopoly;

import java.util.Random;

public class Die {
	private int numberOfSides;
	
	public Die(){
		setNumberOfSides(6); 
	}
	
	/**
	 * Die must have at least 2 sides. 2 sided die can act as a coinflip. 
	 */
	public Die(int numberOfSides) throws IndexOutOfBoundsException{
		if (numberOfSides > 1){
			setNumberOfSides(numberOfSides); 
		} else throw new IndexOutOfBoundsException("A die must have at least 2 sides. This die tried to be created with " + numberOfSides + " sides."); 
	}
	
	/**
	 * Default roll is 2 dice 
	 * @return
	 */
	public int[] roll(){
		return roll(2); 
	}
	
	/**
	 * Rolls a specified number of dice and returns the result for each as an array of integers. 
	 * @param numDiceRolled
	 * @return
	 */
	public int[] roll(int numDiceRolled){
		int[] rolledDice = new int[numDiceRolled]; 		
		for (int i = 0; i < numDiceRolled; i++){
			rolledDice[i] = rollSingleDie(); 
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return rolledDice;
	}
	
	/**
	 * Private helper method. Single die rolls should use roll(1). 
	 * @return
	 */
	private int rollSingleDie(){ 
		Random r = new Random(System.currentTimeMillis());
		int rolled = r.nextInt(numberOfSides) + 1; 
		return rolled; 
	}
	
	/*
	 * === SETTERS & GETTERS ====================================================================
	 */
	public int getNumberOfSides()
	{
		return this.numberOfSides;
	}
	
	public void setNumberOfSides(int numberOfSides)
	{
		this.numberOfSides = numberOfSides;
	}

}
