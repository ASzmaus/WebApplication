package pl.futuresoft.judo.backend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pl.futuresoft.judo.backend.entity.Club;

public interface ClubRepository extends CrudRepository<Club,Integer> {

    @Query(value = "UPDATE Club club set club.deleted =:true where club.clubId = :clubId")
    @Modifying
    public void delete(@Param("clubId") int clubId, @Param("true") Boolean deleted);

}

