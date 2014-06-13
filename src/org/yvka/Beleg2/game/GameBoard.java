package org.yvka.Beleg2.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GameBoard {
	public enum Stone {
		UNSET,
		WHITE_STONE,
		BLACK_STONE,
		UNSET_BUT_POSSIBLE_MOVE;
		
		boolean isEmpty() {
			return this.compareTo(UNSET) == 0 || this.compareTo(UNSET_BUT_POSSIBLE_MOVE) == 0;
		}
		
		boolean isWhiteStone() {
			return this.compareTo(WHITE_STONE) == 0;
		}
		
		boolean isBlackStone() {
			return this.compareTo(BLACK_STONE) == 0;
		}
		
		boolean isReversedColorStone(Stone stone) {
			return !isEmpty() && !equals(stone);
		}
		
		Stone getOpponentStone() {
			if(isBlackStone()) return WHITE_STONE;
			if(isWhiteStone()) return BLACK_STONE;
			return this;
		}
	};

	public enum GameState {
		NEXT_TURN,
		NEW_GAME,
		END_GAME;
	}
	
	Stone[][] fields;
	private int turnNumber;
	private Player firstPlayer;
	private Player secondPlayer;
	private List<FieldVector> toBeReversedStones = new ArrayList<FieldVector>();
	private List<GameEventListener> gameEventListeners = new ArrayList<>();
	private List<FieldVector> freeFields = new ArrayList<>();
	
	public GameBoard() {
		firstPlayer = new Player("White", Stone.WHITE_STONE);
		secondPlayer = new Player("Black", Stone.BLACK_STONE);
	}
	
	public void registerGameEventListener(GameEventListener listener) {
		gameEventListeners.add(listener);
	}
	
	public void removeGameEventListener(GameEventListener listener) {
		gameEventListeners.remove(listener);
	}
	
	protected void notifyListener() {
		GameEvent event = new GameEvent(getGameState(), this);
		for(GameEventListener listener : gameEventListeners) {
			listener.OnGameEvent(this, event);
		}
	}
	
	public boolean canCurrentPlayerSetStoneAt(int row, int col) {

		List<FieldVector> reverseAbleStonesOfCurrentDirection = new ArrayList<FieldVector>();
		FieldVector currentFieldVector = new FieldVector(this, row, col);
		Stone desiredField = currentFieldVector.getField();
		Player currentPlayer = getCurrentPlayer();
		
		toBeReversedStones.clear();
		if(!desiredField.isEmpty()) return false;
		for(FieldVector direction : FieldVector.DIRECTIONS) {
			reverseAbleStonesOfCurrentDirection.clear();
			
			FieldVector desiredFieldVector = new FieldVector(this, row, col);
			desiredFieldVector.add(direction);
			
			while(desiredFieldVector.isInBound() && desiredFieldVector.getField().isReversedColorStone(currentPlayer.getStoneColor())) {
				reverseAbleStonesOfCurrentDirection.add((FieldVector) desiredFieldVector.clone());
				desiredFieldVector.add(direction);
				
			}
			
			if(!desiredFieldVector.isInBound() || !desiredFieldVector.getField().equals(currentPlayer.getStoneColor())) {
				reverseAbleStonesOfCurrentDirection.clear();
			}
			toBeReversedStones.addAll(reverseAbleStonesOfCurrentDirection);
		}
		return toBeReversedStones.size() > 0;
	}
	
	private boolean canCurrentPlayerSetStoneAt(FieldVector vector) {
		return canCurrentPlayerSetStoneAt(vector.row, vector.col);
	}
	
	public boolean setStone(int row, int col) {
		
		if(!canCurrentPlayerSetStoneAt(row, col)) return false;
		Stone currentPlayerColor = getCurrentPlayer().getStoneColor();
		
		FieldVector currentField = new FieldVector(this, row, col);
		toBeReversedStones.add(currentField);
		freeFields.remove(currentField);
		toBeReversedStones.stream().forEach(vec -> vec.setField(currentPlayerColor));
				
		getCurrentPlayer().addStone(toBeReversedStones.size());
		getOppositePlayer().addStone(-(toBeReversedStones.size() - 1));
		toBeReversedStones.clear();
		turnNumber++; // nextTurn
		
		if(!performPossibleMoveChecks()) {
			turnNumber++;
			if(!performPossibleMoveChecks()) {
				freeFields.clear(); // --> end game
			}
		}
		
		notifyListener();
		return true;
	}
	
	public boolean performPossibleMoveChecks() {
		Stream<FieldVector> freeFieldsStream = freeFields.stream();
		
		long sizeOfPossileMoves = freeFieldsStream.filter(fieldVector -> {
			if(canCurrentPlayerSetStoneAt(fieldVector)) {
				fieldVector.setField(Stone.UNSET_BUT_POSSIBLE_MOVE);
				return true;
			} 
			if(fieldVector.getField().isEmpty()) fieldVector.setField(Stone.UNSET);
			return false;
		}).count();
		
		return sizeOfPossileMoves > 0;
	}
	
	public Stone getStone(int row, int col) {
		FieldVector field = new FieldVector(this, row, col);
		field.ensureIsInBound();
		return field.getField();
	}
	
	public void startNewGame(int fieldSize) {
		if(fieldSize < 5 || fieldSize > 10) {
			throw new IllegalArgumentException(String.format("Invalid field size, field size must be between 5 - 10"));
		}
		freeFields.clear();
		fields = new Stone[fieldSize][fieldSize];
		
		// set mid stones
		int posFirstStone = (fieldSize - 2) / 2; // mid pos
		fields[posFirstStone][posFirstStone] = Stone.WHITE_STONE;
		fields[posFirstStone][posFirstStone + 1] = Stone.BLACK_STONE;
		fields[posFirstStone + 1][posFirstStone] = Stone.BLACK_STONE;
		fields[posFirstStone + 1][posFirstStone + 1] = Stone.WHITE_STONE;
		
		// unset other stones
		for(int row = 0; row < fieldSize; row++) {
			for(int col = 0; col < fieldSize; col++) {
				FieldVector fieldVector = new FieldVector(this, row, col);
				Stone currentStone = fieldVector.getField();
				if(Stone.WHITE_STONE.equals(currentStone) || Stone.BLACK_STONE.equals(currentStone)) continue;
				fieldVector.setField(Stone.UNSET);
				freeFields.add(fieldVector);
			}
		}
		
		turnNumber = 0;
		getFirstPlayer().resetPoints();
		getSecondPlayer().resetPoints();
		performPossibleMoveChecks();
		notifyListener();
		
	}
	
	public GameState getGameState() {
		if(turnNumber == 0) return GameState.NEW_GAME;
		if(freeFields.size() <= 0) return GameState.END_GAME;
		return GameState.NEXT_TURN;

	}
	
	public Player getFirstPlayer() {
		return this.firstPlayer;
	}
	
	public Player getSecondPlayer() {
		return this.secondPlayer;
	}
	
	public Player getCurrentPlayer() {
		return turnNumber % 2 == 0 ? getFirstPlayer() : getSecondPlayer();
	}
	
	public Player getOppositePlayer() {
		return turnNumber % 2 == 0 ? getSecondPlayer() : getFirstPlayer();
	}
	
	
	public int getSize() {
		return fields.length;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		for(int row = 0; row < fields.length; row++) {
			str.append(" ");
			for(int col = 0; col < fields.length; col++) str.append("----");
			str.append("-\n");
			str.append(" | ");
			for(int col = 0; col < fields.length; col++) {
				String fieldStr = " ";
				
				if(Stone.UNSET.equals(fields[row][col]) && canCurrentPlayerSetStoneAt(row, col)) {
					fieldStr = "x";
				}
				
				if(fields[row][col].isWhiteStone()) {
					fieldStr = "W";
				}
				if(fields[row][col].isBlackStone()) {
					fieldStr = "B";
				}
				str.append(fieldStr);
				str.append(" | ");
			}
			str.append("\n");
		}
		for(int col = 0; col < fields.length; col++) str.append("----");
		str.append("\n");
		str.append("\nCurrent Player " + getCurrentPlayer().getStoneColor().name());
		str.append(String.format("\nWhite: %d \t Black: %d", firstPlayer.getStones(), secondPlayer.getStones()));
		str.append("\n");
		return str.toString();
	}	
	
}
