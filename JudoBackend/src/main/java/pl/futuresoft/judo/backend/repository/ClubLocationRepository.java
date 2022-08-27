package pl.futuresoft.judo.backend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.futuresoft.judo.backend.entity.ClubLocation;
import pl.futuresoft.judo.backend.entity.Location;

import java.util.List;


public interface ClubLocationRepository extends CrudRepository<ClubLocation,Integer> {

	@Query("FROM ClubLocation cl WHERE cl.clubId=:kid AND cl.locationId=:cid")
	ClubLocation findAllByClubIdLocationId(@Param("kid") int clubId, @Param("cid")int locationId);

	@Query("DELETE FROM ClubLocation cl WHERE cl.clubId=:kid AND cl.locationId=:lid")
	@Modifying
	public void delete(@Param("kid") int clubId, @Param("lid") int locationId);

	@Query("SELECT d FROM ClubLocation cl JOIN Location d ON d.id=cl.locationId WHERE cl.clubId=:kid")
	List<Location> findAllByClubId(@Param("kid") int clubId);

    Integer findByLocationId(Integer clubLocationId);
}
