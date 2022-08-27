package pl.futuresoft.judo.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.futuresoft.judo.backend.entity.PageStatic;

import java.util.List;

public interface PageStaticRepository extends CrudRepository<PageStatic,Integer> {
	@Query("SELECT ss FROM pl.futuresoft.judo.backend.entity.PageStatic ss WHERE ss.display='t' AND ("
			+ "(ss.displayFrom IS NULL AND ss.displayTo IS NULL) OR"
			+ "(ss.displayFrom<=CURRENT_DATE AND ss.displayTo IS NULL) OR"
			+ "(ss.displayTo>=CURRENT_DATE AND ss.displayFrom IS NULL) OR"
			+ "(CURRENT_DATE BETWEEN ss.displayFrom AND ss.displayTo)"
			+ ") ORDER BY ss.sequence")
	List<PageStatic> findWyswietlane();
}
