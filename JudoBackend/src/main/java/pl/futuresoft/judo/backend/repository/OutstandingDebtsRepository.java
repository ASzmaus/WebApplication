package pl.futuresoft.judo.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.futuresoft.judo.backend.entity.OutstandingDebts;
import pl.futuresoft.judo.backend.entity.WorkGroup;

import java.util.List;

public interface OutstandingDebtsRepository extends CrudRepository<OutstandingDebts, Integer> {

   @Query("FROM OutstandingDebts od JOIN User u ON od.userId=u.userId JOIN Club c ON u.clubId=c.clubId WHERE c.clubId=:cid")
   List<OutstandingDebts> findAllOutstandingDebtsByClubId(@Param("cid") Integer clubId);

   @Query("SELECT wg FROM OutstandingDebts od JOIN User u ON od.userId=u.userId JOIN WorkGroupUser wgu ON u.userId=wgu.userId JOIN WorkGroup wg ON wgu.workGroupId=wg.workGroupId WHERE od.debtsId=:did")
   WorkGroup findWorkGroupFromOutstandingDebtsByUserId(@Param("did") int debtsId);
}