package org.yvka.Beleg2.gui;

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

/**
 * <p>
 * This class is responsible to display the fields for the stones <br>
 * and the rendering of the stones.<br>
 * <br>
 * Each stone field has state {@link Stone} which indicates <br>
 * how the the stone field should rendered. For example the <br>
 * the stone field with the state Stone.BLACK_STONE indicates <br>
 * that the stone field should render a field with a black stone. <br>
 * This behavior is the same for the other Stone states.
 * </p>
 * 
 * @author Yves Kaufmann
 */
public class StoneField extends Parent implements GameEventHandler {
	
	/**
	 * <p>
	 * A ClickHandler which should a client implement <br>
	 * to handle the click events on the StoneFields. <br>
	 * 
	 * </p>
	 * @author Yves Kaufmann
	 */
	public interface OnFieldClickHandler {
		/**
		 * Handle the click on a stone field.
		 * 
		 * @param field the field which was clicked by the user
		 * @param event the triggered mouse event.
		 */
		public void handle(StoneField field, MouseEvent event);
	}
	
	/**
	 * The default width and height in pixel of a stone field.
	 */
	public static final int FIELD_WIDTH = 50;
	
	private Stone fieldState;
	private Circle stone;
	private int row;
	private int col;
	private Player currentPlayer = null;
	
	/**
	 * <p>
	 * Create the stone field for a specified row and column index, <br>
	 * define if the field should rendered with a alternative color <br>
	 * and register the {@link OnFieldClickHandler} which should <br>
	 * handle the clicks on a stone field. <br>
	 * 
	 * </p>
	 * @param row the row index of this stone field.
	 * @param col the column index of this stone field.
	 * @param isAlternativeField define if the field should rendered with a alternative background color.
	 * @param clickHandler the {@link OnFieldClickHandler} which should handle the clicks on a stone field.
	 */
	public StoneField(int row, int col, boolean isAlternativeField, OnFieldClickHandler clickHandler) {
		
		this.row = row;
		this.col = col;
		
		Group root = new Group();
		Rectangle fieldRect = new Rectangle();
		if(isAlternativeField) {
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
	
	/**
	 * Returns the row index of this field.
	 * 
	 * @return the row index of this field.
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Returns the column index of this field.
	 * 
	 * @return the column index of this field.
	 */
	public int getCol() {
		return this.col;
	}
	
	/**
	 * <p>
	 * Toggles the state of the stone field which means <br> 
	 * a field with the state Stone.WHITE_STONE would get <br>
	 * the new state Stone.BLACK_STONE and vice versa.<br>
	 * <br>
	 * The state itself describes how the field should rendered<br>
	 * for more informations see the method {@link #setState(Stone)}.  
	 * </p>
	 */
	public void toggleState() {
		if(Stone.UNSET.equals(fieldState) || Stone.BLACK_STONE.equals(fieldState)) {
			setState(Stone.WHITE_STONE);
		} else {
			setState(Stone.BLACK_STONE);
		}
	}
	
	/**
	 * Sets the state of this stone field and renders the stone <br>
	 * field accordingly by the new state. The state itself <br> 
	 * describes how a stone field should rendered.<br>
	 * The following states are possible: <br>
	 * 
	 * <ul>
	 * 	<li>Stone.UNSET - the field should rendered without a stone.</li>
	 *  <li>Stone.UNSET_BUT_POSSIBLE_MOVE - the field should rendered without 
	 *  	a stone but should render a sign which indicates a move on this field is possible.</li>
	 *  <li>Stone.BLACK_STONE - the field should rendered with a black colored stone.</li>
	 *  <li>Stone.WHITE_STONE - the field should rendered with a white colored stone.</li>
	 * <ul>
	 * 	
	 * 
	 * @param state the new desired state.
	 */
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
	
	/**
	 * Return the current state of this stone field.
	 * 
	 * @return the state of the current field.
	 */
	public Stone getState() {
		return fieldState;
	}

	/**
	 * <p>
	 * A OnGameEventListener which is responsible <br> 
	 * to repaint the stone field when the {@link GameBoard} triggers
	 * a {@link GameEvent}. <br>
	 * </p>
	 */
	@Override
	public void OnGameEvent(GameEvent event) {
		GameBoard board = event.getSrcGameBoard();
		currentPlayer = board.getCurrentPlayer();
		setState(board.getStone(row, col));
	}
}
