package pl.futuresoft.judo.backend.repository;

import org.springframework.data.repository.CrudRepository;
import pl.futuresoft.judo.backend.entity.*;

public interface DocumentRepository extends CrudRepository<Document,Integer> {

}
