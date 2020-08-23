package main.java.app;

import java.util.Map;

/**
 * Checks if the FxmlData object supports options
 * TODO: Is this really needed?
 */

public interface Optionable {
	
	public Map<String, String> getOptions();
	
	/** @param key the key of the element to update or insert
	 *  @param value actual value of the element */
	public void setOption(String key, String value);
}
