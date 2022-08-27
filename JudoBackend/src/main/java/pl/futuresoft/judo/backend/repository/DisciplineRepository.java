package pl.futuresoft.judo.backend.repository;

import org.springframework.data.repository.CrudRepository;

import pl.futuresoft.judo.backend.entity.Discipline;

public interface DisciplineRepository extends CrudRepository<Discipline,Integer> {

}
