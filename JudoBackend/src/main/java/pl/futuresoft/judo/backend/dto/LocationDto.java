package pl.futuresoft.judo.backend.dto;

import lombok.Data;

@Data
public class LocationDto {
	private Integer locationId;
	private String street;
	private Integer houseNumber;
	private String city;
    private String postcode;
	private String description;
}
