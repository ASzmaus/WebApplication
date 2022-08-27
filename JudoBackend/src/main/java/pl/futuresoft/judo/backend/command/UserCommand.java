package pl.futuresoft.judo.backend.command;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class UserCommand {
	  private Integer userId;
	  private String firstName;
	  private String lastName;
	  private String position;
}
