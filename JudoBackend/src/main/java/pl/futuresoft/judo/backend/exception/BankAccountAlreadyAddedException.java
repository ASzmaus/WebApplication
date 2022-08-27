package pl.futuresoft.judo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT, reason = "BankAccount has been already added")
public class BankAccountAlreadyAddedException extends RuntimeException {

}
