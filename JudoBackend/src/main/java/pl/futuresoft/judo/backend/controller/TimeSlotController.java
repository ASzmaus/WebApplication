package pl.futuresoft.judo.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.futuresoft.judo.backend.command.TimeSlotCommand;
import pl.futuresoft.judo.backend.dto.TimeSlotDto;
import pl.futuresoft.judo.backend.service.TimeSlotService;

import java.util.List;

@RestController
public class TimeSlotController {

	@Autowired
	TimeSlotService timeSlotService;

	@PostMapping("/workGroup/{workGroupId}/timeSlot")
	public ResponseEntity<TimeSlotDto> addTimeSlot(@RequestBody TimeSlotDto timeSlotDto, @PathVariable int workGroupId) {
		timeSlotDto = timeSlotService.addTimeSlot(timeSlotDto, workGroupId);
		return new ResponseEntity<TimeSlotDto>(timeSlotDto, HttpStatus.OK);
	}

	@PutMapping("/workGroup/{workGroupId}/timeSlot/{timeSlotId}")
	public ResponseEntity<TimeSlotDto> editTimeSlot(@RequestBody TimeSlotDto timeSlotDto, @PathVariable int timeSlotId, @PathVariable int workGroupId) {
		timeSlotDto.setTimeSlotId(timeSlotId);
		timeSlotDto = timeSlotService.editTimeSlot(timeSlotDto, timeSlotId, workGroupId);
		return new ResponseEntity<TimeSlotDto>(timeSlotDto, HttpStatus.OK);
	}
	@GetMapping("/timeSlot")
	public ResponseEntity <List<TimeSlotCommand>>listTimeSlot(){
		List<TimeSlotCommand> timeSlotCommandList = timeSlotService.schemaPrintingCheckIfWhichDayIsHoliday();
	return new ResponseEntity<List<TimeSlotCommand>>(timeSlotCommandList,HttpStatus.OK);
	}
}