package org.yvka.Beleg1.ui;
	
import org.yvka.Beleg2.game.GameBoard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OthelloApplication extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			GameBoard gameLogic = new GameBoard();
			GameField gameField = new GameField(gameLogic);
			
			GameControlPanel gameControlPanel = new GameControlPanel(gameLogic);
			gameControlPanel.gameFieldSizeProperty().addListener(( observable, oldValue, newValue) -> {
				gameField.setSize(newValue);
				primaryStage.sizeToScene();
				primaryStage.centerOnScreen();
			}); 
			
			gameControlPanel.setOnNewGameClicked((event) -> {
				gameField.startNewGame();
			});
			
			VBox root = new VBox();
			root.getChildren().addAll(
				gameField, gameControlPanel
			);
				
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Reversi");
			primaryStage.centerOnScreen();
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
