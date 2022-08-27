package pl.futuresoft.judo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT, reason = "Club does not have paid a subscription")
public class ClubDoseNotHavePaidSubscriptionException extends RuntimeException {
}
