package Exceptions;

/**
 * This Custom Exception Class Handles UDP data sent between two Players.
 * This calls the super exception to print the message.
 * @author K3
 *
 */
public class DataNotSentException extends Exception{
	public DataNotSentException(String s) {
		super(s);
	}

}
