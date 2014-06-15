package org.yvka.Beleg1.ui;

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
import org.yvka.Beleg2.game.GameEventListener;


public class GameControlPanel extends Parent implements GameEventListener {
	
	private static final String GAME_FIELD_CHILD_CHOOSER = "gameFieldChildChooser";
	private ComboBox<Integer> fieldSizeChooser;
	private Button newGameButton;
	private PlayerPointsLabel whitePlayerLabel;
	private PlayerPointsLabel blackPlayerLabel;

	
	public GameControlPanel(GameBoard gameLogic) {
		
		gameLogic.registerGameEventListener(this);
		fieldSizeChooser = new ComboBox<>();
		fieldSizeChooser.setId(GAME_FIELD_CHILD_CHOOSER);
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
	
	@Override
	public void OnGameEvent(GameBoard board, GameEvent event) {
		
		whitePlayerLabel.setAsCurrentPlayer(board.getCurrentPlayer().equals(board.getFirstPlayer()));
		blackPlayerLabel.setAsCurrentPlayer(board.getCurrentPlayer().equals(board.getSecondPlayer()));
		
		whitePlayerLabel.playerPointsProperty.set(event.getFirstPlayerStones());
		blackPlayerLabel.playerPointsProperty.set(event.getSecondPlayerStones());
		
	}
	
	public ReadOnlyObjectProperty<Integer> gameFieldSizeProperty() {
		return fieldSizeChooser.valueProperty();
	}
	
	public void setOnNewGameClicked(EventHandler<ActionEvent> eventHandler) {
		newGameButton.setOnAction(eventHandler);
	}
	
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
