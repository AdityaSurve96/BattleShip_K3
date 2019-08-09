package Exceptions;

/**
 * <p>This Custom Exception Class Handles Null Ship Exception.
 * If the ship is present at a particular cell or not .
 * If not then the exception is raised.</p>
 * @author K3
 *
 */
public class ShipNullCheckException extends Exception{
		public ShipNullCheckException(String s) {
			super(s);
		}
}
