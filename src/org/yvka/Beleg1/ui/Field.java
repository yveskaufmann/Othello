package org.yvka.Beleg1.ui;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Field extends Parent {
	public static final int FIELD_WIDTH = 50;
	public enum State {
		UNSET,
		UNSET_BUT_POSSIBLE_MOVE,
		BLACK_STONE,
		WHITE_STONE
	}
	private State state;
	private Circle stone;
	
	public Field(int row, int col, boolean b) {
		
		Group root = new Group();
		Rectangle rect = new Rectangle();
		if(b) {
			rect.setFill(Color.web("0x357e1c"));
		} else {
			rect.setFill(Color.web("0x2b6a16"));
		}

		rect.setWidth(FIELD_WIDTH);
		rect.setHeight(FIELD_WIDTH);
		rect.setStroke(Color.BLACK);
		rect.setStrokeWidth(2.0);
		
		
		 Light.Point light = new Light.Point();
		 light.setX(100);
		 light.setY(40);
		 light.setZ(600);
		 light.setColor(Color.GREEN.desaturate());
		
		 Lighting lighting = new Lighting();
		 lighting.setLight(light);
		 lighting.setSurfaceScale(3.0);
		rect.setEffect(lighting);
		
		
		stone = new Circle(FIELD_WIDTH / 2.5, Color.WHITE );
		stone.setCenterX( rect.getX() + rect.getWidth() / 2);
		stone.setCenterY( rect.getY() + rect.getHeight() / 2);
		stone.setVisible(false);
		stone.setMouseTransparent(true);
		rect.setOnMouseClicked((event) -> {
			System.out.println("clicked");
			toogleState();
		});
		
		rect.setOnMouseEntered((event) -> {
			if(!State.UNSET.equals(state)) return;
			
			InnerShadow shadow = new InnerShadow();
			shadow.setOffsetX(-2);
			shadow.setOffsetY(4);
			shadow.setBlurType(BlurType.TWO_PASS_BOX);
			shadow.setRadius(6);
			
			// new InnerShadow(20, 5, 1, new Color(0, 0, 0, .5))
			
			stone.setVisible(true);
			stone.setOpacity(1.0);
			stone.setEffect(shadow);
			stone.setFill(new LinearGradient(0.6, 0.25, 0.6, 20, true, CycleMethod.NO_CYCLE, 
				new Stop(0.0, Color.GREEN),
				new Stop(0.5, new Color(1.0, 1.0, 1.0, 0))
			));
			
			
			
		});
		
		rect.setOnMouseExited((event) -> {
			stone.setVisible(!State.UNSET.equals(state));
			stone.setOpacity(1.0);
			rect.setEffect(null);
		});
		
		root.getChildren().addAll(rect, stone);
		setState(State.UNSET);
		getChildren().add(root);
		
	}
	
	public void toogleState() {
		if(State.UNSET.equals(state) || State.BLACK_STONE.equals(state)) {
			setState(State.WHITE_STONE);
		} else {
			setState(State.BLACK_STONE);
		}
	}
	
	public void setState(State state) {
		this.state = state;
		switch(state) {
			case UNSET:
			case UNSET_BUT_POSSIBLE_MOVE:
				stone.setVisible(false);
			break;
			case BLACK_STONE:
				stone.setVisible(true);
				stone.setFill(Color.BLACK);
			break;
			case WHITE_STONE:
				stone.setVisible(true);
				stone.setFill(Color.WHITE);
			break;
		}
	}
	
	public State getState() {
		return state;
	}
}
