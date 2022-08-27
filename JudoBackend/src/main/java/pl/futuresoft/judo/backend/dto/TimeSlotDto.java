package pl.futuresoft.judo.backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data

public class TimeSlotDto {
	private Integer timeSlotId;
	private LocalDate whichDay;
	private LocalTime lessonStart;
	private LocalTime lessonEnd;
	private Integer whichDayOfWeek;
	private LocalDate validFrom;
	private LocalDate validTo;
	private Integer workGroupId;
	private Integer userId;
	private String clubLocationOptional;
}
