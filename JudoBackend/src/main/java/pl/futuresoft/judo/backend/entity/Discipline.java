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
@Entity(name = "Discipline")
@Table(name = "discipline")
public class Discipline {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "discipline_id", nullable = false)
  private Integer disciplineId;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "description", nullable = false)
  private String description;
}