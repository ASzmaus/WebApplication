package pl.futuresoft.judo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name="TimeSlot")
@Table(name="time_slot")

public class TimeSlot {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="time_slot_id", nullable=false)
private Integer timeSlotId;
@Column(name="which_day", nullable=true)
private LocalDate whichDay;
@Column(name="lesson_start", nullable=false)
private LocalTime lessonStart;
@Column(name="lesson_end", nullable=false)
private LocalTime lessonEnd;
@Column(name="which_day_of_week", nullable=true)
private Integer whichDayOfWeek;
@Column(name="valid_from", nullable=false)
private LocalDate validFrom;
@Column(name="valid_to", nullable=false)
private LocalDate validTo;
@Column(name="work_group_id", nullable=false)
private Integer workGroupId;
@Column(name="user_id", nullable=false)
private Integer userId;
@Column(name="club_location_optional", nullable=true)
private String clubLocationOptional;
}
