package se.skaro.workerbot.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import se.skaro.workerbot.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByName(String name);
	List<User> findByInChannelTrue();

}
