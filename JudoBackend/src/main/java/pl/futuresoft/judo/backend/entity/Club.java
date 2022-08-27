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
@Entity(name = "Club")
@Table(name = "club")
public class Club {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_id", nullable = false)
  private Integer clubId;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "domain", nullable = false)
  private String domain;
  @Column(name = "phone_number", nullable = true)
  private String phoneNumber;
  @Column(name = "address_email", nullable = true)
  private String addressEmail;
  @Column(name = "deleted", nullable = false)
  private Boolean deleted;
}