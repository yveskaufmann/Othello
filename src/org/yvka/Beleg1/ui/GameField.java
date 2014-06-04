package org.yvka.Beleg1.ui;


import javafx.scene.Group;
import javafx.scene.layout.Region;


public class GameField extends Region {
	
	private static final int FIELD_GAP_WIDTH = 0;

	public GameField(int size) {
		setStyle("-fx-background-color: green;-fx-padding: 15;");
		setSize(size);		
	}
	
	public void setSize(int size) {
		Group root = new Group();
		root.relocate(30, 30);
		for(int row = 0; row  < size; row++) {
			for(int col = 0; col < size; col++) {
				boolean isAlternativeField = (row * size + col) % 2 == (size % 2 == 0 ? row % 2 : 0);
				Field field = new Field(row, col, isAlternativeField);
				field.relocate(
					col *(Field.FIELD_WIDTH + FIELD_GAP_WIDTH), 
					row * (Field.FIELD_WIDTH + FIELD_GAP_WIDTH));			
				root.getChildren().add(field);
			}
		}
		
		getChildren().clear();
		getChildren().add(root);
		autosize();
	}
}
