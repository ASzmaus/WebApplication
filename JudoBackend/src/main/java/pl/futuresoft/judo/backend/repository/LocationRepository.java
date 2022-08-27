package pl.futuresoft.judo.backend.repository;

import org.springframework.data.repository.CrudRepository;

import pl.futuresoft.judo.backend.entity.Location;

public interface LocationRepository extends CrudRepository<Location,Integer> {

}
