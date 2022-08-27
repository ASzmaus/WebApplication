package pl.futuresoft.judo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT, reason = "This coach at this time has already group")
public class ThisCoachAtThisTimeHasAlreadyGroup extends RuntimeException {

}
