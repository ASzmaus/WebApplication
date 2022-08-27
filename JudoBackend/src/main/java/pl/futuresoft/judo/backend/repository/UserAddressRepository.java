package pl.futuresoft.judo.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.futuresoft.judo.backend.entity.UserAddress;

import java.util.List;

public interface UserAddressRepository extends CrudRepository<UserAddress,Integer> {

   @Query("FROM UserAddress ua WHERE ua.userId=:uid")
   List<UserAddress> findAllByUserId(@Param("uid") int userId);
}



