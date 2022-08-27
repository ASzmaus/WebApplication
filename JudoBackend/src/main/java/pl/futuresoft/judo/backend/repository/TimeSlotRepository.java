package pl.futuresoft.judo.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.futuresoft.judo.backend.entity.TimeSlot;
import pl.futuresoft.judo.backend.entity.WorkGroup;
import pl.futuresoft.judo.backend.entity.ClubLocation;
import pl.futuresoft.judo.backend.entity.Location;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Integer> {

	@Query("SELECT workGroup FROM TimeSlot timeSlot JOIN  User user  ON  timeSlot.userId=user.userId JOIN WorkGroup workGroup ON  timeSlot.workGroupId=workGroup.workGroupId  WHERE timeSlot.whichDay =:wd AND timeSlot.lessonStart =:ls AND timeSlot.lessonEnd =:le AND timeSlot.userId =:uid")
	List<WorkGroup> findByAllWhichDayAndLessonStartAndLessonEndAndUserId(@Param("wd") LocalDate whichDay, @Param("ls") LocalTime lessonStart,  @Param("le") LocalTime lessonEnd,  @Param("uid") Integer userId);

	@Query("SELECT clubLocation FROM TimeSlot timeSlot JOIN WorkGroup workGroup ON timeSlot.workGroupId=workGroup.workGroupId JOIN ClubLocation clubLocation ON workGroup.clubLocation.clubLocationId=clubLocation.id WHERE timeSlot.whichDay=:wd AND timeSlot.lessonStart=:ls AND timeSlot.lessonEnd=:le AND timeSlot.workGroupId=:wid")
	List<ClubLocation> findAllByWhichDayAndLessonStartAndLessonEndAndWorkGroupId(@Param("wd")LocalDate whichDay, @Param("ls") LocalTime lessonStart, @Param("le") LocalTime lessonEnd, @Param("wid") Integer workGroupId);

	@Query("SELECT workGroup FROM TimeSlot timeSlot JOIN User user  ON  timeSlot.userId=user.userId JOIN WorkGroup workGroup ON  timeSlot.workGroupId=workGroup.workGroupId  WHERE timeSlot.whichDayOfWeek =:wd AND timeSlot.lessonStart =:ls AND timeSlot.lessonEnd =:le AND timeSlot.userId =:uid")
	List<WorkGroup> findByAllWhichDayOfWeekAndLessonStartAndLessonEndAndUserId(@Param("wd") Integer whichDayOfWeek, @Param("ls") LocalTime lessonStart,  @Param("le") LocalTime lessonEnd,  @Param("uid") Integer userId);

	@Query("SELECT clubLocation FROM TimeSlot timeSlot JOIN WorkGroup workGroup ON timeSlot.workGroupId=workGroup.workGroupId JOIN ClubLocation clubLocation ON workGroup.clubLocation.clubLocationId=clubLocation.id WHERE timeSlot.whichDayOfWeek=:wd AND timeSlot.lessonStart=:ls AND timeSlot.lessonEnd=:le AND timeSlot.workGroupId=:wid")
	List<ClubLocation> findAllByWhichDayOfWeekAndLessonStartAndLessonEndAndWorkGroupId(@Param("wd") Integer whichDayOfWeek, @Param("ls") LocalTime lessonStart, @Param("le") LocalTime lessonEnd, @Param("wid") Integer workGroupId);

	@Query("SELECT location FROM TimeSlot timeSlot JOIN WorkGroup workGroup ON timeSlot.workGroupId=workGroup.workGroupId JOIN ClubLocation clubLocation ON workGroup.clubLocation.clubLocationId=clubLocation.clubLocationId JOIN Location location ON clubLocation.locationId=location.locationId WHERE timeSlot.timeSlotId=:tsid")
	Location findLocationByTimeSlotId(@Param("tsid") int timeSlotId);

}
