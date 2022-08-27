package pl.futuresoft.judo.backend.command;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WorkGroupCommand {
	private Integer workGroupId;
	private String name;
	private String bankAccountNumber;
}
