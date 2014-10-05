package org.yvka.Beleg2.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import org.yvka.Beleg2.game.Player;

/**
 * <p>
 * This class provides a notification which should be displayed at the end of the game.<br>
 * <br>
 * The GameEndNotification tells the players who win the game <br>
 * and how much points each player earned in the game.<br>
 * <br>
 * </p>
 * 
 * @author Yves Kaufmann
 *
 */
public class GameEndNotification extends BorderPane {
	
	/**
	 * The id of this border pane which can be used to style this border pane with css.
	 */
	public static final String GAME_END_NOTIFICATION_ID = "gameEndNotification";
	
	private Player whitePlayer;
	private Player blackPlayer;
	private Label gameOverLabel;
	private Label whitePlayerLabel;
	private Label blackPlayerLabel;
	private Label pressToContinueLabel;
	private Label blackPoints;
	private Label whitePoints;
	
	/**
	 * <p>
	 * Creates a GameEndNotification which provides some information<br>
	 * about a specified game. <br>
	 * The GameEndNotification tells the players who win the game <br>
	 * and how much points each player earned in the game.<br>
	 * <br>
	 * </p>
	 * 
	 * @param firstPlayer the specified first Player
	 * @param secondPlayer the specified second Player
	 * @param onContinuePressed the EventHandler<Event> which describes what should happen
	 * 		 if the user touch or click anywhere.
	 */
	public GameEndNotification(Player firstPlayer, Player secondPlayer, EventHandler<Event> onContinuePressed) {
		whitePlayer = firstPlayer;
		blackPlayer = secondPlayer;
		
	
		gameOverLabel = new Label(generateGameOverLabelText());
		gameOverLabel.setFont(new Font(50));
		gameOverLabel.setStyle("-fx-stroke: black;-fx-stroke-width: 4;");
		gameOverLabel.setEffect(new DropShadow(10, Color.WHITE));
		gameOverLabel.setTextFill(Color.WHITE);
		gameOverLabel.setAlignment(Pos.CENTER);
		gameOverLabel.setPadding(new Insets(40, 0, 50, 0));
		
		
		VBox leftColumn = new VBox();
		VBox rightColumn = new VBox();
		
		whitePlayerLabel = new Label(whitePlayer.getName());
		whitePlayerLabel.setTextFill(Color.WHITE);
		whitePlayerLabel.setFont(new Font(30));
		
		whitePoints = new Label(firstPlayer.getCountOfStones() + "");
		whitePoints.setTextFill(Color.WHITE);
		whitePoints.setFont(new Font(20));
		
		leftColumn.setAlignment(Pos.CENTER);
		leftColumn.setPadding(new Insets(60, 0, 50, 80));
		leftColumn.getChildren().addAll(whitePlayerLabel, whitePoints);
		
		blackPlayerLabel = new Label(blackPlayer.getName());
		blackPlayerLabel.setTextFill(Color.WHITE);
		blackPlayerLabel.setFont(new Font(30));
		
		blackPoints = new Label(secondPlayer.getCountOfStones() + "");
		blackPoints.setTextFill(Color.WHITE);
		blackPoints.setFont(new Font(20));
		blackPoints.setAlignment(Pos.CENTER);
		
		rightColumn.setAlignment(Pos.CENTER);
		rightColumn.setPadding(new Insets(60, 80, 50, 0));
		rightColumn.getChildren().addAll(blackPlayerLabel, blackPoints);
		
		pressToContinueLabel = new Label("Click here for continue.");
		pressToContinueLabel.setPadding(new Insets(0, 0, 100, 0));
		pressToContinueLabel.setFont(new Font(20));
		pressToContinueLabel.setTextFill(Color.WHITE);
		
		BorderPane root = new BorderPane();
		
		root.setTop(gameOverLabel);
		BorderPane.setAlignment(gameOverLabel, Pos.CENTER);
		
		root.setLeft(leftColumn);
		BorderPane.setAlignment(leftColumn, Pos.BASELINE_LEFT);
		
		root.setRight(rightColumn);
		BorderPane.setAlignment(rightColumn, Pos.BASELINE_RIGHT);
		
		root.setBottom(pressToContinueLabel);
		BorderPane.setAlignment(pressToContinueLabel, Pos.BOTTOM_CENTER);
		
		setId(GAME_END_NOTIFICATION_ID);
		setCenter(root);
		
		if(onContinuePressed != null) {
			setOnMouseClicked(onContinuePressed);
			setOnTouchPressed(onContinuePressed);
		}
		
	}
	
	/**
	 * Generates the GameOver message which can be either draw 
	 * or any player wins.
	 *
	 * @return the GameOver message.
	 */
	private String generateGameOverLabelText() {
		int pointsComparedFlag = Integer.compare(whitePlayer.getCountOfStones(), blackPlayer.getCountOfStones());
		
		if(pointsComparedFlag == 0) {
			return "Draw!";
		}
		
		return String.format("%s wins!", 
				(pointsComparedFlag >= 1) ? whitePlayer.getName() : blackPlayer.getName());
	}
	
}
