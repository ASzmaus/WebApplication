package pl.futuresoft.judo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "User")
@Table(name = "user", schema = "administration")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false)
  private Integer userId;
  @Column(name = "email", nullable = false)
  private String email;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "role_id", nullable = false)
  private String roleId;
  @Column(name = "active", nullable = false)
  private boolean active;
  @Column(name = "club_id", nullable = true)
  private Integer clubId;
  @Column(name = "deleted", nullable = false)
  private Boolean deleted;
  @Column(name = "activation_token", nullable = true)
  private String activationToken;
  @Column(name = "activation_token_expiration_date", nullable = true)
  private Timestamp activationTokenExpirationDate;
  @Column(name = "reminder_token", nullable = true)
  private String reminderToken;
  @Column(name = "reminder_token_expiration_date", nullable = true)
  private Timestamp reminderTokenExpirationDate;
  @Column(name = "beneficiary_id", nullable = true)
  private Integer beneficiaryId;
  @Column(name="first_name", nullable = true)
  private String firstName;
  @Column(name="last_name", nullable = true)
  private String lastName;
  @Column(name="position", nullable = true)
  private String position;
  @Column(name="birthdate", nullable = true)
  private Date birthdate;
}