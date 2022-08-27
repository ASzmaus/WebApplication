package pl.futuresoft.judo.backend.command;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class LocationCommand {
	private Integer locationId;
	private String street;
	private Integer houseNumber;
	private String city;
  	private String description;
}
