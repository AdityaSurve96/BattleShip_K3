package Exceptions;


/**
 * This Custom Exception Class Handles All the Grid Bound Exceptions 
 * @author K3
 *
 */
public class BeyondGridBoundsException extends Exception{
	public BeyondGridBoundsException(String s) {
		super(s);
	}
}
