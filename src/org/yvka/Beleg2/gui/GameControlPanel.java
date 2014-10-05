package org.yvka.Beleg2.gui;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import org.yvka.Beleg2.game.GameBoard;
import org.yvka.Beleg2.game.GameEvent;
import org.yvka.Beleg2.game.GameEventHandler;

/**
 * <p>
 * This class implements the {@link GameControlPanel} which contains<br>
 * the Controls for creating a new game, a control which allows <br>
 * to choose the game field size and the current score of the game.<br>
 * </p>
 * 
 * @author Yves Kaufmann
 *
 */
public class GameControlPanel extends Parent implements GameEventHandler {
	
	/**
	 * The id of this game field chooser which can be used to style this field size chooser with css.
	 */
	private static final String GAME_FIELD_SIZE_CHOOSER_ID = "gameFieldChildChooser";
	
	private ComboBox<Integer> fieldSizeChooser;
	private Button newGameButton;
	private PlayerPointsLabel whitePlayerLabel;
	private PlayerPointsLabel blackPlayerLabel;

	
	/**
	 * <p>
	 * Creates the GameControlPanel which should <br>
	 * handle the events from a {@link GameBoard}. <br> 
	 * Which means this object will show the score of 'gamelogic' and <br> 
	 * triggers the resize of the 'gamelogic' or triggers the restart <br>
	 * of the which is handled by gameLogic.<br>
	 * <br>
	 * <p>
	 * 
	 * @param gameLogic the assigned {@link GameBoard} which events should be handled.
	 */
	public GameControlPanel(GameBoard gameLogic) {
		
		gameLogic.registerGameEventHandler(this);
		
		fieldSizeChooser = new ComboBox<>();
		fieldSizeChooser.setId(GAME_FIELD_SIZE_CHOOSER_ID);
		fieldSizeChooser.setValue(8);
		fieldSizeChooser.setPromptText("Feldgröße: ");
		fieldSizeChooser.getItems().addAll(6, 7, 8, 9, 10);
		fieldSizeChooser.setButtonCell(new FieldSizeListCell());
		fieldSizeChooser.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {
			@Override
			public ListCell<Integer> call(ListView<Integer> param) {
				return new FieldSizeListCell();
			}
		});
		
		newGameButton = new Button();
		newGameButton.setText("New Game");
		
		whitePlayerLabel = new PlayerPointsLabel(Color.WHITE);
		blackPlayerLabel = new PlayerPointsLabel(Color.BLACK); 
	
		HBox root = new HBox(5);
		root.setAlignment(Pos.CENTER_LEFT);
		root.setPadding(new Insets(5.0));
	
		root.getChildren().addAll(
				newGameButton,
				fieldSizeChooser, 
				whitePlayerLabel,
				blackPlayerLabel
		);
		getChildren().add(root);
	
	}
	
	/**
	 * <p>
	 * Handles the game events which are occurred in the assigned {@link GameBoard}.
	 * <br>
	 * Ensures that the score is up to date, which means this listener update
	 * the score display. 
	 * </p>
	 */
	@Override
	public void OnGameEvent(GameEvent event) {
		GameBoard board = event.getSrcGameBoard();
		whitePlayerLabel.setAsCurrentPlayer(board.getCurrentPlayer().equals(board.getFirstPlayer()));
		blackPlayerLabel.setAsCurrentPlayer(board.getCurrentPlayer().equals(board.getSecondPlayer()));
		
		whitePlayerLabel.playerPointsProperty().set(event.getFirstPlayerStones());
		blackPlayerLabel.playerPointsProperty().set(event.getSecondPlayerStones());
		
	}
	
	/**
	 * Returns the gameFieldSize property which is
	 * used to listen to field size changes. 
	 * 
	 * @return the gameFieldSize property.
	 */
	public ReadOnlyObjectProperty<Integer> gameFieldSizeProperty() {
		return fieldSizeChooser.valueProperty();
	}
	
	/**
	 * Register the {@link EventHandler} for the NewGame Button which
	 * should restart the currently running game. 
	 * 
	 * @param eventHandler the {@link EventHandler} which should handle the click on
	 * 					   the new game button
	 */
	public void setOnNewGameClicked(EventHandler<ActionEvent> eventHandler) {
		newGameButton.setOnAction(eventHandler);
	}
	
	/**
	 * <p>
	 * A Implementation of {@link ListCell} which 
	 * format the content of a ListCell in the following format: %d X %d
	 * </p>
	 * 
	 * @see ListCell
	 * @author Yves Kaufmann
	 */
	static class FieldSizeListCell extends ListCell<Integer> {
		@Override
		protected void updateItem(Integer item, boolean empty) {
			super.updateItem(item, empty);
			if(item != null) {
				String caption = item  + " X " + item;
				setText(caption);
			} else {
				setText(null);
			}
		}
	}
}
