package pl.futuresoft.judo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "ClubLocation")
@Table(name = "club_location")
public class ClubLocation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  @Column(name = "club_location_id", nullable = false)
  private Integer clubLocationId;

  @OneToMany(mappedBy = "clubLocation", cascade = CascadeType.PERSIST)
  private List<WorkGroup> workGroupList;

  @Column(name = "club_id", nullable = false)
  private Integer clubId;

  @Column(name = "location_id", nullable = false)
  private Integer locationId;
}