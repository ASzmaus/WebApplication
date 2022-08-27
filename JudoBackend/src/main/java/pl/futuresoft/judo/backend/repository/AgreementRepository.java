package pl.futuresoft.judo.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.futuresoft.judo.backend.entity.*;
import java.util.List;

public interface AgreementRepository extends CrudRepository <Agreement, Integer>{

    @Query("FROM Agreement a WHERE a.userId=:uid")
    List<Agreement> findAllByUserId(@Param("uid") int userId);

    @Query("SELECT wg FROM Agreement a JOIN User u ON a.userId=u.userId JOIN WorkGroupUser wgu ON u.userId=wgu.userId JOIN WorkGroup wg ON wgu.workGroupId=wg.workGroupId WHERE a.userId=:ud")
    WorkGroup findWorkGroupByUserId(@Param("ud") int userId);
}
