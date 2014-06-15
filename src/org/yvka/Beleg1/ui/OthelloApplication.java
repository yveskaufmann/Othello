package org.yvka.Beleg1.ui;
	

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.yvka.Beleg2.game.GameBoard;

public class OthelloApplication extends Application {
	

	
	public static final String MODAL_DIMMER_ID = "modalDimmer";
	public static final String GAME_FIELD_ID = "gameField";
	public static final String CONTROL_PANEL_ID = "controlPanel";
	
	private static OthelloApplication instance = null;
	public static OthelloApplication getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	private VBox root;
	private StackPane layeredPane;
	private GameField gameField;
	private GameControlPanel gameControlPanel;
	private Scene scene;
	private StackPane modalDimmer;

	@Override
	public void start(Stage primaryStage) {
		try {	
			instance = this;
			layeredPane = new StackPane();
			layeredPane.setDepthTest(DepthTest.DISABLE);
			
			modalDimmer = new StackPane();
			modalDimmer.setId(MODAL_DIMMER_ID);
			modalDimmer.setVisible(false);
			modalDimmer.setOnMouseClicked((event) -> {
				event.consume();
				hideModalDimmer();
			});
			
			GameBoard gameLogic = new GameBoard();
			gameControlPanel = new GameControlPanel(gameLogic);
			gameControlPanel.setId(CONTROL_PANEL_ID);
			gameControlPanel.gameFieldSizeProperty().addListener(( observable, oldValue, newValue) -> {
				gameField.setSize(newValue);
				primaryStage.sizeToScene();
				primaryStage.centerOnScreen();
			});
			gameField = new GameField(gameLogic);
			gameField.setId(GAME_FIELD_ID);
			
			gameControlPanel.setOnNewGameClicked((event) -> {
				
showModalDimmer(new GameEndNotification(gameLogic.getFirstPlayer(), gameLogic.getSecondPlayer(), null));
				gameField.startNewGame();
			});

			root = new VBox();
			root.setId("root");
			root.getChildren().addAll(gameField, gameControlPanel);
			
			layeredPane.getChildren().addAll(root, modalDimmer);
			
			scene = new Scene(layeredPane);
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
	
	public void showModalDimmer(Node content) {
		modalDimmer.setOpacity(0);
		modalDimmer.getChildren().add(content);
		modalDimmer.setVisible(true);
		modalDimmer.setCache(true);
		
		
		EventHandler<ActionEvent> onAnimationFinished = (event) -> {
			modalDimmer.setCache(false);
		};
		
		Timeline transitionAnimation = new Timeline(
			new KeyFrame(
				new Duration(500), 
				onAnimationFinished ,
				new KeyValue(modalDimmer.opacityProperty(), 1.0, Interpolator.EASE_BOTH)
			)
		);
		transitionAnimation.play();
	}
	
	public void hideModalDimmer() {
		modalDimmer.setCache(true);
		EventHandler<ActionEvent> onAnimationFinished = (event) -> {
			modalDimmer.setCache(false);
			modalDimmer.setVisible(false);
			modalDimmer.getChildren().clear();
		};
		
		Timeline transitionAnimation = new Timeline(
			new KeyFrame(
				new Duration(500), 
				onAnimationFinished ,
				new KeyValue(modalDimmer.opacityProperty(), 0.0, Interpolator.EASE_BOTH)
			)
		);
		transitionAnimation.play();
	}
	
}
