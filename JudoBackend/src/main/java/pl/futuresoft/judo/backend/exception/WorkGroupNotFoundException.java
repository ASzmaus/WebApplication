package pl.futuresoft.judo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class WorkGroupNotFoundException extends RuntimeException {
	public WorkGroupNotFoundException( Integer workGroupId)
	{
		super("Work group work groupId: " + workGroupId );
	}
}
