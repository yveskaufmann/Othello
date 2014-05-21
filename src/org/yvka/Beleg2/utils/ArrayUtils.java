package org.yvka.Beleg2.utils;



/**
 * Common used array utility methods.
 * @author Yves Kaufmann
 *
 */
public class ArrayUtils {
	
	/**
	 * Just a simple data structure for the dimension of a array.
	 * The Purpose of this class is only for convenience transferring between methods.
	 * @author Yves Kaufmann
	 *
	 */
	public static class ArrayDimensions {
		/**
		 * The number of columns of an array.
		 */
		public int cols;
		/**
		 * The number of rows of an array.
		 */
		public int rows;
		
		private ArrayDimensions() {
			cols = 0;
			rows = 0;
		}
		
		/**
		 * Checks if the dimension is empty.
		 * 
		 * @return <code>true</code> if the dimension is empty.
		 */
		public boolean isEmpty() {
			return cols <= 0 || rows <= 0;
		}
		
		/**
		 * Ensure that the dimension not describe a empty array.
		 * Otherwise a Exception will be thrown.
		 */
		public void ensureIsNotEmpty() {
			if(this.isEmpty()) {
				throw new IllegalArgumentException(
					String.format("Invalid array: empty [%d,%d]", rows, cols) //$NON-NLS-1$
				);
			}
		}
		
		/**
		 * Determine if the specified row and column index is inside of the specified array.
		 * @param rowIndex the index for the row.
		 * @param colIndex the index for the column.
		 * @return <code>true</code> if the indexes are inside the array.
		 */
		public boolean isIndexInBound(int rowIndex, int colIndex) {
			return rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < cols;
		}
	}
	
	/**
	 * Disables the default constructor because this class is for static usage only.
	 */
	protected ArrayUtils() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Returns true if the specified array is empty.
	 * <br>
	 * @param array the specified array.
	 * @return Returns <code>true</code> if the specified array is empty
	 */
	public static boolean isEmpty(double [][]array) {
		ArrayDimensions dim = getDimension(array); 
		return dim.isEmpty();
		
	}
	
	/**
	 * Determines the dimension of a specified array.
	 * <br>
	 * @param array the specified array.
	 * @return the dimension of the array.
	 */
	public static ArrayDimensions getDimension(double[][] array) {
		ArrayDimensions dim = new ArrayDimensions();
		dim.rows = array != null ? array.length : 0;
		dim.cols = (dim.rows > 0 && array[0].length > 0)? array[0].length : 0;
		return dim;
	}
	
	/**
	 * Returns true if the specified index is in side the specified array.<br>
	 * <br>
	 * @param array The specified array.
	 * @param rowIndex the row Index which should be checked.
	 * @param colIndex the col Index which should be checked.
	 * @return Returns <code>true</code> if the specified index is inside this array.
	 */
	public static boolean isIndexInBound(double[][] array, int rowIndex, int colIndex) {
		ArrayDimensions dim = getDimension(array);
		return dim.isIndexInBound(rowIndex,colIndex);
	}
	
	/**
	 * <p>
	 * Switch two rows of a specified 2d Array.<br>
	 * </p>
	 * @param array  the array on which this operation should work.
	 * @param rowIndex1 the index of the row which should switch with row at rowIndex2.
	 * @param rowIndex2 the index of the row which should switch with row at rowIndex1.
	 * @return the specified array instance with the swapped rows. 
	 * 
	 */
	public static double[][] swapRows(double[][] array, int rowIndex1, int rowIndex2) {
		
		if(!(isIndexInBound(array, rowIndex1, 0) && isIndexInBound(array, rowIndex2, 0))) {
			throw new IllegalArgumentException(
					String.format("The rowIndexes are out of bounds [%d,%d] but the array have only %d rows.", rowIndex1, rowIndex2, array.length)
			);
		};
		
		if( rowIndex1 != rowIndex2 ) {
			double []tmp = array[rowIndex1];
			array[rowIndex1] = array[rowIndex2];
			array[rowIndex2] = tmp;
		}
		
		return array;
	}
 }
