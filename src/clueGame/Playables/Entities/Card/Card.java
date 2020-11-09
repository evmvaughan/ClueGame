package clueGame.Playables.Entities.Card;

import Exceptions.BadConfigFormatException;
import SeedWork.IEvent;

public class Card implements IEvent {
	
	private CardType _type;
	private String _name;
	private boolean _inUse;
	
	private char _iD;
	
	public Card(String name, char iD) {
		_name = name;
		_iD = iD;
	}
	
	public CardType getType(){
		   return _type;
	}

	public String toString() {
		return "Card [type = " + _type + " name = " + _name + " iD = " + _iD + "]";
	}	
	
	public String getName() {
		return _name;
	}
	
	
	public void setCardType(String stringType) throws BadConfigFormatException {
		if (stringType.equals("Weapon")) {
			_type = CardType.WEAPON;
			
		}else if(stringType.equals("Player")) {
			_type = CardType.PERSON;
		}
		else if (stringType.equals("Room")) {
			_type = CardType.ROOM;
		} else {
			throw new BadConfigFormatException("Configuration incorrectly formatted!");
		}
	}

	public boolean isBeingUsed() {
		return _inUse;
	}
	
	public void setBeingUsed() {
		_inUse = true;
	}
}
