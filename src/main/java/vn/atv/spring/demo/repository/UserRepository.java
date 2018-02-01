package vn.atv.spring.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.atv.spring.demo.model.User;

import java.util.List;

@Repository

public interface UserRepository extends JpaRepository<User,Integer> {


    User findByUsername(String userName);

    @Query("select u from User u where u.username<> :adminUsername")
    public List<User> findAll(@Param("adminUsername") String adminUsername);

    @Modifying
    @Query("update User u set u.image=:newImage where u.username=:username")
    public void updateImage(@Param("username")String username,@Param("newImage")String newImage);

    @Modifying
    @Query("update User u set u.address=:address, u.firstName=:firstName, u.lastName=:lastName, u.phone=:phone where u.username=:username")
    public void updateInfo(@Param("address") String address,@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("phone") String phone,@Param("username")String username);

    @Modifying
    @Query("update User u set u.email=:email where u.username=:username")
    public void updateEmail(@Param ("email")String newEmail,@Param("username")String username);

    @Modifying
    public void deleteByUsername(String username);
}
