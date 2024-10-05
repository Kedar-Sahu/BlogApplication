package in.main.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import in.main.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{

}
