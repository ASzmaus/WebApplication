package pl.futuresoft.judo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT, reason = "Wrong format of bank account")
public class WrongDataFormatException extends RuntimeException {
}
