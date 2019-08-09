package Exceptions;

/**
 * <p>This Custom Exception Class Handles InvalidShipPlacements .If the ship is
 * not placed on the board or a invalid position. <p> 
 * @author K3
 *
 */
public class InvalidShipPlacementException extends Exception{

	public InvalidShipPlacementException(String s){  
		  super(s);  
	}  
}
