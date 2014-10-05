package org.yvka.Beleg2.gui;
	
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
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.yvka.Beleg2.game.GameBoard;

/**
 * <p>
 * The main entry point of the java-fx based GUI implementation <br> 
 * of the Game Othello.<br>
 * <br>
 * The class is responsible for assemble the gui out of <br>
 * the {@link GameBoardUI} and the {@link GameControlPanel}.<br>
 * <br>
 * </p>
 * @author Yves Kaufmann
 *
 */
public class OthelloGuiApplication extends Application {
	
	/**
	 * The id of modal dimmer pane which can be used to style modal dimmer pane with css.
	 */
	public static final String MODAL_DIMMER_ID = "modalDimmer";
	/**
	 * The id of the game field pane which can be used to style game field pane with css.
	 */
	public static final String GAME_FIELD_ID = "gameField";
	/**
	 * The id of the control panel pane which can be used to style control panel pane with css.
	 */
	public static final String CONTROL_PANEL_ID = "controlPanel";
	
	private static OthelloGuiApplication instance = null;
	/**
	 * Convenient method for retrieving the instance of the OthelloGuiApplication.
	 * 
	 * @return the single instance of the OthelloGuiApllication.
	 */
	public static OthelloGuiApplication getInstance() {
		return instance;
	}
	
	/**
	 * Main entry points of the java-fx based GUI implementation for the game Othello 
	 * 
	 * @param args the application arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	private VBox root;
	private StackPane layeredPane;
	private GameBoardUI gameField;
	private GameControlPanel gameControlPanel;
	private Scene scene;
	private StackPane modalDimmer;

	@Override
	public void start(Stage primaryStage) {
		try {	
			// store the instance in the static instance methode
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
			gameField = new GameBoardUI(gameLogic);
			gameField.setId(GAME_FIELD_ID);
			
			gameControlPanel.setOnNewGameClicked((event) -> {				
				gameField.startNewGame();
			});

			root = new VBox();
			root.setId("root");
			root.getChildren().addAll(gameField, gameControlPanel);
			
			layeredPane.getChildren().addAll(root, modalDimmer);
			
			scene = new Scene(layeredPane);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(
				getClass().getResourceAsStream("icon.png")
			));
			primaryStage.sizeToScene();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Reversi");
			primaryStage.centerOnScreen();
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>
	 * Display a transparent black colored overlay <br>
	 * with a specified Node component.  <br>
	 * <br>
	 * For example the Overlay is used to display the result of a game.
	 *  </p>
	 * @param content the content node which should display on the overlay.
	 */
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
	
	/**
	 * Hide the modal dimmer.
	 */
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
