package pl.futuresoft.judo.backend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.futuresoft.judo.backend.entity.User;
import pl.futuresoft.judo.backend.entity.WorkGroup;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {
	User findByEmail(String email);

	@Query(value = "UPDATE User uk set uk.deleted =:true where uk.userId = :userId")
	@Modifying
	public void softDelete(@Param("userId") int userId, @Param("true") Boolean deleted);

	@Query("FROM User uz WHERE uz.clubId=:cid AND uz.userId=:uzid")
	User findAllByClubIdByUserId(@Param("cid") int clubId, @Param("uzid") int userId);

	@Query("SELECT wg FROM User u JOIN WorkGroupUser wgu ON u.userId=wgu.userId JOIN WorkGroup wg ON wgu.workGroupId=wg.workGroupId WHERE u.userId=:uid")
	WorkGroup findWorkGroupByUserId(@Param("uid") int userId);

	List<User> findAllByClubId(Integer clubId);

	User findByEmailAndActive(String email, boolean active);

	List<User> findByActivationToken(String tokenAktywacyjny);

	List<User> findAllByClubIdAndActiveAndRoleIdInOrderByEmail( int clubId, boolean active, List<String> rolaIdList);

	List<User> findByReminderToken(String tokenPrzypomnienia);

}
