package  pl.futuresoft.judo.backend.exception;

public class TokenExpiredException extends Exception {
	public TokenExpiredException()
	{
		super("Token jest niewazny");
	}
}
