package org.yvka.Beleg2.game;

/**
 * <p>
 * EventHandler which should handle the game events
 * and decouples the logic from the user interface.<br>
 * <br>
 * For example a implementation of a event handler could redraw the game field.
 * </p>
 * 
 * @author Yves Kaufmann
 *
 */
public interface GameEventHandler {
	
	/**
	 * Handles a occurred game event. 
	 * 
	 * @param event the occurred game event.
	 */
	public void OnGameEvent(GameEvent event);
}
