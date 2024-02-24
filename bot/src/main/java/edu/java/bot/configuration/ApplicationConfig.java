package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.core.dto.UserData;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken
) {
    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(telegramToken);
    }

    @Bean
    public Map<Long, UserData> trackHandler(){
        return new HashMap<>();
    }
}
