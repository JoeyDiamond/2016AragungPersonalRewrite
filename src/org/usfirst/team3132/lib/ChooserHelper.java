package org.usfirst.team3132.lib;

public interface ChooserHelper {
	
	/**
	 * generates the info in the smart dashboard
	 */
	public void create();
	
	/**
	 * retrieves the current selection from the smart dashboard
	 * @return String of current selection
	 */
	public Object getSelection();
}
