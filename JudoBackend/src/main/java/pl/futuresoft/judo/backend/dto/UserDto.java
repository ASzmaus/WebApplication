package pl.futuresoft.judo.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
	  private Integer userId;
	  private String email;
	  private String password;
	  private RoleDto role;
	  private boolean active;
	  private Integer clubId;
	  private String firstName;
	  private String lastName;
	  private String position;
	  private Date birthdate;
}
