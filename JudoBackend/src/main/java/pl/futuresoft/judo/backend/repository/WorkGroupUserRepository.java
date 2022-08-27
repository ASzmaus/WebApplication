package pl.futuresoft.judo.backend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.futuresoft.judo.backend.entity.User;
import pl.futuresoft.judo.backend.entity.WorkGroupUser;

import java.util.List;

public interface WorkGroupUserRepository extends CrudRepository<WorkGroupUser, Integer> {

    @Query("FROM WorkGroupUser dwgu WHERE dwgu.workGroupId=:dwgid AND dwgu.userId=:duid")
    WorkGroupUser findAllByUserIdAndWorkGroupId(@Param("dwgid") int userId, @Param("duid") int workGroupId);

    @Query("DELETE FROM WorkGroupUser dwgu WHERE dwgu.workGroupId=:dwgid AND dwgu.userId=:duid")
    @Modifying
    void delete(@Param("dwgid") int workGroupId, @Param("duid") int userId);

   @Query("SELECT u FROM WorkGroupUser swgu JOIN User u ON u.id=swgu.userId WHERE swgu.workGroupId=:swgid")
   List<User> findAllByWorkGroupId(@Param("swgid") int workGroupId);

}
