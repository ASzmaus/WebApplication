package pl.futuresoft.judo.backend.dto;

import lombok.Data;

@Data
public class LoginAvailableDto {
	private String login;
	private Boolean available;
}
