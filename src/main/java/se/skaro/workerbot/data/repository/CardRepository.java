package se.skaro.workerbot.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import se.skaro.workerbot.data.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long>{
	
	List<Card> findByFormatedNameIgnoreCase(String name);

}
