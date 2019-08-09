package Exceptions;

/**
 * This Custom Exception Class Handles the Save button and checks for Appropriate actions. 
 * @author K3
 *
 */
public class InvalidButtonPressException extends Exception{
	public InvalidButtonPressException(String s) {
		super(s);
	}
}
