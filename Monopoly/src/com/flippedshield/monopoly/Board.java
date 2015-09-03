package com.flippedshield.monopoly;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Board {
//	private static Board instance; 
	
	private static final String PROPERTIES_FILE = "properties.txt"; 
	private static final String CARD_INFORMATION_JSON = "cardInformation.json"; 
	private static final String DEEDS_INFO_JSON = "deeds.json";
	private static final String TOKENS_JSON = "tokens.json";
	private static final String PLAYERS_JSON = "players.json";
	
	private static BankerPlayer banker;
	
	private ArrayList<Player> players;
	private Die die;
	private ArrayList<Card> bigFunCards;
	private ArrayList<Card> contingencyCards;
	private ArrayList<Space> spaces; 
	private ArrayList<PlayerToken> playerTokens;
	
	public Board() {
		initPlayers();
		die = new Die();
		initBigFunCards();
		initContingencyCards();
		try {
			initSpaces();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		initTokens(); 
		giveTokensToPlayers(); 
	}
	
	private void giveTokensToPlayers(){
		Random r = new Random(); 	
		
		for (int i = 0; i < players.size(); i++) {
			int index = r.nextInt(playerTokens.size()); 
			players.get(i).setPlayerToken(playerTokens.remove(index));  
		}
	}
		
	private  void initPlayers()
	{
		players = new ArrayList<Player>(); 
		JSONParser parser = new JSONParser(); 
		try {
			Object obj = parser.parse(new FileReader(PLAYERS_JSON)); 
			JSONObject jsonObj = (JSONObject) obj; 
			JSONArray jsonPlayers = (JSONArray) jsonObj.get("players"); 
			Iterator<JSONObject> iterator = jsonPlayers.iterator(); 
			while(iterator.hasNext()){
				players.add(new Player(iterator.next())); 
			} 
			} catch (IOException | ParseException e){
				e.printStackTrace();
		}
		
		setBanker(new BankerPlayer("Banker"));
	}
	
	/**
	 * Creates the spaces list and fills it with space objects
	 * @throws IOException
	 */
	private void initSpaces() throws IOException{
//		spaces = new ArrayList<Space>(40);
//		BufferedReader br = new BufferedReader(new FileReader(PROPERTIES_FILE));
//		try { 
//			String line = br.readLine(); 
//			while (line != null) {
//				spaces.add(new Space(line));
//				line = br.readLine(); 
//			}	
//		} finally {
//			br.close(); 
//		}
		
		
		spaces = new ArrayList<Space>(40);
		ArrayList<Deed> properties = Game.getBank().getDeeds();
		
		/*
		JSONParser parser = new JSONParser();
		JSONObject space;
		
		try {
			Object obj = parser.parse(new FileReader(DEEDS_INFO_JSON));
			JSONObject jsonObj = (JSONObject) obj;
			
			JSONArray spacesArray = (JSONArray) jsonObj.get("deeds");
			
			Iterator<JSONObject> iterator = spacesArray.iterator();
			
			while(iterator.hasNext())
			{
				space = iterator.next();
				properties.add(
						new PropertySpace(
							new Deed()
						));
			}
			
//			spaces.add(new PropertySpace(null, 0));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		*/
		int i = 0;
		int j = 0;
		spaces.add(new GoSpace("Go Space"));
		spaces.add(new PropertySpace(properties.get(i++))); 
		spaces.add(new CardSpace("Contingency Card"));
		spaces.add(new PropertySpace(properties.get(i++)));
		spaces.add(new FeeSpace("Tax"));
		spaces.add(new PropertySpace(properties.get(i++)));
		spaces.add(new PropertySpace(properties.get(i++)));
		spaces.add(new CardSpace("Big Fun Card"));
		spaces.add(new PropertySpace(properties.get(i++)));
		spaces.add(new PropertySpace(properties.get(i++)));
		
		spaces.add(new JailSpace("Jail/Visiting"));
		for(j = 0; j < 6; j++)
		{
			spaces.add(new PropertySpace(properties.get(j)));
		}
		i += j;
		
		spaces.add(new CardSpace("Contingency Card"));
		spaces.add(new PropertySpace(properties.get(i++)));
		spaces.add(new PropertySpace(properties.get(i++)));
		
		spaces.add(new FreeParkingSpace("Free Parking"));
		spaces.add(new PropertySpace(properties.get(i++)));
		spaces.add(new CardSpace("Big Fun Card"));
		for(j = 0; j < 7; j++)
		{
			spaces.add(new PropertySpace(properties.get(j)));
		}
		i+=j;
		
		spaces.add(new GoToJailSpace("Go To Jail"));
		spaces.add(new PropertySpace(properties.get(i++)));
		spaces.add(new PropertySpace(properties.get(i++)));
		spaces.add(new CardSpace("Contingency Card"));
		spaces.add(new PropertySpace(properties.get(i++)));
		spaces.add(new PropertySpace(properties.get(i++)));//should be RR
		spaces.add(new CardSpace("Big Fun Card"));
		spaces.add(new PropertySpace(properties.get(i++)));
		spaces.add(new FeeSpace("Tax"));
		spaces.add(new PropertySpace(properties.get(i++)));
		
		
		
		
		for(Space s : spaces)
		{
			System.out.println(" + " + s.getName());
		}
		System.out.println("SIZE " + spaces.size());
		
	}
	
	/**
	 * Creates big fun card list and fills it with cards
	 */
	private  void initBigFunCards()
	{
		bigFunCards = new ArrayList<Card>();
		
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader(CARD_INFORMATION_JSON));
			JSONObject jsonObj = (JSONObject) obj;
			
			JSONArray cards = (JSONArray) jsonObj.get("bigFunCards");
			
			Iterator<JSONObject> iterator = cards.iterator();
			
			while(iterator.hasNext())
			{
				bigFunCards.add(new Card(iterator.next()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates contingency card list and fills it with cards
	 */
	private  void initContingencyCards()
	{
		contingencyCards = new ArrayList<Card>();
		
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader(CARD_INFORMATION_JSON));
			JSONObject jsonObj = (JSONObject) obj;
			
			JSONArray cards = (JSONArray) jsonObj.get("contingencyCards");
			
			Iterator<JSONObject> iterator = cards.iterator();
			
			while(iterator.hasNext())
			{
				contingencyCards.add(new Card(iterator.next()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates list of Tokens and initializes it from TOKENS_JSON file 
	 */
	private  void initTokens(){
		playerTokens = new ArrayList<PlayerToken>(); 
		JSONParser parser = new JSONParser(); 
		try {
			Object obj = parser.parse(new FileReader(TOKENS_JSON)); 
			JSONObject jsonObj = (JSONObject) obj; 
			JSONArray tokens = (JSONArray) jsonObj.get("tokens"); 
			Iterator<JSONObject> iterator = tokens.iterator(); 
			while(iterator.hasNext()){
				playerTokens.add(new PlayerToken(iterator.next())); 
			} 
			} catch (IOException | ParseException e){
				e.printStackTrace();
		}
	}
	
	/*
	 * === GETTERS AND SETTERS  ==================================
	 */
	public ArrayList<Player> getPlayers() { return players; }
	public Die getDie() { return die; }
	public ArrayList<Card> getBigFunCards() { return bigFunCards; }
	public ArrayList<Card> getContingencyCards() { return contingencyCards; }
	public ArrayList<Space> getSpaces() { return spaces; } 
	public ArrayList<PlayerToken> getTokens() { return playerTokens; }

	public static BankerPlayer getBanker() {
		return banker;
	}

	public static void setBanker(BankerPlayer banker) {
		Board.banker = banker;
	} 

}
