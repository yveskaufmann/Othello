package org.yvka.Beleg2.game;

import org.yvka.Beleg2.game.GameBoard.Stone;

public class Player {
	private static final int INITIAL_COUNT_OF_STONES = 2;
	private String name;
	private int countOfStones;
	private Stone stoneColor;
	
	public Player(String name, Stone stoneColor ) {
		this.name = name;
		this.stoneColor = stoneColor;
		resetPoints();
	}
	
	public void addStone(int stones) {
		this.countOfStones += stones;
	}
	
	public void resetPoints() {
		this.countOfStones = INITIAL_COUNT_OF_STONES;
	}
	
	public int getStones() {
		return countOfStones;
	}
	
	public String getName() {
		return name;
	}	
	
	public Stone getStoneColor() {
		return stoneColor;
	}
}
