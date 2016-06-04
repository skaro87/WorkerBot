package se.skaro.workerbot.data.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import se.skaro.workerbot.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	@Cacheable("user")
	List<User> findByNameIgnoreCase(String name);
	
	List<String> findAutoJoinChannels();	
	
	List<User> findByIgnIgnoreCase(String ign);

	List<User> findByIgnContains(String message);
	

}
