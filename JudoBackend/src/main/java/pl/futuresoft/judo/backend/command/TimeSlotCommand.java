package pl.futuresoft.judo.backend.command;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class TimeSlotCommand {
	private Integer timeSlotId;
	private LocalDate whichDay;
	private LocalTime lessonStart;
	private LocalTime lessonEnd;
	private Integer whichDayOfWeek;
	private LocalDate validFrom;
	private LocalDate validTo;
	private Integer workGroupId;
	private String name;
	private Integer userId;
	private String firstName;
	private String lastName;
	private Integer locationId;
	private String street;
	private Integer houseNumber;
	private String city;
	private String description;
	private String clubLocationOptional;
}
