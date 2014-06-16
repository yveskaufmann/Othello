package org.yvka.Beleg1.gui;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import org.yvka.Beleg2.game.GameBoard;
import org.yvka.Beleg2.game.GameBoard.Stone;
import org.yvka.Beleg2.game.GameEvent;
import org.yvka.Beleg2.game.GameEventHandler;
import org.yvka.Beleg2.game.Player;

public class StoneField extends Parent implements GameEventHandler {
	
	public interface OnFieldClickHandler {
		public void handle(StoneField field, MouseEvent event);
	}
	
	public static final int FIELD_WIDTH = 50;
	private Stone fieldState;
	private Circle stone;
	private int row;
	private int col;
	private Player currentPlayer = null;
	
	public StoneField(int row, int col, boolean b, OnFieldClickHandler clickHandler) {
		
		this.row = row;
		this.col = col;
		
		Group root = new Group();
		Rectangle fieldRect = new Rectangle();
		if(b) {
			fieldRect.setFill(Color.web("0x357e1c"));
		} else {
			fieldRect.setFill(Color.web("0x2b6a16"));
		}

		fieldRect.setWidth(FIELD_WIDTH);
		fieldRect.setHeight(FIELD_WIDTH);
		fieldRect.setStroke(Color.BLACK);
		fieldRect.setStrokeWidth(2.0);

		fieldRect.setOnMouseClicked((event) -> {
			if( clickHandler != null ) {
				clickHandler.handle(this, event);
			}
		});
	
		stone = new Circle(FIELD_WIDTH / 2.5, Color.WHITE );
		stone.setCenterX( fieldRect.getX() + fieldRect.getWidth() / 2);
		stone.setCenterY( fieldRect.getY() + fieldRect.getHeight() / 2);
		stone.setVisible(false);
		stone.setMouseTransparent(true);
		
		root.getChildren().addAll(fieldRect, stone);
		setState(Stone.UNSET);
		getChildren().add(root);
		
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void toogleState() {
		if(Stone.UNSET.equals(fieldState) || Stone.BLACK_STONE.equals(fieldState)) {
			setState(Stone.WHITE_STONE);
		} else {
			setState(Stone.BLACK_STONE);
		}
	}
	
	public void setState(Stone state) {
		this.fieldState = state;
		stone.setEffect(null);
		stone.setOpacity(1.0);
		
		switch(state) {
			case UNSET:
				stone.setVisible(false);
			break;
			case UNSET_BUT_POSSIBLE_MOVE:
				Glow glow = new Glow(0.1);	
				InnerShadow shadow = new InnerShadow(20, 5, 1, new Color(0, 0, 0, .5));
				glow.setInput(shadow);
				stone.setEffect(glow);
				stone.setFill( currentPlayer == null || Stone.WHITE_STONE.equals(currentPlayer.getStoneColor()) ? Color.WHITE : Color.BLACK);
				stone.setOpacity(0.2);
				stone.setVisible(true);
			break;
			case BLACK_STONE:
				stone.setVisible(true);
				stone.setEffect(new InnerShadow(20, 5, 1, new Color(1.0, 1.0, 1.0, .5)));
				stone.setFill(Color.BLACK);
			break;
			case WHITE_STONE:
				stone.setVisible(true);
				stone.setEffect(new InnerShadow(20, 5, 1, new Color(0, 0, 0, .5)));
				stone.setFill(Color.WHITE);
			break;
		}
	}
	
	public Stone getState() {
		return fieldState;
	}

	@Override
	public void OnGameEvent(GameEvent event) {
		GameBoard board = event.getSrcGameBoard();
		currentPlayer = board.getCurrentPlayer();
		setState(board.getStone(row, col));
	}
}