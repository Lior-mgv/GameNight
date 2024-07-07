package org.lior.gamenight;

import org.lior.gamenight.Entities.Game;
import org.lior.gamenight.Repositories.GameRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameNightApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(GameNightApplication.class, args);
	}

}
