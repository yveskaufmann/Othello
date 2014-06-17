package org.yvka.Beleg1.gui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * <p>
 * Displays the count of stones for a assigned player.
 * <br>
 * A PlayerPointsLabel display a circle in the color of the assigned player. <br>
 * Furthermore this Pane shows the assigned count of stones of the assigned player.
 * </p>
 * @author Yves Kaufmann
 *
 */
class PlayerPointsLabel extends HBox {

	private SimpleIntegerProperty playerPointsProperty;
	private Label playerIconLabel;
	private Label pointsLabel;
	private Circle stoneIcon;
	private Timeline blinkAnimation = null;
	private Color colorOfStone = null;
	
	
	/**
	 * Create a PointsLabel and assign the color of the circle 
	 * the current player.
	 *  
	 * @param stoneColor the color of the assigned player stones.
	 */
	PlayerPointsLabel(Color stoneColor) {
		playerPointsProperty = new SimpleIntegerProperty(2);
		colorOfStone = stoneColor;
		
		stoneIcon = new Circle(10, stoneColor);
		stoneIcon.setEffect(new DropShadow());
		stoneIcon.setVisible(true);
		
		playerIconLabel = new Label(" X ", stoneIcon);
		pointsLabel = new Label("0");
	
		pointsLabel.textProperty().bind(playerPointsProperty.asString());
		
		getChildren().addAll( playerIconLabel, pointsLabel);
		setAlignment(Pos.CENTER_RIGHT);
	}
	
	/**
	 * Returns the player points property which can be used
	 * to update the count of points display of the assigned user.
	 * 
	 * @return the player points property
	 */
	IntegerProperty playerPointsProperty() {
		return playerPointsProperty;
	}

	/**
	 * <p>
	 * Set the assigned user of this {@link PlayerPointsLabel} as current user,<br> 
	 * which triggers a animation of the circle which indicates<br>
	 * that the current user is on turn. <br>
	 * </p>
	 * 
	 * @param isCurrentPlayer then the circle animation is started otherwise the animation is stopped.
	 */
	void setAsCurrentPlayer(boolean isCurrentPlayer) {
		if(blinkAnimation != null) { 
			blinkAnimation.stop();
		}
		
		stoneIcon.setScaleX(1.0);
		stoneIcon.setFill(colorOfStone);
		
		blinkAnimation = new Timeline(
			new KeyFrame(Duration.millis(700),
				new KeyValue(stoneIcon.scaleXProperty(), -1.0, Interpolator.EASE_BOTH)
			)
		);
		
		if(isCurrentPlayer) {
			blinkAnimation.setAutoReverse(true);
			blinkAnimation.setCycleCount(Timeline.INDEFINITE);
			blinkAnimation.play();
		} 
	}
}