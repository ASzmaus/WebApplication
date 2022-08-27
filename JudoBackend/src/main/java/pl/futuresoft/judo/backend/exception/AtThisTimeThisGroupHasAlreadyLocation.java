package pl.futuresoft.judo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT, reason = "There is some location At this time and this group")
public class AtThisTimeThisGroupHasAlreadyLocation extends RuntimeException {

}
