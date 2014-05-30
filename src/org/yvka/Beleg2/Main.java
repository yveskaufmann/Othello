package org.yvka.Beleg2;

import org.yvka.Beleg1.ui.IOTools;
import org.yvka.Beleg2.game.GameBoard;
import org.yvka.Beleg2.game.GameEvent;
import org.yvka.Beleg2.game.GameEventListener;

/**
 * Main entry point of this application.
 * 
 * @author Yves Kaufmann
 *
 */
public class Main implements GameEventListener{
	/**
	 * Just the main function which is the main entry point.
	 * 
	 * @param args the program arguments.
	 */
	public static void main(String[] args) {
		GameBoard field = new GameBoard();
		field.registerGameEventListener(new Main());
		field.startNewGame(8);
	}
	
	@Override
	public void OnGameEvent(GameBoard board, GameEvent event) {
		switch(event.getState()) {
			case NEW_GAME:
				System.out.println("Welcome to Othello.");
			case NEXT_TURN:
				System.out.println(board);
				for(;;) {
					int row = IOTools.readInteger("row: ");
					int col = IOTools.readInteger("col: ");
					try {
						if(board.setStone(row, col)) {
							break;
						}
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				break;
			case END_GAME:
				System.out.println(board);
				System.out.println("Gewinner: " + board.getCurrentPlayer().getName());
				System.exit(0);
			break;
			
		}		
	}
}
