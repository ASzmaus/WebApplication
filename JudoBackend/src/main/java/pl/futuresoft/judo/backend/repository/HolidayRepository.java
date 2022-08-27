package pl.futuresoft.judo.backend.repository;

import org.springframework.data.repository.CrudRepository;
import pl.futuresoft.judo.backend.entity.*;

import java.util.List;

public interface HolidayRepository extends CrudRepository<Holiday, Integer> {
        List<Holiday> findAll();
}
