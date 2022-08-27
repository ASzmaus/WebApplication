package pl.futuresoft.judo.backend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.futuresoft.judo.backend.entity.Discipline;
import pl.futuresoft.judo.backend.entity.DisciplineClub;

import java.util.List;

public interface DisciplineClubRepository extends CrudRepository<DisciplineClub,Integer> {

	@Query("FROM DisciplineClub dk WHERE dk.clubId=:kid AND dk.disciplineId=:did")
	DisciplineClub findAllByClubIdByDisciplineId(@Param("kid") int clubId, @Param("did") int disciplineId);

	@Query("DELETE FROM DisciplineClub dk WHERE dk.disciplineId =:did AND dk.clubId=:kid")
	@Modifying
	public void delete(@Param("kid") int clubId, @Param("did") int disciplineId);

	@Query("SELECT d FROM DisciplineClub dk JOIN Discipline d ON d.id=dk.disciplineId WHERE dk.clubId=:kid")
	List<Discipline> findAllByClubId(@Param("kid") int clubId);

	DisciplineClub findByDisciplineId(int disciplineId);
}
