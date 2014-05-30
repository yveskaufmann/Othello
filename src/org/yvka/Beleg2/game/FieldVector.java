package org.yvka.Beleg2.game;

import java.util.Arrays;
import java.util.List;

import org.yvka.Beleg2.game.GameBoard.Stone;

class FieldVector implements Cloneable {

	public final static List<FieldVector> DIRECTIONS = Arrays.asList(
		// Left
		new FieldVector(null, 0, -1),
		// Right
		new FieldVector(null, 0, 1),
		// Top
		new FieldVector(null, -1, 0),
		// Top Left
		new FieldVector(null, -1, -1),
		// Top Right
		new FieldVector(null, -1, 1),
		// Bottom
		new FieldVector(null, 1, 0),
		// Bottom Left
		new FieldVector(null, 1, -1),
		// Bottom Right
		new FieldVector(null, 1, 1)
		
	);
	
	private final GameBoard gamefield;
	int row;
	int col;
	
	public FieldVector(GameBoard gamefield, int row, int col) {
		this.gamefield = gamefield;
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void add(FieldVector vector) {
		this.row += vector.row;
		this.col += vector.col;
	}
	
	Stone getField() {
		ensureIsInBound();
		return gamefield.fields[row][col];
	}
	
	void setField(Stone stone) {
		ensureIsInBound();
		gamefield.fields[row][col] = stone;
	} 
	
	boolean isInBound() {
		int fieldSize = this.gamefield.fields.length;
		return (row >= 0 && row < fieldSize) && (col >= 0 && col < fieldSize) ;
	}
	
	@Override
	protected Object clone() {
		try {
			return super.clone();
		} catch(CloneNotSupportedException ex) {}
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("row = %d, col=%d", row, col);
	}
	
	public void ensureIsInBound() {
		if(!isInBound()) {
			throw new IndexOutOfBoundsException("The field [" + row + "," + col + "] dosn't exists.");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldVector other = (FieldVector) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}