package com.telebotZodiac.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TelegramBotZodiacApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramBotZodiacApplication.class, args);
	}

}
