package userdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userdetails.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

}
