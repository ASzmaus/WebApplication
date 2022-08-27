package pl.futuresoft.judo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT, reason = "Login is used")
public class LoginIsUsedException extends RuntimeException {

}
