package Exceptions;


/**
 * <p>This Custom Exception Class Handles All the Grid Bound Exceptions </p>
 * @author K3
 *
 */
public class BeyondGridBoundsException extends Exception{
	public BeyondGridBoundsException(String s) {
		super(s);
	}
}
