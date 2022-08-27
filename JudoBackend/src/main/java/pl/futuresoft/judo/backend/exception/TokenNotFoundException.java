package  pl.futuresoft.judo.backend.exception;

public class TokenNotFoundException extends Exception {
	public TokenNotFoundException()
	{
		super("No user found for such a link");
	}
}
