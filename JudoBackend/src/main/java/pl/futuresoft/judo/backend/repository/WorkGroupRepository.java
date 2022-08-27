package pl.futuresoft.judo.backend.repository;

import org.springframework.data.repository.CrudRepository;
import pl.futuresoft.judo.backend.entity.WorkGroup;

import java.util.List;

public interface WorkGroupRepository extends CrudRepository<WorkGroup, Integer>{

	WorkGroup findByName(String name);

	List<WorkGroup> findAllByClubId(int clubId);

	WorkGroup findByClubIdAndWorkGroupId(int clubId, int workGroupId);
}
