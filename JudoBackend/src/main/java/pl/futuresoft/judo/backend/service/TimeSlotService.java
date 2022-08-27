package pl.futuresoft.judo.backend.service;

import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.command.TimeSlotCommand;
import pl.futuresoft.judo.backend.entity.*;
import pl.futuresoft.judo.backend.exception.AtThisTimeThisGroupHasAlreadyLocation;
import pl.futuresoft.judo.backend.exception.ThisCoachAtThisTimeHasAlreadyGroup;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import pl.futuresoft.judo.backend.mapper.TimeSlotMapper;
import pl.futuresoft.judo.backend.repository.*;
import pl.futuresoft.judo.backend.dto.TimeSlotDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TimeSlotService {

	@Autowired
	TimeSlotRepository timeSlotRepository;
	@Autowired
	WorkGroupRepository workGroupRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TimeSlotMapper timeSlotMapper;

	@Autowired
	HolidayRepository holidayRepository;
	private void writeDtoToEntity(TimeSlot timeSlot, TimeSlotDto timeSlotDto) {
		timeSlot.setTimeSlotId(timeSlotDto.getTimeSlotId());
		timeSlot.setWhichDay(timeSlotDto.getWhichDay());
		timeSlot.setLessonStart(timeSlotDto.getLessonStart());
		timeSlot.setLessonEnd(timeSlotDto.getLessonEnd());
		timeSlot.setWhichDayOfWeek(timeSlotDto.getWhichDayOfWeek());
		timeSlot.setValidFrom(timeSlotDto.getValidFrom());
		timeSlot.setValidTo(timeSlotDto.getValidTo());
		timeSlot.setWorkGroupId(timeSlotDto.getWorkGroupId());
		timeSlot.setUserId(timeSlotDto.getUserId());
		timeSlot.setClubLocationOptional(timeSlotDto.getClubLocationOptional());
	}

	private void writeEntityToDto(TimeSlot timeSlot, TimeSlotDto timeSlotDto) {
		timeSlotDto.setTimeSlotId(timeSlot.getTimeSlotId());
		timeSlotDto.setWhichDay(timeSlot.getWhichDay());
		timeSlotDto.setLessonStart(timeSlot.getLessonStart());
		timeSlotDto.setLessonEnd(timeSlot.getLessonEnd());
		timeSlotDto.setWhichDayOfWeek(timeSlot.getWhichDayOfWeek());
		timeSlotDto.setValidFrom(timeSlot.getValidFrom());
		timeSlotDto.setValidTo(timeSlot.getValidTo());
		timeSlotDto.setWorkGroupId(timeSlot.getWorkGroupId());
		timeSlotDto.setUserId(timeSlot.getUserId());
		timeSlotDto.setClubLocationOptional(timeSlot.getClubLocationOptional());
	}

	@Transactional
	public TimeSlotDto addTimeSlot(TimeSlotDto timeSlotDto, Integer workGroupId) {
		Preconditions.checkNotNull(workGroupId, "WorkGroupId can not be null");
		if (!workGroupRepository.findById(workGroupId).isPresent())
			throw new EntityNotFoundException();
		if (!userRepository.findById(timeSlotDto.getUserId()).isPresent())
			throw new EntityNotFoundException();

		if (timeSlotDto.getWhichDay() != null && timeSlotDto.getWhichDayOfWeek() == null) {
			List<WorkGroup> workGroupList = timeSlotRepository.findByAllWhichDayAndLessonStartAndLessonEndAndUserId(timeSlotDto.getWhichDay(), timeSlotDto.getLessonStart(), timeSlotDto.getLessonEnd(),timeSlotDto.getUserId());
			if (!workGroupList.isEmpty())
				throw new ThisCoachAtThisTimeHasAlreadyGroup();
			List<ClubLocation> clubLocationList = timeSlotRepository.findAllByWhichDayAndLessonStartAndLessonEndAndWorkGroupId(timeSlotDto.getWhichDay(), timeSlotDto.getLessonStart(), timeSlotDto.getLessonEnd(), timeSlotDto.getWorkGroupId());
			if(!clubLocationList.isEmpty()){
				throw new AtThisTimeThisGroupHasAlreadyLocation();
			}
		}
		if (timeSlotDto.getWhichDay() == null && timeSlotDto.getWhichDayOfWeek() != null) {
			List<WorkGroup> workGroupList = timeSlotRepository.findByAllWhichDayOfWeekAndLessonStartAndLessonEndAndUserId(timeSlotDto.getWhichDayOfWeek(), timeSlotDto.getLessonStart(), timeSlotDto.getLessonEnd(),timeSlotDto.getUserId());
			if (!workGroupList.isEmpty())
				throw new ThisCoachAtThisTimeHasAlreadyGroup();
			List<ClubLocation> clubLocationList = timeSlotRepository.findAllByWhichDayOfWeekAndLessonStartAndLessonEndAndWorkGroupId(timeSlotDto.getWhichDayOfWeek(), timeSlotDto.getLessonStart(), timeSlotDto.getLessonEnd(), timeSlotDto.getWorkGroupId());
			if(!clubLocationList.isEmpty()){
				throw new AtThisTimeThisGroupHasAlreadyLocation();
			}
		}
		TimeSlot timeSlot = new TimeSlot();
		writeDtoToEntity(timeSlot, timeSlotDto);
		timeSlot.setWorkGroupId(workGroupId);
		timeSlot.setUserId(timeSlotDto.getUserId());
		timeSlotRepository.save(timeSlot);
		timeSlotDto.setTimeSlotId(timeSlot.getTimeSlotId());
		return timeSlotDto;
	}

	@Transactional
	public TimeSlotDto editTimeSlot (TimeSlotDto timeSlotDto, Integer timeSlotId, Integer workGroupId) {
		Preconditions.checkNotNull(timeSlotId, "TimeSlotId can not be null");
		Preconditions.checkNotNull(workGroupId, "WorkGroupId can not be null");
		Preconditions.checkNotNull(timeSlotDto.getUserId(), "UserId can not be null");
		if (!timeSlotRepository.findById(timeSlotDto.getTimeSlotId()).isPresent()) {
			throw new EntityNotFoundException();
		}
		TimeSlot timeSlot4 = timeSlotRepository.findById(timeSlotDto.getTimeSlotId()).get();
		if (timeSlotDto.getWhichDay() != null && timeSlotDto.getWhichDayOfWeek() == null) {
			List<WorkGroup> workGroupList = timeSlotRepository.findByAllWhichDayAndLessonStartAndLessonEndAndUserId(timeSlotDto.getWhichDay(), timeSlotDto.getLessonStart(), timeSlotDto.getLessonEnd(),timeSlotDto.getUserId());
			if (!workGroupList.isEmpty())
				throw new ThisCoachAtThisTimeHasAlreadyGroup();
			List<ClubLocation> clubLocationList = timeSlotRepository.findAllByWhichDayAndLessonStartAndLessonEndAndWorkGroupId(timeSlotDto.getWhichDay(), timeSlotDto.getLessonStart(), timeSlotDto.getLessonEnd(), timeSlotDto.getWorkGroupId());
			if(!clubLocationList.isEmpty()){
				throw new AtThisTimeThisGroupHasAlreadyLocation();
			}
		}
		if (timeSlotDto.getWhichDay() == null && timeSlotDto.getWhichDayOfWeek() != null) {
			List<WorkGroup> workGroupList = timeSlotRepository.findByAllWhichDayOfWeekAndLessonStartAndLessonEndAndUserId(timeSlotDto.getWhichDayOfWeek(), timeSlotDto.getLessonStart(), timeSlotDto.getLessonEnd(),timeSlotDto.getUserId());
			if (!workGroupList.isEmpty())
				throw new ThisCoachAtThisTimeHasAlreadyGroup();
			List<ClubLocation> clubLocationList = timeSlotRepository.findAllByWhichDayOfWeekAndLessonStartAndLessonEndAndWorkGroupId(timeSlotDto.getWhichDayOfWeek(), timeSlotDto.getLessonStart(), timeSlotDto.getLessonEnd(), timeSlotDto.getWorkGroupId());
			if(!clubLocationList.isEmpty()){
				throw new AtThisTimeThisGroupHasAlreadyLocation();
			}
		}
		timeSlot4.setTimeSlotId(timeSlotDto.getTimeSlotId());
		timeSlot4.setLessonStart(timeSlotDto.getLessonStart());
		timeSlot4.setLessonEnd(timeSlotDto.getLessonEnd());
		timeSlot4.setWhichDay(timeSlotDto.getWhichDay());
		timeSlot4.setWhichDayOfWeek(timeSlotDto.getWhichDayOfWeek());
		timeSlot4.setWorkGroupId(timeSlotDto.getWorkGroupId());
		timeSlot4.setUserId(timeSlotDto.getUserId());
		timeSlot4.setValidFrom(timeSlotDto.getValidFrom());
		timeSlot4.setValidTo(timeSlotDto.getValidTo());
		timeSlot4.setClubLocationOptional(timeSlotDto.getClubLocationOptional());
		timeSlotRepository.save(timeSlot4);
		writeEntityToDto(timeSlot4,timeSlotDto);
		return timeSlotDto;
	}

	@Transactional
	public List<TimeSlotCommand> schemaPrintingCheckIfWhichDayIsHoliday(){
		Iterable<TimeSlot> iterableTimeSlot = timeSlotRepository.findAll();
		if(iterableTimeSlot==null)
			return null;
		List<TimeSlotCommand> timeSlotCommandList = StreamSupport
				.stream(iterableTimeSlot.spliterator(), false)
				.filter( timeSlot -> ifNotEqualsHoliday(timeSlot) || timeSlot.getWhichDay()==null)
				.map(timeSlot -> timeSlotMapper.mapTimeSlotToTimeSlotCommand(timeSlot.getTimeSlotId()))
				.collect(Collectors.toList());
		return timeSlotCommandList;
	}

	private Boolean ifNotEqualsHoliday(TimeSlot timeSlot) {
		List<Holiday> listHoliday = holidayRepository.findAll();
		if(timeSlot.getWhichDay()==null)
			return true;
		return listHoliday.stream().noneMatch(holiday -> holiday.getHolidayDate().isEqual(timeSlot.getWhichDay()));
	}
}