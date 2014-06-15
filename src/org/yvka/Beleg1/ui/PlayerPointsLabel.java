package org.yvka.Beleg1.ui;

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

class PlayerPointsLabel extends HBox {
	SimpleIntegerProperty playerPointsProperty;
	private Label playerIconLabel;
	private Label pointsLabel;
	private Circle stoneIcon;
	private Timeline blinkAnimation = null;
	private Color colorOfStone = null;
	
	
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
	
	IntegerProperty playerPointsProperty() {
		return playerPointsProperty;
	}
	

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