package pl.futuresoft.judo.backend.repository;

import org.springframework.data.repository.CrudRepository;

import pl.futuresoft.judo.backend.entity.Role;

public interface RoleRepository extends CrudRepository<Role,String> {
}
