package pl.futuresoft.judo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class DisciplineNotFoundInClubException extends RuntimeException {
	public DisciplineNotFoundInClubException(Integer clubId, Integer disciplineId)
	{
		super("Discipline disciplineId: " + disciplineId + "not found in club clubId: " + clubId);
	}
}
