package pl.futuresoft.judo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder

@Data
@Entity(name = "DisciplineClub")
@Table(name = "discipline_club")
public class DisciplineClub {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "discipline_club_id", nullable = false)
  private Integer disciplineClubId;
  @Column(name = "club_id", nullable = false)
  private Integer clubId;
  @Column(name = "discipline_id", nullable = false)
  private Integer disciplineId;
}