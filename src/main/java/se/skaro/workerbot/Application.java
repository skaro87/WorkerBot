package se.skaro.workerbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import se.skaro.workerbot.bot.IrcBot;

@SpringBootApplication
@EnableCaching
public class Application {

	public static void main(String[] args) { 
		
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		
		IrcBot bot = (IrcBot) ctx.getBean("ircBot");
		bot.connect();
		
	}

}
