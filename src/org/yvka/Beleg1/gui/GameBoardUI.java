package org.yvka.Beleg1.gui;


import javafx.scene.Group;
import javafx.scene.layout.Region;

import org.yvka.Beleg2.game.GameBoard;
import org.yvka.Beleg2.game.GameEvent;
import org.yvka.Beleg2.game.GameEventHandler;


/**
 * This class implements the representation of the othello game board.
 * 
 * @author Yves Kaufmann
 *
 */
public class GameBoardUI extends Region implements GameEventHandler {
	
	/**
	 * The default size of the game field. 
	 */
	private static final int DEFAULT_FIELD_SIZE = 8;
	
	/**
	 * The gap size between field. 
	 */
	private static final int FIELD_GAP_WIDTH = 0;
	
	private GameBoard gameLogic = null;
	private Group fieldGroup;
	private int size;
	
	/**
	 * Creates a game field and specifies the game logic.
	 * 
	 * @param board the game logic of the game.
	 */
	public GameBoardUI(GameBoard board) {
		setStyle("-fx-background-color: green;-fx-padding: 15;");
		gameLogic = board;
		gameLogic.registerGameEventHandler(this);
		setSize(DEFAULT_FIELD_SIZE);
	}
	
	/**
	 * Triggers the start of a new game.
	 */
	public void startNewGame() {
		setSize(size);
	}
	
	/**
	 * <p>
	 * Set the size of the game field and triggers the start of a new game.
	 * <br>
	 * Creates the gamefield and register his event listeners.<br>
	 * </p>
	 * 
	 * 
	 * @param size the new size of the game field.
	 */
	public void setSize(int size) {
		gameLogic.removeAllGameEventHandlersByType(StoneField.class);
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
				gameLogic.registerGameEventHandler(field);
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
	
	/**
	 * Handles the occurring of the end game event
	 * and shows in this case the GameEndNotification. 
	 */
	@Override
	public void OnGameEvent(GameEvent event) {	
		switch(event.getState()) {
			case NEW_GAME:
			break;
			case END_GAME:
				OthelloGuiApplication
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
