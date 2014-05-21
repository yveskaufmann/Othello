package org.yvka.Beleg2.utils;

/**
 * <p>
 * The StringUtil provides methods for common string operation.
 * <br>
 * 
 * </p>
 * @author Yves Kaufmann
 * @see String
 */
public class StringUtil {
   
   /**
    * The line separator constant which is used to create system independent strings.
    */
   public static final String LINE_SEPERATOR = System.lineSeparator();
	
   /**
    * <p>
    * Wraps a specified string with vertical lines and prepends a specified title.<br> 
    * <br>
    * <code>
    * For instance the string "1 2 3  will be converted to "| 1 2 3 |.
    *  				           4 5 6" 						| 4 5 6 |
    * </code>
    * </p>
    * @param content the specified string
    * @param title the title of this matrix
    * @return the 'content' which is wrapped with vertical lines. 
    */
   public static String wrapWithVerticalBorders(String content, String title) {
	   StringBuilder str = new StringBuilder();
	   String label =  title + " = ";
	   String indent = repeatString(" ", label.length());
	   String[] lines = content.split(LINE_SEPERATOR);
	   
	   for(int i = 0; i < lines.length; i++) {
		   lines[i] = (i == 0 ? label : indent) + "|" + lines[i] + "|";
		   str.append(lines[i]).append(LINE_SEPERATOR);
	   }
	 
	  return  str.toString();
   }
     
	/**
	 * <p>
	 * Generates a horizontal string line with a specified length. <br>
	 * 
	 * </p>
	 * @param length the length of the desired string line.
	 * @return the generated line.
	 */
	public static String createLine(int length) {
		return repeatString( "-", length);
	}
	
	/**
	 * <p>
	 * Repeat a String <code>str</code> times to create a new string.<br>
	 * 
	 * </p>
	 * @param str the String to repeat.
	 * @param times the number of times to repeat str.
	 * @return the new string which contains <code>str</code> repeated.
	 * @throws IllegalArgumentException if times &lt;= 0
	 */
	public static String repeatString(String str, int times ) {
		if(times <= 0) {
			throw new IllegalArgumentException("Invalid times: times must be > 0");
		}
		StringBuilder strBuilder = new StringBuilder();
		while(--times >= 0) strBuilder.append(str);
		return strBuilder.toString();
	}
}
