package server;

import javax.xml.ws.WebFault;

@WebFault(name="WrongInputException")
public class WrongInputException extends Exception{

	public WrongInputException (String cause) {
		super (cause);
	}
}
