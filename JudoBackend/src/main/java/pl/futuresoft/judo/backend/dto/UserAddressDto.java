package pl.futuresoft.judo.backend.dto;

import lombok.Data;

@Data
public class UserAddressDto {
	private Integer userAddressId;
	private String street;
	private Integer houseNumber;
	private String city;
    private String postcode;
	private Integer userId;
}
