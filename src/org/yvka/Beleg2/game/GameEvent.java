package org.yvka.Beleg2.game;

import org.yvka.Beleg2.game.GameBoard.GameState;

public class GameEvent {
	private GameState state;
	private GameBoard field;
	
	public GameEvent(GameState state, GameBoard field) {
		super();
		this.state = state;
		this.field = field;
	}

	public GameState getState() {
		return state;
	}
	
	public int getFirstPlayerStones() {
		return field.getFirstPlayer().getStones();
	}

	public int getSecondPlayerStones() {
		return field.getSecondPlayer().getStones();
	}

	public String getFirstPlayerName() {
		return field.getFirstPlayer().getName();
	}

	public String getSecondPlayerName() {
		return field.getSecondPlayer().getName();
	}
	
	public GameBoard getField() {
		return field;
	}
}
