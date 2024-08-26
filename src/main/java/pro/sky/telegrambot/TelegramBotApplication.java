package pro.sky.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TelegramBotApplication {
    //	t.me/ArrahtBot
    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}