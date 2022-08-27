package pl.futuresoft.judo.backend.mapper;

import org.springframework.stereotype.Component;
import pl.futuresoft.judo.backend.command.TimeSlotCommand;
import pl.futuresoft.judo.backend.entity.Location;
import pl.futuresoft.judo.backend.entity.TimeSlot;
import pl.futuresoft.judo.backend.repository.*;

@Component
public class TimeSlotMapper {
    private final TimeSlotRepository timeSlotRepository;
    private final WorkGroupMapper workGroupMapper;
    private final UserMapper userMapper;
    private final LocationMapper locationMapper;

    public TimeSlotMapper(TimeSlotRepository timeSlotRepository, WorkGroupMapper workGroupMapper, UserMapper userMapper, LocationMapper locationMapper){
        this.timeSlotRepository=timeSlotRepository;
        this.workGroupMapper=workGroupMapper;
        this.userMapper=userMapper;
        this.locationMapper=locationMapper;
    }

    public TimeSlotCommand mapTimeSlotToTimeSlotCommand(Integer timeSlotId){
        TimeSlot timeSlot=timeSlotRepository
                .findById(timeSlotId)
                .orElseThrow(()-> new RuntimeException("No time slot for this Id"+timeSlotId));
        Location location=timeSlotRepository
                .findLocationByTimeSlotId(timeSlotId);

        return TimeSlotCommand
                .builder()
                .timeSlotId(timeSlotId)
                .lessonStart(timeSlot.getLessonStart())
                .lessonEnd(timeSlot.getLessonEnd())
                .whichDay(timeSlot.getWhichDay())
                .whichDayOfWeek(timeSlot.getWhichDayOfWeek())
                .workGroupId(timeSlot.getWorkGroupId())
                .name(workGroupMapper.mapWorkGroupToWorkGroupCommand(timeSlot.getWorkGroupId()).getName())
                .userId(timeSlot.getUserId())
                .firstName(userMapper.mapUserToUserCommand(timeSlot.getUserId()).getFirstName())
                .lastName(userMapper.mapUserToUserCommand(timeSlot.getUserId()).getLastName())
                .locationId(location.getLocationId())
                .description(locationMapper.mapLocationToLocationCommand(location.getLocationId()).getDescription())
                .city(locationMapper.mapLocationToLocationCommand(location.getLocationId()).getCity())
                .street(locationMapper.mapLocationToLocationCommand(location.getLocationId()).getStreet())
                .houseNumber(locationMapper.mapLocationToLocationCommand(location.getLocationId()).getHouseNumber())
                .validFrom(timeSlot.getValidFrom())
                .validTo(timeSlot.getValidTo())
                .clubLocationOptional(timeSlot.getClubLocationOptional())
                .build();
    }
}
