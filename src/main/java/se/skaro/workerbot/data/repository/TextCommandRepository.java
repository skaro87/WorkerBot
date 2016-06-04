package se.skaro.workerbot.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.skaro.workerbot.data.entity.TextCommand;

public interface TextCommandRepository extends JpaRepository<TextCommand, Long>{

}
