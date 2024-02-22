package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.command.Command;
import edu.java.bot.command.InitialCommand;
import edu.java.bot.core.Link;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public Command.Handler commandHandler(InitialCommand initialCommand, List<Command> commandList) {
        return Command.Handler.build(
            initialCommand,
            commandList.stream().filter(c -> !c.equals(initialCommand)).toList()
        );
    }

    @Bean
    public Map<Long, Set<Link>> trackHandler(){
        return new HashMap<>();
    }
}
