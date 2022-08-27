package pl.futuresoft.judo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder

@Data
@Entity(name = "Role")
@Table(name = "role", schema = "administration")
public class Role {

  @Id
  @Column(name = "role_id", nullable = false)
  private String roleId;
  @Column(name = "name", nullable = false)
  private String name;
}