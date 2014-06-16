package org.yvka.Beleg2.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * <p>
 * Implementation of the Othello game board which encapsulate<br>
 * the logic of the game. The game is as state machine implemented<br>
 * which means the GameBoard have to called from the outside <br>
 * in order to work properly.<br>
 * <br>
 * A implementer of the game have to register a {@link GameEventHandler} (by {@link #registerGameEventHandler(GameEventHandler)}) <br>
 * which handles the emitted events of the game field. Such as the start of a new game,<br>
 * the end of a game and the start of a new turn. This enables the user interface <br>
 * to redraw the field or notify the user of the end of the game.<br>
 * <br>
 * In order to perform a move on the game board a caller have to call {@link #canCurrentPlayerSetStoneAt(FieldVector)}<br>
 * to check if the current player can set the stone by using {@link #setStone(int, int)}.<br>
 * <br>
 * The game board handles the turn mechanism by its self which<br>
 * means the board it self decide which player is on turn.<br> 
 * All actions are performed in the name of the current player.
 * </p> 
 * @author Yves Kaufmann
 *
 */
public class GameBoard {
	/**
	 * Enumeration of possible stone/field states which
	 * descriping the content of a gameboard field.
	 * 
	 * @author Yves Kaufmann
	 *
	 */
	public enum Stone {
		/**
		 * Describes a empty field
		 */
		UNSET,
		/**
		 * Describes a empty field which is set able by the current player.
		 */
		UNSET_BUT_POSSIBLE_MOVE,
		/**
		 * Describes a not empty field which is contains a white stone.
		 */
		WHITE_STONE,
		/**
		 * Describes a not empty field which is contains a black stone.
		 */
		BLACK_STONE;
		
		/**
		 * Determines if this stone state describes a empty field.
		 * 
		 * @return true, if this stone state describes a empty field.
		 */
		boolean isEmpty() {
			return this.compareTo(UNSET) == 0 || this.compareTo(UNSET_BUT_POSSIBLE_MOVE) == 0;
		}
		
		/**
		 * Determines if this stone state describes a white stone field.
		 * 
		 * @return true, if this stone state describes a white stone field
		 */
		boolean isWhiteStone() {
			return this.compareTo(WHITE_STONE) == 0;
		}
		
		/**
		 * Determines if this stone state describes a black stone field.
		 * 
		 * @return true, if this stone state describes a black stone field
		 */
		boolean isBlackStone() {
			return this.compareTo(BLACK_STONE) == 0;
		}
		
		/**
		 * Determines if the specified stone has the opposite color 
		 * of this field.
		 * 
		 * @param stone the specified stone
		 * @return true, if the specified stone has the opposite color 
		 */
		boolean isReversedColorStone(Stone stone) {
			return !isEmpty() && !equals(stone);
		}
		
		/**
		 * Returns the opponent stone of this stone if 
		 * the stone is not empty otherwise this stone is returned.
		 * 
		 * @return the opponent stone of this stone.
		 */
		Stone getOpponentStone() {
			if(isBlackStone()) return WHITE_STONE;
			if(isWhiteStone()) return BLACK_STONE;
			return this;
		}
	};
	
	/**
	 * Enumeration of possible game events.
	 * 
	 * @author Yves Kaufmann
	 *
	 */
	public enum GameState {
		/**
		 * Describes the start of a new turn.
		 */
		NEXT_TURN,
		/**
		 * Describes the start of a new game.
		 */
		NEW_GAME,
		/**
		 * Describes the end of a game.
		 */
		END_GAME;
	}
	
	Stone[][] fields;
	private int turnNumber;
	private Player firstPlayer;
	private Player secondPlayer;
	private List<FieldVector> toBeReversedStones = new ArrayList<FieldVector>();
	private List<GameEventHandler> gameEventListeners = new ArrayList<>();
	private List<FieldVector> freeFields = new ArrayList<>();
	
	/**
	 * Create a new {@link GameBoard}
	 */
	public GameBoard() {
		firstPlayer = new Player("White", Stone.WHITE_STONE);
		secondPlayer = new Player("Black", Stone.BLACK_STONE);
	}
	
	/**
	 * Registers a {@link GameEventHandler} which is 
	 * called by this game board if any of the {@link GameState} occurs. 
	 *   
	 * @param listener the GameEventHandler which should be registered.
	 */
	public void registerGameEventHandler(GameEventHandler listener) {
		gameEventListeners.add(listener);
	}
	
	/**
	 * Removes / unregister {@link GameEventHandler} by his object instance.    
	 * 
	 * @param listener the GameEventHandler which should be removed.
	 */
	public void removeGameEventHandler(GameEventHandler listener) {
		gameEventListeners.remove(listener);
	}
	
	/**
	 * Removes / unregister all {@link GameEventHandler} by his type.    
	 * 
	 * @param type the type of the to be removed {@link GameEventHandler}s.
	 */
	public void removeAllGameEventHandlersByType(Class<?> type) {
		gameEventListeners.removeIf(listener -> type.equals(listener.getClass()));
	}
	
	/**
	 * Notifies all registered {@link GameEventHandler}s.
	 */
	protected void notifyListeners() {
		GameEvent event = new GameEvent(getGameState(), this);
		for(GameEventHandler listener : gameEventListeners) {
			listener.OnGameEvent(event);
		}
	}
	
	/**
	 * Checks if the current player can set a stone to the specified field position.
	 * 
	 * @param row the row of the specified field.
	 * @param col the column of the specified field.
	 * @return true, if the current player can set a stone to the specified field position.
	 * @throws IndexOutOfBoundsException if the specified field is out of bounds.
	 */
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
	
	/**
	 * Set a stone of the current player to the specified field position.
	 * 
	 * @param row the row of the specified field.
	 * @param col the column of the specified field.
	 * @return true, if the current player can set a stone to the specified field position.
	 * @throws IndexOutOfBoundsException if the specified field is out of bounds.
	 */
	public boolean setStone(int row, int col) {
		
		if(!canCurrentPlayerSetStoneAt(row, col)) return false;
		Stone currentPlayerColor = getCurrentPlayer().getStoneColor();
		
		FieldVector currentField = new FieldVector(this, row, col);
		toBeReversedStones.add(currentField);
		freeFields.remove(currentField);
		toBeReversedStones.stream().forEach(vec -> vec.setField(currentPlayerColor));
				
		getCurrentPlayer().addCountOfStones(toBeReversedStones.size());
		getOppositePlayer().addCountOfStones(-(toBeReversedStones.size() - 1));
		toBeReversedStones.clear();
		turnNumber++; // nextTurn
		
		if(!performPossibleMoveChecks()) {
			turnNumber++;
			if(!performPossibleMoveChecks()) {
				freeFields.clear(); // --> end game
			}
		}
		
		notifyListeners();
		return true;
	}
	
	/**
	 * Determines which fields are possible set able by the current player. 
	 * 
	 * @return true, if any field is set able by the current player.
	 */
	private boolean performPossibleMoveChecks() {
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
	
	/**
	 * Returns the {@link Stone} state of the specified field. 
	 * 
	 * @param row the row of the specified field.
	 * @param col the column of the specified field.
	 * @return the {@link Stone} state of the specified field.
	 * @throws IndexOutOfBoundsException if the specified field is out of bounds.
	 */
	public Stone getStone(int row, int col) {
		FieldVector field = new FieldVector(this, row, col);
		field.ensureIsInBound();
		return field.getField();
	}
	
	/**
	 * Start a new game with a specified field size.
	 * 
	 * @param fieldSize the specified field size between 5 and 10.<br>
	 * 		  The field size describes the count of rows and count of columns of the game board.
	 * @throws IllegalArgumentException if the field size is not between 5 and 10.
	 */
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
		notifyListeners();
		
	}
	
	/**
	 * Returns the current {@link GameState} of this game board.
	 * 
	 * @return the current {@link GameState} of this game board.
	 */
	public GameState getGameState() {
		if(turnNumber == 0) return GameState.NEW_GAME;
		if(freeFields.size() <= 0) return GameState.END_GAME;
		return GameState.NEXT_TURN;

	}
	
	/**
	 * Returns the first player of the game.
	 * 
	 * @return the first player of the game.
	 */
	public Player getFirstPlayer() {
		return this.firstPlayer;
	}
	
	/**
	 * Return the second player of the game.
	 * 
	 * @return the second player of the game.
	 */
	public Player getSecondPlayer() {
		return this.secondPlayer;
	}
	
	/**
	 * Return the player which is currently on turn.
	 * 
	 * @return the player which is currently on turn.
	 */
	public Player getCurrentPlayer() {
		return turnNumber % 2 == 0 ? getFirstPlayer() : getSecondPlayer();
	}

	/**
	 * Return the player which is currently not on turn.
	 * 
	 * @return the player which is currently not on turn.
	 */
	public Player getOppositePlayer() {
		return turnNumber % 2 == 0 ? getSecondPlayer() : getFirstPlayer();
	}
	
	
	/**
	 * Return the field size of the current game board, <br>
	 * the field size describes the count of rows and count of columns of the game board.
	 * 
	 * @return the field size of the current game board.
	 */
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
		str.append(String.format("\nWhite: %d \t Black: %d", firstPlayer.getCountOfStones(), secondPlayer.getCountOfStones()));
		str.append("\n");
		return str.toString();
	}	
	
}
