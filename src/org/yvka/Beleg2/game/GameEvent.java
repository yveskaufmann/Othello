package org.yvka.Beleg2.game;

import org.yvka.Beleg2.game.GameBoard.GameState;

/**
 * <p>
 * A event which contains information about 
 * a occurred event in the game such as new 
 * new game or next turn.<br>
 * <br>
 * </p>
 * @author Yves Kaufmann
 *
 */
public class GameEvent {
	private GameState state;
	private GameBoard field;
	
	/**
	 * <p>
	 * Creates a game event with a specified {@link GameState} and 
	 * a {@link GameBoard} which both contains informations
	 * about the occurred event. 
	 * </p>
	 * 
	 * @param state the state of the specified game field.
	 * @param gameBoard the game board in which the event occurs. 
	 */
	public GameEvent(GameState state, GameBoard gameBoard) {
		super();
		this.state = state;
		this.field = gameBoard;
	}
	
	/**
	 * Returns the state of this event.
	 * 
	 * @return the state of this event.
	 */
	public GameState getState() {
		return state;
	}
	
	/**
	 * Returns the count of stones of the first player
	 * which means the number of stones on the field.
	 * 
	 * @return the count of stones of the first player 
	 */
	public int getFirstPlayerStones() {
		return field.getFirstPlayer().getCountOfStones();
	}

	/**
	 * Returns the count of stones of the second player
	 * which means the number of stones on the field.
	 * 
	 * @return the count of stones of the second player 
	 */
	public int getSecondPlayerStones() {
		return field.getSecondPlayer().getCountOfStones();
	}
	
	/**
	 * Returns the name of the first player.
	 * 
	 * @return the name of the first player.
	 */
	public String getFirstPlayerName() {
		return field.getFirstPlayer().getName();
	}

	/**
	 * Returns the name of the second player.
	 * 
	 * @return the name of the second player.
	 */
	public String getSecondPlayerName() {
		return field.getSecondPlayer().getName();
	}
	
	/**
	 * Returns the game game board in which this event occurred.
	 * 
	 * @return the game game board in which this event occurred.
	 */
	public GameBoard getSrcGameBoard() {
		return field;
	}
}
