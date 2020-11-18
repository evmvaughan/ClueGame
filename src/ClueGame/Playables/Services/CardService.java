package ClueGame.Playables.Services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Card.CardType;
import ClueGame.Playables.Entities.Card.Collection.CollectionType;
import ClueGame.Playables.Entities.Card.Collection.Hand;
import ClueGame.Playables.Entities.Card.Collection.ICardCollection;
import ClueGame.Playables.Entities.Player.Guess.Solution;
import ClueGame.Playables.Infrastructure.CardStorage;
import Exceptions.BadConfigFormatException;
import Exceptions.CardCollectionException;
import Exceptions.CouldNotCreateEntityException;
import SeedWork.ISingleton;

public class CardService implements ISingleton<CardService> {
	
	private static CardService instance = new CardService();
	
	private CardStorage _cardStorage;
	
	private CardService() {
		_cardStorage = CardStorage.getInstance();
	}

	public static CardService getInstance(){
	   return instance;
	}
	
	//////////////////////
	// Card Service API //
	//////////////////////
	public void pruneStorage() {
		_cardStorage.clear();
	}
	
	public ArrayList<Card> getCards() {
		return _cardStorage.getAll();
	}

	public ArrayList<Card> getWeapons() {
		return _cardStorage.getCardsByType(CardType.WEAPON);
	}
	
	public ArrayList<Card> getPersons() {
		return _cardStorage.getCardsByType(CardType.PERSON);
	}
	
	public ArrayList<Card> getRooms() {
		return _cardStorage.getCardsByType(CardType.ROOM);
	}
	
	public void createNewSolution(Solution solution) throws CardCollectionException {
		if (_cardStorage.getCardCollectionsByType(CollectionType.SOLUTION).size() > 0) {
			throw new CardCollectionException("Solution already exists!");
		}
		_cardStorage.addCardCollection(solution);
	}
	
	public ArrayList<Hand> getHands() {
		
		ArrayList<Hand> hands = new ArrayList<Hand>();
		
		for (ICardCollection collection : _cardStorage.getCardCollectionsByType(CollectionType.HAND)) {
			hands.add((Hand) collection);
		}
		
		return hands;
	}
	
	public ArrayList<Card> getUnusedCards() {
		
		ArrayList<Card> unused = new ArrayList<Card>();
		
		for (Card card : _cardStorage.getAll()) {
			if (!card.isBeingUsed()) {
				unused.add(card);
			}
		}
		return unused;
	}

	public Solution getSolution() {
		return (Solution)(_cardStorage.getCardCollectionsByType(CollectionType.SOLUTION).get(0));
	}
	
	/////////////////////////////////////////////////////////////////
	// Load setup configuration file and build cards from the list //
	/////////////////////////////////////////////////////////////////
	public void createCardsFromConfigFile(File setupConfigurationFile) throws FileNotFoundException, BadConfigFormatException {
		String configurationLine;

		try {
			@SuppressWarnings("resource")
			BufferedReader configurationBuffer = new BufferedReader(new FileReader(setupConfigurationFile));

			while((configurationLine = configurationBuffer.readLine()) != null) {

				if (configurationLine.indexOf("Card") == 0) {

					String[] cardDescription = configurationLine.split(", ");

					if (cardDescription.length > 4) throw new BadConfigFormatException("File format does not meet specifications!");

					String cardType = cardDescription[1];
					String cardName = cardDescription[2];
					String CardID = cardDescription[3];	
					
					Card card = new Card(cardName, CardID.charAt(0));
					card.setCardType(cardType);
					
					_cardStorage.addOne(card);
				}
			}
			
			configurationBuffer.close();
			
			if (_cardStorage.getAll().size() != 21) {
				throw new BadConfigFormatException("File format does not meet specifications!");
			}
			
		} catch(IOException e) {
			System.out.println("File reader error.");
		} catch (CouldNotCreateEntityException e) {
			System.out.println(e.getMessage());
		}
	}
}