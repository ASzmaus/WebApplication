package pl.futuresoft.judo.backend.entity;

import java.sql.*;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder

@Data
@Entity(name = "Location")
@Table(name = "location")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "location_id", nullable = false)
  private Integer locationId;
  @Column(name = "street", nullable = false)
  private String street;
  @Column(name = "house_number", nullable = false)
  private Integer houseNumber;
  @Column(name = "city", nullable = false)
  private String city;
  @Column(name = "postcode", nullable = false)
  private String postcode;
  @Column(name = "description", nullable = false)
  private String description;
}