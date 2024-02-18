package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.command.Command;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.command.InitialCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.core.Bot;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.List;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken
) {
    @Bean
    public Bot bot(InitialCommand initialCommand) {
        return new Bot() {
            private final TelegramBot telegramBot = new TelegramBot(telegramToken);

            @Override
            public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
                telegramBot.execute(request);
            }

            @Override
            public void init() {
                telegramBot.setUpdatesListener(updates -> {
                    updates.forEach(initialCommand::handle);
                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                });
            }
        };
    }
}
