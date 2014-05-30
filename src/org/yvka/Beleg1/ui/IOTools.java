package org.yvka.Beleg1.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.StringTokenizer;

/**
 * <p>
 * This class provides convenience methods for reading from the keyboard. <br>
 * <br>
 * It can be read several values from the keyboard,<br> 
 * which must be separated by a space, a tab stop or a newline character.<br>
 * <br>
 * 
 * </p>
 * @author Yves Kaufmann
 *
 */
public class IOTools {

	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));

	private static StringTokenizer input;
	private static String string;

	
	/**
	 * Read a integer number from the console and ensure that
	 * the number is in the is between min and max.
	 * 
	 * @param prompt the string which is display as prompt.
	 * @param min the minimum value of the desired number.
	 * @param max the maximum value of the desired number.
	 * @return the entered number.
	 */
	public static int readNumberInRange(String prompt, int min, int max ) {
		int size = 0; 
		boolean isVaidInput = false;
		while(!isVaidInput) {
			size = IOTools.readInteger(
				String.format("%s [%d-%d] : ",prompt, min, max)
			);
			isVaidInput = size >= min && size <= max;
			if(!isVaidInput) {
				System.out.println("The specified size is out of the range [1-7].");
				System.out.println("Please repeat the input ...");
			}
		}
		return size;
	}
	
	/**
	 *  Deletes all in the current line.
	 */
	public static void flush() {
		input = null;
	}

	private static void init() {
		string = null;
		if ((input != null) && (input.hasMoreTokens()))
			return;
		while ((input == null) || (!input.hasMoreTokens())) {
			input = new StringTokenizer(readLine());
		}
	}

	private static void error(Exception paramException, String paramString) {
		System.out.println("Input error " + paramException);
		System.out.println("Please repeat the input ...");
		System.out.print(paramString);
	}

	/**
	 * Reads a line from the keyboard.
	 * 
	 * @param paramString the prompt for the user.
	 * @return the readed line.
	 */
	public static String readLine(String paramString) {
		flush();
		String str = "";
		System.out.print(paramString);
		try {
			str = in.readLine();
		} catch (IOException localIOException) {
			System.err.println(localIOException
					+ "\n Program terminated...\n");
			System.exit(1);
		}
		if (str == null) {
			System.err
					.println("Reached end of file.\nProgram terminated...\n");
			System.exit(1);
		}
		return str;
	}
	
	/**
	 * Read a integer value from the keyboard.
	 * 
	 * @param paramString the prompt for the user.
	 * @return the entered integer value.
	 */
	public static int readInteger(String paramString) {
		System.out.print(paramString);
		init();
		for (;;) {
			try {
				return Integer.parseInt(input.nextToken());
			} catch (NumberFormatException localNumberFormatException) {
				error(localNumberFormatException, paramString);
			}
			init();
		}
	}
	
	/**
	 * Reads a long value from the keyboard.
	 * 
	 * 
	 * @param paramString the prompt for the user.
	 * @return the entered long value.
	 */
	public static long readLong(String paramString) {
		System.out.print(paramString);
		init();
		for (;;) {
			try {
				return Long.parseLong(input.nextToken());
			} catch (NumberFormatException localNumberFormatException) {
				error(localNumberFormatException, paramString);
			}
			init();
		}
	}
	
	/**
	 * Reads a double value from the keyboard.
	 * 
	 * 
	 * @param paramString the prompt for the user.
	 * @return the entered double value.
	 */
	public static double readDouble(String paramString) {
		System.out.print(paramString);
		init();
		for (;;) {
			try {
				return Double.valueOf(input.nextToken()).doubleValue();
			} catch (NumberFormatException localNumberFormatException) {
				error(localNumberFormatException, paramString);
			}
			init();
		}
	}
	
	/**
	 * Reads a float value from the keyboard.
	 * 
	 * 
	 * @param paramString the prompt for the user.
	 * @return the entered float value.
	 */
	public static float readFloat(String paramString) {
		System.out.print(paramString);
		init();
		for (;;) {
			try {
				return Float.valueOf(input.nextToken()).floatValue();
			} catch (NumberFormatException localNumberFormatException) {
				error(localNumberFormatException, paramString);
			}
			init();
		}
	}
	
	/**
	 * Reads a short value from the keyboard.
	 * 
	 * 
	 * @param paramString the prompt for the user.
	 * @return the entered short value.
	 */
	public static short readShort(String paramString) {
		System.out.print(paramString);
		init();
		for (;;) {
			try {
				return Short.valueOf(input.nextToken()).shortValue();
			} catch (NumberFormatException localNumberFormatException) {
				error(localNumberFormatException, paramString);
			}
			init();
		}
	}
	
	/**
	 * Reads a boolean value from the keyboard.
	 * 
	 * 
	 * @param paramString the prompt for the user.
	 * @return the entered boolean value.
	 */
	public static boolean readBoolean(String paramString) {
		String str = readString(paramString);
		while ((!str.equals("true")) && (!str.equals("false")))
			str = readString();
		return str.equals("true");
	}

	/**
	 * Reads a string value from the keyboard.
	 * 
	 * 
	 * @param paramString the prompt for the user.
	 * @return the entered string value.
	 */
	public static String readString(String paramString) {
		System.out.print(paramString);
		init();
		return input.nextToken();
	}
	
	/**
	 * Reads a char value from the keyboard.
	 * 
	 * 
	 * @param paramString the prompt for the user.
	 * @return the entered char value.
	 */
	public static char readChar(String paramString) {
		System.out.print(paramString);
		if ((string == null) || (string.length() == 0))
			string = readString("");
		char c = string.charAt(0);
		string = string.length() > 1 ? string.substring(1)
				: null;
		return c;
	}
	
	/**
	 * Read a line value from the keyboard.
	 * @return the entered line.
	 */
	public static String readLine() {
		return readLine("");
	}
	
	/**
	 * Read a integer value from the keyboard.
	 * @return the entered integer.
	 */
	public static int readInteger() {
		return readInteger("");
	}
	
	/**
	 * Read a long value from the keyboard.
	 * @return the entered long value.
	 */
	public static long readLong() {
		return readLong("");
	}
	
	/**
	 * Read a double value from the keyboard.
	 * @return the entered double value.
	 */
	public static double readDouble() {
		return readDouble("");
	}
	
	/**
	 * Read a short value from the keyboard.
	 * @return the entered short value.
	 */
	public static short readShort() {
		return readShort("");
	}
	
	/**
	 * Read a float value from the keyboard.
	 * @return the entered float value.
	 */
	public static float readFloat() {
		return readFloat("");
	}
	
	/**
	 * Read a char value from the keyboard.
	 * @return the entered char value.
	 */
	public static char readChar() {
		return readChar("");
	}
	
	/**
	 * Read a string value from the keyboard.
	 * @return the entered string value.
	 */
	public static String readString() {
		return readString("");
	}

	/**
	 * Read a boolean value from the keyboard.
	 * @return the entered boolean value.
	 */
	public static boolean readBoolean() {
		return readBoolean("");
	}
	
	/**
	 * Create the string representation of a specified double value.
	 * 
	 * @param paramDouble the specified double value.
	 * @return the string representation of 'paramDouble'.
	 */
	public static String toString(double paramDouble) {
		if ((Double.isInfinite(paramDouble)) || (Double.isNaN(paramDouble)))
			return String.valueOf(paramDouble);
		return new BigDecimal(paramDouble).toString();
	}
}
