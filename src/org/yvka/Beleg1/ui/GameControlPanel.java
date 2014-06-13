package org.yvka.Beleg1.ui;

import org.yvka.Beleg2.game.GameBoard;
import org.yvka.Beleg2.game.GameEvent;
import org.yvka.Beleg2.game.GameEventListener;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;


public class GameControlPanel extends Parent implements GameEventListener {
	
	private static final String GAME_FIELD_CHILD_CHOOSER = "gameFieldChildChooser";
	private ComboBox<Integer> fieldSizeChooser;
	private Button newGameButton;
	private PlayerLabel whitePlayerLabel;
	private PlayerLabel blackPlayerLabel;
	
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
		
		whitePlayerLabel = new PlayerLabel(gameLogic.getFirstPlayer().getName());
		blackPlayerLabel = new PlayerLabel(gameLogic.getSecondPlayer().getName());
		
		HBox root = new HBox(2.0);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(5.0));
		root.getChildren().addAll(
			fieldSizeChooser, 
			newGameButton,
			whitePlayerLabel,
			blackPlayerLabel
		);
		getChildren().add(root);
	
	}
	
	@Override
	public void OnGameEvent(GameBoard board, GameEvent event) {
		whitePlayerLabel.playerNameProperty.set(event.getFirstPlayerName());
		blackPlayerLabel.playerNameProperty.set(event.getSecondPlayerName());
		
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
	
	static class PlayerLabel extends HBox {
		private SimpleStringProperty playerNameProperty;
		private SimpleIntegerProperty playerPointsProperty;
		
		PlayerLabel(String name) {
			playerNameProperty = new SimpleStringProperty(name);
			playerPointsProperty = new SimpleIntegerProperty(2);
			
			Label nameLabel = new Label("Black: ");
			Label pointsLabel = new Label("0");
			
			nameLabel.textProperty().bind(playerNameProperty.concat(": "));
			pointsLabel.textProperty().bind(playerPointsProperty.asString());
			
			getChildren().addAll( nameLabel, pointsLabel);
		}
		
		StringProperty playerNameProperty() {
			return playerNameProperty;
		}
		
		IntegerProperty playerPointsProperty() {
			return playerPointsProperty;
		}
	}

	


	
	
}
