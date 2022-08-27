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
@Entity(name = "UserAddress")
@Table(name = "user_address", schema = "administration")
public class UserAddress {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_address_id", nullable = false)
  private Integer userAddressId;
  @Column(name = "street", nullable = false)
  private String street;
  @Column(name = "house_number", nullable = false)
  private Integer houseNumber;
  @Column(name = "city", nullable = false)
  private String city;
  @Column(name = "postcode", nullable = false)
  private String postcode;
  @Column(name = "user_id", nullable = false)
  private Integer userId;
}