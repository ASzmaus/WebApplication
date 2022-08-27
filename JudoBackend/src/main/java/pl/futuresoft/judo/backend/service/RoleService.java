package pl.futuresoft.judo.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.dto.RoleDto;
import pl.futuresoft.judo.backend.entity.Role;
import pl.futuresoft.judo.backend.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {

	public void writeEntityToDto(Role role, RoleDto roleDto) {
		roleDto.setName(role.getName());
		roleDto.setRoleId(role.getRoleId());
	}

	@Autowired
    RoleRepository roleRepository;

	@Transactional
	public RoleDto getRoleById(String id) {
		Optional<Role> role = roleRepository.findById(id);
		if (!role.isPresent())
			return null;
		RoleDto roleDto = new RoleDto();
		writeEntityToDto(role.get(),roleDto);
		return roleDto;
	}
}
