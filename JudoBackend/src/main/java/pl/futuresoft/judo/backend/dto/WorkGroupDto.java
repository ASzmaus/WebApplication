package pl.futuresoft.judo.backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WorkGroupDto {
	private Integer workGroupId;
	private String name;
	private Integer disciplineId;
	private Integer clubId;
	private Integer limitOfPlaces;
	private Date startingDate;
	private Date endDate;
	private LocationDto locationDto;
	private BigDecimal monthlyCost;
	private String bankAccountNumber;
}
