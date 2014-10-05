package org.yvka.Beleg2.game;

import java.util.Arrays;
import java.util.List;

import org.yvka.Beleg2.game.GameBoard.Stone;

/**
 * <p>
 * Encapsulates the coordinates of a field position
 * and a direction of a othello game board.  
 * </p>
 * 
 * @author Yves Kaufmann
 *
 */
class FieldVector implements Cloneable {
	/**
	 * <p>
	 * List of possible game field directions 
	 * which should checked if a stone could be set.
	 * </p> 
	 */
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
	
	private final GameBoard gameboard;
	int row;
	int col;
	
	/**
	 * <p>
	 * Creates a FieldVector for a specified gameboard 
	 * with specific coordinates.  
	 * </p>
	 * 
	 * @param gameboard the gameboard on which this field vector should operate.
	 * @param row the row component of this vectot 
	 * @param col the column component of this vectot
	 */
	public FieldVector(GameBoard gameboard, int row, int col) {
		this.gameboard = gameboard;
		this.row = row;
		this.col = col;
	}
	
	/**
	 * <p>
	 * Returns the row component of this vector. 
	 * </p>
	 * 
	 * @return the row component of this vector.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * <p>
	 * Returns the column component of this vector. 
	 * </p>
	 * 
	 * @return the column component of this vector.
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * <p>
	 * Performs a vector addition of this 
	 * vector and a specified vector and saves
	 * the result in this instance.
	 * </p>
	 * @param vector the specified vector which should added to this vector.
	 */
	public void add(FieldVector vector) {
		this.row += vector.row;
		this.col += vector.col;
	}
	
	/**
	 * <p>
	 * Returns the content({@link Stone}) of the field which is pointed 
	 * by this vector.
	 *  </p>
	 *  
	 * @return the content of pointed game field.
	 */
	public Stone getField() {
		ensureIsInBound();
		return gameboard.fields[row][col];
	}
	
	/**
	 * <p>
	 * Set the content({@link Stone}) of the field which is pointed 
	 * by this vector.
	 * </p>
	 * 
	 * @param stone the new content of the field which is pointed by this vector..
	 */
	void setField(Stone stone) {
		ensureIsInBound();
		gameboard.fields[row][col] = stone;
	} 
	
	/**
	 * <p>
	 * Checks if this vector is in the bound of 
	 * the gameboard which is attached to this vector.
	 * </p>
	 * @return if this vector is in the bound of the gameboard.
	 */
	boolean isInBound() {
		int fieldSize = this.gameboard.fields.length;
		return (row >= 0 && row < fieldSize) && (col >= 0 && col < fieldSize) ;
	}
	
	/**
	 * <p>
	 * Ensures that this field vector is in the 
	 * bound of the attached game field.
	 * </p>
	 * 
	 * @throws IndexOutOfBoundsException if the field vector is out of bounds.
	 */
	public void ensureIsInBound() {
		if(!isInBound()) {
			throw new IndexOutOfBoundsException("The field [" + row + "," + col + "] dosn't exists.");
		}
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