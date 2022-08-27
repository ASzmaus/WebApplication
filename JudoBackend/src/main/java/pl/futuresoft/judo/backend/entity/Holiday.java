package pl.futuresoft.judo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder

@Data
@Entity(name = "Holiday")

@Table(name = "holiday", schema = "public")
public class Holiday {

  @Id
  @Column(name = "holiday_id", nullable = false)
  private String holidayId;
  @Column(name = "holiday_date", nullable = false)
  private LocalDate holidayDate;
}