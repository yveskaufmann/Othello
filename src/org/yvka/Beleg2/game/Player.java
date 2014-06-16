package org.yvka.Beleg2.game;

import org.yvka.Beleg2.game.GameBoard.Stone;

/**
 * The implementation of a Othello Player which contains<br>
 * the following informations:
 * <ul>
 * 	<li>The name of the player</li>
 * 	<li>The points of the player which means the number of stones of the players color which are on the field.</li>
 *  <li>The stone color of this player</li>
 * </ul>
 * 
 * @author Yves Kaufmann
 */
public class Player {
	
	private static final int INITIAL_COUNT_OF_STONES = 2;
	
	private String name;
	private int countOfStones;
	private Stone stoneColor;
	
	/**
	 * Creates a Othello player with a specified name 
	 * and her or her stone color.  
	 * 
	 * @param name the name of this player
	 * @param stoneColor the stone color of this player
	 */
	public Player(String name, Stone stoneColor ) {
		this.name = name;
		this.stoneColor = stoneColor;
		resetPoints();
	}
	
	/**
	 * Adds a number of stones to the count of stones of the current player which are
	 * on the field.  
	 * 
	 * @param stones the number of points which should be added to the player points.
	 */
	public void addCountOfStones(int stones) {
		this.countOfStones += stones;
	}
	
	/**
	 * Returns the count of stones of the current player which are on the field.
	 * 
	 * @return the count of stones which should be added.
	 */
	public int getCountOfStones() {
		return countOfStones;
	}
	
	/**
	 * Resets the count of stones of the current player.
	 */
	public void resetPoints() {
		this.countOfStones = INITIAL_COUNT_OF_STONES;
	}
	
	/**
	 * Returns the name this player.
	 * 
	 * @return the name of this player.
	 */
	public String getName() {
		return name;
	}	
	
	/**
	 * Returns the stone color of this player.
	 * 
	 * @return the stone color of this player.
	 */
	public Stone getStoneColor() {
		return stoneColor;
	}
}
