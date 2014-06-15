package org.yvka.Beleg1.ui;


import javafx.scene.Group;
import javafx.scene.layout.Region;

import org.yvka.Beleg2.game.GameBoard;
import org.yvka.Beleg2.game.GameEvent;
import org.yvka.Beleg2.game.GameEventListener;


public class GameField extends Region implements GameEventListener {
	
	private static final int DEFAULT_FIELD_SIZE = 8;
	private static final int FIELD_GAP_WIDTH = 0;
	private GameBoard gameLogic = null;
	private Group fieldGroup;
	private int size;
	
	public GameField(GameBoard board) {
		setStyle("-fx-background-color: green;-fx-padding: 15;");
		gameLogic = board;
		gameLogic.registerGameEventListener(this);
		setSize(DEFAULT_FIELD_SIZE);
	}
	
	public void startNewGame() {
		setSize(size);
	}
	
	public void setSize(int size) {
		gameLogic.removeAllGameEventListenersByType(StoneField.class);
		this.size = size;
		fieldGroup = new Group();
		fieldGroup.relocate(30, 30);
		for(int row = 0; row  < size; row++) {
			for(int col = 0; col < size; col++) {
				
				boolean isAlternativeField = (row * size + col) % 2 == (size % 2 == 0 ? row % 2 : 0);
				StoneField field = new StoneField(row, col, isAlternativeField, (currField, event) -> {
					if(gameLogic.canCurrentPlayerSetStoneAt(currField.getRow(), currField.getCol())) {
						gameLogic.setStone(currField.getRow(), currField.getCol());
					}
				});
				gameLogic.registerGameEventListener(field);
				field.relocate(
					col *(StoneField.FIELD_WIDTH + FIELD_GAP_WIDTH), 
					row * (StoneField.FIELD_WIDTH + FIELD_GAP_WIDTH));			
				fieldGroup.getChildren().add(field);
			}
		}
		
		getChildren().clear();
		getChildren().add(fieldGroup);
		autosize();
	
		gameLogic.startNewGame(size);
	}
	
	@Override
	public void OnGameEvent(GameBoard board, GameEvent event) {	
		switch(event.getState()) {
			case NEW_GAME:
			break;
			case END_GAME:
				OthelloApplication
				.getInstance()
				.showModalDimmer(
					new GameEndNotification(
						gameLogic.getFirstPlayer(), 
						gameLogic.getSecondPlayer(),
						(e) -> {
							startNewGame();
						}
					)
				);
			break;
			case NEXT_TURN:
			break;
		}
	}
}
